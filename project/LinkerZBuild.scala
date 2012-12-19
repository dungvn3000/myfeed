import sbt._
import Keys._
import Project._
import sbtassembly.Plugin._
import AssemblyKeys._

object LinkerZBuild extends Build {

  lazy val sharedSetting = defaultSettings ++ Seq(
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

  lazy val linkerZRecommendation = Project("linkerz_recommendation_topology", file("linkerz_recommendation_topology"), settings = sharedSetting).settings(
    libraryDependencies ++= recommendationDependencies
  ).dependsOn(linkerZCore, linkerZModel, scalaStorm, linkerZLogger)

  lazy val linkerZLogger = Project("linkerz_logger", file("linkerz_logger"), settings = sharedSetting).settings(
    libraryDependencies ++= loggerDependencies ++ testDependencies
  ).dependsOn(linkerZCore, linkerZModel)

  lazy val linkerZCrawlTopology = Project("linkerz_crawl_topology", file("linkerz_crawl_topology"), settings = sharedSetting ++ assemblySettings).settings(
    jarName in assembly := "linkerz_crawl_topology.jar",
    libraryDependencies ++= crawlerTopologyDependencies
  ).dependsOn(
    linkerZCore, linkerZModel, linkerZLogger, scalaStorm, urlBuilder
  )

  lazy val scalaStorm = Project("scala_storm", file("scala_storm"), settings = sharedSetting).settings {
    libraryDependencies ++= stormDependencies ++ rabbitMqDependencies ++ testDependencies
  }

  lazy val urlBuilder = Project("url_builder", file("url_builder"), settings = sharedSetting).settings {
    libraryDependencies ++= testDependencies
  }

  lazy val coreDependencies = Seq(
    "org.slf4j" % "slf4j-simple" % "1.6.6",
    "org.slf4j" % "slf4j-api" % "1.6.6",
    "org.clapper" %% "grizzled-slf4j" % "0.6.9",
    "commons-collections" % "commons-collections" % "3.2.1",
    "commons-digester" % "commons-digester" % "2.1" exclude("commons-beanutils", "commons-beanutils"),
    "commons-lang" % "commons-lang" % "2.6",
    "org.apache.commons" % "commons-math3" % "3.0",
    "commons-validator" % "commons-validator" % "1.4.0" exclude("commons-beanutils", "commons-beanutils"),
    "commons-io" % "commons-io" % "2.4",
    "org.scalaz" %% "scalaz-core" % "6.0.4",
    "com.typesafe" % "config" % "1.0.0",
    "com.typesafe.akka" % "akka-actor" % "2.0.3"
  )

  lazy val testDependencies = Seq(
    "junit" % "junit" % "4.10" % "test",
    "org.scalatest" %% "scalatest" % "1.8" % "test"
  )

  lazy val modelDependencies = Seq(
    "com.novus" %% "salat" % "1.9.1"
  )

  lazy val crawlerTopologyDependencies = Seq(
    "org.jsoup" % "jsoup" % "1.7.1",
    "org.apache.httpcomponents" % "httpclient" % "4.2.2",
    "net.htmlparser.jericho" % "jericho-html" % "3.2",
    "net.coobird" % "thumbnailator" % "0.4.2",
    "com.gravity" % "goose" % "2.1.22",
    "org.scalanlp" % "breeze-process_2.9.2" % "0.1"
  ) ++ stormDependencies ++ testDependencies

  lazy val recommendationDependencies = Seq(
    "org.scalanlp" % "breeze-process_2.9.2" % "0.1",
    "org.apache.mahout" % "mahout-core" % "0.7",
    "org.carrot2" % "carrot2-mini" % "3.6.1",
    "redis.clients" % "jedis" % "2.1.0"
  ) ++ stormDependencies ++ testDependencies

  lazy val loggerDependencies = Seq(
  )

  lazy val stormDependencies = Seq(
    "storm" % "storm" % "0.8.1" % "provided"
  )

  lazy val rabbitMqDependencies = Seq(
    "com.rabbitmq" % "amqp-client" % "2.8.7"
  )
}