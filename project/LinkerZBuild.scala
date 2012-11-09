import sbt._
import Keys._
import Project._
import com.github.retronym.SbtOneJar._

object LinkerZBuild extends Build {

  val sharedSetting = Seq(
    version := "0.1-SNAPSHOT",
    organization := "org.linkerz",
    scalaVersion := "2.9.1",
    exportJars := true
  )

  lazy val linkerZ = Project("linkerz", file("."), settings = defaultSettings ++ sharedSetting ++ oneJarSettings).aggregate(
    linkerZCore, linkerzModel, linkerZJobQueue, linkerZCrawlerCore, linkerZCrawlerBot, linkerRecommendation
  ).dependsOn(
    linkerZCore, linkerzModel, linkerZJobQueue, linkerZCrawlerCore, linkerZCrawlerBot, linkerRecommendation
  )

  lazy val linkerZCore = Project("linkerz_core", file("linkerz_core"), settings = defaultSettings ++ sharedSetting).settings(
    libraryDependencies ++= coreDependencies
  )

  lazy val linkerzModel = Project("linkerz_model", file("linkerz_model"), settings = defaultSettings ++ sharedSetting).settings(
    libraryDependencies ++= modelDependencies ++ testDependencies
  ).dependsOn(linkerZCore)

  lazy val linkerZJobQueue = Project("linkerz_job_queue", file("linkerz_job_queue"), settings = defaultSettings ++ sharedSetting).settings(
    libraryDependencies ++= jobQueueDependencies ++ testDependencies,
    resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
  ).dependsOn(linkerZCore)

  lazy val linkerZCrawlerCore = Project("linkerz_crawler_core", file("linkerz_crawler_core"), settings = defaultSettings ++ sharedSetting).settings(
    libraryDependencies ++= crawlerCoreDependencies ++ testDependencies
  ).dependsOn(linkerZCore, linkerZJobQueue, linkerzModel)

  lazy val linkerZCrawlerBot = Project("linkerz_crawler_bot", file("linkerz_crawler_bot"), settings = defaultSettings ++ sharedSetting).settings(
    libraryDependencies ++= crawlerBotDependencies ++ testDependencies
  ).dependsOn(linkerZCore, linkerZJobQueue, linkerzModel, linkerZCrawlerCore)

  lazy val linkerRecommendation = Project("linkerz_recommendation", file("linkerz_recommendation"), settings = defaultSettings ++ sharedSetting).settings(
    libraryDependencies ++= recommendationDependencies ++ testDependencies
  ).dependsOn(linkerZCore, linkerzModel)

  val coreDependencies = Seq(
    "org.slf4j" % "slf4j-simple" % "1.6.6",
    "org.slf4j" % "slf4j-api" % "1.6.6",
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
    "com.novus" %% "salat" % "1.9.1"
  )

  val jobQueueDependencies = Seq(
    "com.rabbitmq" % "amqp-client" % "2.8.7",
    "com.typesafe.akka" % "akka-actor" % "2.0.3"
  )

  val crawlerCoreDependencies = Seq(
    "org.jsoup" % "jsoup" % "1.7.1",
    "commons-httpclient" % "commons-httpclient" % "3.1",
    "org.apache.httpcomponents" % "httpclient" % "4.2.1",
    "com.ning" % "async-http-client" % "1.7.6",
    "org.apache.tika" % "tika-core" % "1.2",
    "org.apache.tika" % "tika-parsers" % "1.2",
    "net.coobird" % "thumbnailator" % "0.4.2",
    "net.sf.trove4j" % "trove4j" % "3.0.3",
    "org.springframework" % "spring-web" % "3.1.2.RELEASE" excludeAll(
      ExclusionRule(organization = "org.springframework", name = "spring-beans"),
      ExclusionRule(organization = "org.springframework", name = "spring-asm"),
      ExclusionRule(organization = "org.springframework", name = "spring-context"),
      ExclusionRule(organization = "org.springframework", name = "spring-tx"),
      ExclusionRule(organization = "org.springframework", name = "spring-aop"),
      ExclusionRule(organization = "org.springframework", name = "spring-expression")
    )
  )

  val crawlerBotDependencies = Seq(
    "org.quartz-scheduler" % "quartz" % "2.1.6"
  )

  val recommendationDependencies = Seq(
    "org.scalanlp" % "breeze-math_2.9.2" % "0.1",
    "org.scalanlp" % "breeze-learn_2.9.2" % "0.1",
    "org.scalanlp" % "breeze-process_2.9.2" % "0.1",
    "org.scalanlp" % "breeze-viz_2.9.2" % "0.1",
    "org.apache.commons" % "commons-math3" % "3.0",
    "net.debasishg" %% "redisclient" % "2.7"
  )
}


