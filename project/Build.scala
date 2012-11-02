import sbt._
import Keys._
import Project._

object LinkerZBuild extends Build {

  lazy val linkerZ = Project("linkerz", file("."), settings = defaultSettings).aggregate(
    linkerZCore, linkerzModel, linkerZTest, linkerZJobQueue, linkerZCrawlerCore, linkerZCrawlerBot
  )

  lazy val linkerZCore = Project("linkerz_core", file("linkerz_core"), settings = defaultSettings).settings(
    libraryDependencies ++= coreDependencies
  )

  lazy val linkerZTest = Project("linkerz_test", file("linkerz_test"), settings = defaultSettings).settings(
    libraryDependencies ++= testDependencies
  ).dependsOn(linkerZCore)

  lazy val linkerzModel = Project("linkerz_model", file("linkerz_model"), settings = defaultSettings).settings(
    libraryDependencies ++= modelDependencies ++ testDependencies
  ).dependsOn(linkerZCore, linkerZTest)

  lazy val linkerZJobQueue = Project("linkerz_job_queue", file("linkerz_job_queue"), settings = defaultSettings).settings(
    libraryDependencies ++= jobQueueDependencies ++ testDependencies,
    resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
  ).dependsOn(linkerZCore, linkerZTest)

  lazy val linkerZCrawlerCore = Project("linkerz_crawler_core", file("linkerz_crawler_core"), settings = defaultSettings).settings(
    libraryDependencies ++= crawlerCoreDependencies ++ testDependencies
  ).dependsOn(linkerZCore, linkerZTest, linkerZJobQueue, linkerzModel)

  lazy val linkerZCrawlerBot = Project("linkerz_crawler_bot", file("linkerz_crawler_bot"), settings = defaultSettings).settings(
    libraryDependencies ++= crawlerBotDependencies ++ testDependencies
  ).dependsOn(linkerZCore, linkerZTest, linkerZJobQueue, linkerzModel, linkerZCrawlerCore)

  val coreDependencies = Seq(
    "org.clapper" %% "grizzled-slf4j" % "0.6.9",
    "commons-collections" % "commons-collections" % "3.2.1",
    "commons-digester" % "commons-digester" % "2.1",
    "commons-lang" % "commons-lang" % "2.6",
    "commons-validator" % "commons-validator" % "1.4.0",
    "commons-io" % "commons-io" % "2.4"
  )

  val testDependencies = Seq(
    "junit" % "junit" % "4.10" % "test",
    "org.scalatest" %% "scalatest" % "1.8" % "test"
  )

  val modelDependencies = Seq(
    "org.springframework.data" % "spring-data-mongodb" % "1.0.3.RELEASE"
  )

  val jobQueueDependencies = Seq(
    "com.rabbitmq" % "amqp-client" % "2.8.7",
    "com.typesafe.akka" % "akka-actor" % "2.0.3"
  )

  val crawlerCoreDependencies = Seq(
    "org.jsoup" % "jsoup" % "1.6.3",
    "commons-httpclient" % "commons-httpclient" % "3.1",
    "org.apache.httpcomponents" % "httpclient" % "4.2.1",
    "com.ning" % "async-http-client" % "1.7.6",
    "org.apache.tika" % "tika-core" % "1.2",
    "org.apache.tika" % "tika-parsers" % "1.2",
    "net.coobird" % "thumbnailator" % "0.4.2",
    "net.sf.trove4j" % "trove4j" % "3.0.3",
    "org.springframework" % "spring-web" % "3.1.2.RELEASE"
  )

  val crawlerBotDependencies = Seq(
    "org.quartz-scheduler" % "quartz" % "2.1.6"
  )
}


