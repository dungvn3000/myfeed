import sbt._
import Keys._
import Project._
import sbtassembly.Plugin._
import AssemblyKeys._

object LinkerZBuild extends Build {

  val sharedSetting = defaultSettings ++ Seq(
    version := "0.1-SNAPSHOT",
    organization := "org.linkerz",
    scalaVersion := "2.9.1",
    resolvers ++= Seq(
      "Typesafe Repository" at "http://repo.akka.io/releases/",
      "twitter4j" at "http://twitter4j.org/maven2",
      "clojars.org" at "http://clojars.org/repo",
      Resolver.file("Local Repository", file(Path.userHome.absolutePath + "/.ivy2/local"))(Resolver.ivyStylePatterns)
    )
  )

  lazy val linkerZ = Project("linkerz", file("."), settings = sharedSetting ++ assemblySettings).settings(
    jarName in assembly := "linkerz.jar",
    excludedJars in assembly <<= (fullClasspath in assembly) map {
      _.filter(key => List("scala-compiler.jar").contains(key.data.getName))
    },
    mergeStrategy in assembly <<= (mergeStrategy in assembly) {
      (old) => {
        case "overview.html" => MergeStrategy.discard
        case x => old(x)
      }
    }
  ).aggregate(
    linkerZCore, linkerzModel, linkerZJobQueue, linkerZCrawlerCore, linkerZCrawlerBot, linkerZRecommendation, linkerZLogger, linkerZStorm
  ).dependsOn(
    linkerZCore, linkerzModel, linkerZJobQueue, linkerZCrawlerCore, linkerZCrawlerBot, linkerZRecommendation, linkerZLogger, linkerZStorm
  )

  lazy val linkerZCore = Project("linkerz_core", file("linkerz_core"), settings = sharedSetting).settings(
    libraryDependencies ++= coreDependencies
  )

  lazy val linkerzModel = Project("linkerz_model", file("linkerz_model"), settings = sharedSetting).settings(
    libraryDependencies ++= modelDependencies ++ testDependencies
  ).dependsOn(linkerZCore)

  lazy val linkerZJobQueue = Project("linkerz_job_queue", file("linkerz_job_queue"), settings = sharedSetting).settings(
    libraryDependencies ++= jobQueueDependencies ++ testDependencies
  ).dependsOn(linkerZCore, linkerzModel)

  lazy val linkerZCrawlerCore = Project("linkerz_crawler_core", file("linkerz_crawler_core"), settings = sharedSetting).settings(
    libraryDependencies ++= crawlerCoreDependencies ++ testDependencies
  ).dependsOn(linkerZCore, linkerZJobQueue, linkerzModel, linkerZLogger)

  lazy val linkerZCrawlerBot = Project("linkerz_crawler_bot", file("linkerz_crawler_bot"), settings = sharedSetting).settings(
    libraryDependencies ++= crawlerBotDependencies ++ testDependencies
  ).dependsOn(linkerZCore, linkerZJobQueue, linkerzModel, linkerZCrawlerCore)

  lazy val linkerZRecommendation = Project("linkerz_recommendation", file("linkerz_recommendation"), settings = sharedSetting).settings(
    libraryDependencies ++= recommendationDependencies ++ testDependencies
  ).dependsOn(linkerZCore, linkerzModel, linkerZJobQueue)

  lazy val linkerZLogger = Project("linkerz_logger", file("linkerz_logger"), settings = sharedSetting).settings(
    libraryDependencies ++= loggerDependencies ++ testDependencies
  ).dependsOn(linkerZCore, linkerzModel)

  lazy val linkerZStorm = Project("linkerz_storm", file("linkerz_storm"), settings = sharedSetting).settings(
    libraryDependencies ++= stormDependencies ++ testDependencies
  ).dependsOn(
    linkerZCore, linkerzModel, linkerZJobQueue, linkerZCrawlerCore, linkerZCrawlerBot, linkerZRecommendation, linkerZLogger
  )

  val coreDependencies = Seq(
    "org.slf4j" % "slf4j-simple" % "1.6.6",
    "org.slf4j" % "slf4j-api" % "1.6.6",
    "org.clapper" %% "grizzled-slf4j" % "0.6.9",
    "commons-collections" % "commons-collections" % "3.2.1",
    "commons-digester" % "commons-digester" % "2.1" exclude("commons-beanutils", "commons-beanutils"),
    "commons-lang" % "commons-lang" % "2.6",
    "commons-validator" % "commons-validator" % "1.4.0" exclude("commons-beanutils", "commons-beanutils"),
    "commons-io" % "commons-io" % "2.4",
    "org.scalaz" %% "scalaz-core" % "6.0.4",
    "com.typesafe" % "config" % "1.0.0"
  )

  val testDependencies = Seq(
    "junit" % "junit" % "4.10" % "test",
    "org.scalatest" %% "scalatest" % "1.8" % "test"
  )

  val modelDependencies = Seq(
    "com.novus" %% "salat" % "1.9.1"
  )

  val jobQueueDependencies = Seq(
    "com.rabbitmq" % "amqp-client" % "2.8.7",
    "com.typesafe.akka" % "akka-actor" % "2.0.3",
    "com.typesafe.akka" % "akka-remote" % "2.0.3",
    "com.typesafe.akka" % "akka-kernel" % "2.0.3"
  )

  val crawlerCoreDependencies = Seq(
    "org.jsoup" % "jsoup" % "1.7.1",
    "commons-httpclient" % "commons-httpclient" % "3.1",
    "org.apache.httpcomponents" % "httpclient" % "4.2.1",
    "com.ning" % "async-http-client" % "1.7.7",
    "org.apache.tika" % "tika-core" % "1.2",
    "org.apache.tika" % "tika-parsers" % "1.2",
    "net.coobird" % "thumbnailator" % "0.4.2",
    "net.sf.trove4j" % "trove4j" % "3.0.3"
  )

  val crawlerBotDependencies = Seq(
  )

  val recommendationDependencies = Seq(
    "org.scalanlp" % "breeze-process_2.9.2" % "0.1",
    "org.apache.commons" % "commons-math3" % "3.0",
    "redis.clients" % "jedis" % "2.1.0"
  )

  val loggerDependencies = Seq(
  )

  val stormDependencies = Seq(
    "com.dc" %% "scala-storm" % "0.2.2-SNAPSHOT",
    //Set this to provided when develop to storm server.
    "storm" % "storm" % "0.8.1"
  )
}


