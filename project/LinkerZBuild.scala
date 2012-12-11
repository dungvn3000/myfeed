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
    scalacOptions += "-Yresolve-term-conflict:package",
    resolvers ++= Seq(
      "Typesafe Repository" at "http://repo.akka.io/releases/",
      "twitter4j" at "http://twitter4j.org/maven2",
      "clojars.org" at "http://clojars.org/repo",
      "Local Maven Repository" at "file://" + Path.userHome.absolutePath + "/.m2/repository",
      Resolver.file("Local Repository", file(Path.userHome.absolutePath + "/.ivy2/local"))(Resolver.ivyStylePatterns)
    )
  )

  lazy val linkerZ = Project("linkerz", file("."), settings = sharedSetting).aggregate(
    linkerZCore, linkerZModel, linkerZRecommendation, linkerZLogger, scalaStorm, urlBuilder, linkerZCrawlTopology
  )

  lazy val linkerZCore = Project("linkerz_core", file("linkerz_core"), settings = sharedSetting).settings(
    libraryDependencies ++= coreDependencies
  )

  lazy val linkerZModel = Project("linkerz_model", file("linkerz_model"), settings = sharedSetting).settings(
    libraryDependencies ++= modelDependencies ++ testDependencies
  ).dependsOn(linkerZCore)

  lazy val linkerZRecommendation = Project("linkerz_recommendation", file("linkerz_recommendation"), settings = sharedSetting).settings(
    libraryDependencies ++= recommendationDependencies ++ testDependencies
  ).dependsOn(linkerZCore, linkerZModel)

  lazy val linkerZLogger = Project("linkerz_logger", file("linkerz_logger"), settings = sharedSetting).settings(
    libraryDependencies ++= loggerDependencies ++ testDependencies
  ).dependsOn(linkerZCore, linkerZModel)

  lazy val linkerZCrawlTopology = Project("linkerz_crawl_topology", file("linkerz_crawl_topology"), settings = sharedSetting).settings(
    jarName in assembly := "linkerz_crawl_topology.jar",
    libraryDependencies ++= stormDependencies ++ crawlerTopologyDependencies ++ testDependencies
  ).dependsOn(
    linkerZCore, linkerZModel, linkerZLogger, scalaStorm, urlBuilder
  )

  lazy val scalaStorm = Project("scala_storm", file("scala_storm"), settings = sharedSetting).settings {
    libraryDependencies ++= stormDependencies ++ testDependencies
  }

  lazy val urlBuilder = Project("url_builder", file("url_builder"), settings = sharedSetting).settings {
    libraryDependencies ++= testDependencies
  }

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

  val crawlerTopologyDependencies = Seq(
    "org.jsoup" % "jsoup" % "1.7.1",
    "commons-httpclient" % "commons-httpclient" % "3.1",
    "org.apache.httpcomponents" % "httpclient" % "4.2.1",
    "com.ning" % "async-http-client" % "1.7.7",
    "org.apache.tika" % "tika-core" % "1.2",
    "org.apache.tika" % "tika-parsers" % "1.2",
    "net.coobird" % "thumbnailator" % "0.4.2",
    "com.rabbitmq" % "amqp-client" % "2.8.7"
  )

  val recommendationDependencies = Seq(
    "org.scalanlp" % "breeze-process_2.9.2" % "0.1",
    "org.apache.commons" % "commons-math3" % "3.0",
    "redis.clients" % "jedis" % "2.1.0"
  )

  val loggerDependencies = Seq(
  )

  val stormDependencies = Seq(
    "storm" % "storm" % "0.8.1" % "provided"
  )
}