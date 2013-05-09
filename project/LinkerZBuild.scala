import sbt._
import Keys._
import Project._
import sbtassembly.Plugin._
import AssemblyKeys._

object LinkerZBuild extends Build {

  lazy val sharedSetting = defaultSettings ++ Seq(
    version := "0.1-SNAPSHOT",
    organization := "org.linkerz",
    scalaVersion := "2.10.0",
    resolvers ++= Seq(
      "twitter4j" at "http://twitter4j.org/maven2",
      "clojars.org" at "http://clojars.org/repo",
      "thischwa-repro" at "http://maven-repo.thischwa.de/",
      "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
      "Expecty Repository" at "https://raw.github.com/pniederw/expecty/master/m2repo/",
      "Local Maven Repository" at "file://" + Path.userHome.absolutePath + "/.m2/repository",
      Resolver.file("Local Repository", file(Path.userHome.absolutePath + "/.ivy2/local"))(Resolver.ivyStylePatterns)
    )
  )

  lazy val linkerZ = Project("linkerz", file("."), settings = sharedSetting).aggregate(
    linkerZCore, linkerZModel, linkerZRecommendation, linkerZLogger,
    scalaStorm, urlBuilder, linkerZTopologyCrawl, linkerZDao
  )

  lazy val linkerZCore = Project("linkerz_core", file("linkerz_core"), settings = sharedSetting).settings(
    libraryDependencies ++= coreDependencies
  )

  lazy val linkerZModel = Project("linkerz_model", file("linkerz_model"), settings = sharedSetting).settings(
    libraryDependencies ++= modelDependencies ++ testDependencies
  ).dependsOn(linkerZCore)

  lazy val linkerZDao = Project("linkerz_dao", file("linkerz_dao"), settings = sharedSetting).settings(
    libraryDependencies ++= testDependencies
  ).dependsOn(linkerZCore, linkerZModel)

  lazy val linkerZRecommendation = Project("linkerz_topology_recommendation", file("linkerz_topology_recommendation"), settings = sharedSetting).settings(
    libraryDependencies ++= recommendationDependencies
  ).dependsOn(linkerZCore, linkerZDao, scalaStorm, linkerZLogger)

  lazy val linkerZLogger = Project("linkerz_logger", file("linkerz_logger"), settings = sharedSetting).settings(
    libraryDependencies ++= testDependencies
  ).dependsOn(linkerZCore, linkerZDao)

  lazy val linkerZTopologyCrawl = Project("linkerz_topology_crawl", file("linkerz_topology_crawl"), settings = sharedSetting ++ assemblySettings).settings(
    jarName in assembly := "linkerz_topology_crawl.jar",
    libraryDependencies ++= crawlerTopologyDependencies
  ).dependsOn(
    linkerZCore, linkerZDao, linkerZLogger, scalaStorm, urlBuilder
  )

  lazy val scalaStorm = Project("scala_storm", file("scala_storm"), settings = sharedSetting).settings(
    libraryDependencies ++= stormDependencies ++ rabbitMqDependencies ++ testDependencies,
    scalacOptions += "-Yresolve-term-conflict:package"
  )

  lazy val urlBuilder = Project("url_builder", file("url_builder"), settings = sharedSetting).settings {
    libraryDependencies ++= testDependencies
  }

  lazy val coreDependencies = Seq(
    "org.slf4j" % "slf4j-simple" % "1.6.6",
    "org.slf4j" % "slf4j-api" % "1.6.6",
    "org.clapper" %% "grizzled-slf4j" % "1.0.1",
    "commons-collections" % "commons-collections" % "3.2.1",
    "org.apache.commons" % "commons-lang3" % "3.1",
    "org.apache.commons" % "commons-math3" % "3.0",
    "commons-digester" % "commons-digester" % "2.1" exclude("commons-beanutils", "commons-beanutils"),
    "commons-validator" % "commons-validator" % "1.4.0" exclude("commons-beanutils", "commons-beanutils"),
    "commons-io" % "commons-io" % "2.4",
    "com.typesafe" % "config" % "1.0.0",
    "com.typesafe.akka" %% "akka-actor" % "2.1.0"
  )

  lazy val testDependencies = Seq(
    "junit" % "junit" % "4.10" % "test",
    "org.expecty" % "expecty" % "0.9" % "test",
    "org.scalatest" %% "scalatest" % "1.9.1" % "test"
  )

  lazy val modelDependencies = Seq(
    "com.novus" %% "salat" % "1.9.2-SNAPSHOT" exclude("joda-time", "joda-time"),
    "joda-time" % "joda-time" % "2.1",
    "org.joda" % "joda-convert" % "1.2"
  )

  lazy val crawlerTopologyDependencies = Seq(
    "net.coobird" % "thumbnailator" % "0.4.3",
    "com.gravity" % "goose" % "2.1.22",
    "org.apache.httpcomponents" % "httpclient" % "4.2.5",
    "de.thischwa.jii" % "java-image-info" % "0.5",
    "org.scalanlp" %% "breeze-process" % "0.3-SNAPSHOT",
    "com.github.sonic" %% "sonic_parser" % "0.0.1",
    "ch.sentric" % "url-normalization" % "1.0.0"
  ) ++ stormDependencies ++ testDependencies

  lazy val recommendationDependencies = Seq(
    "org.scalanlp" %% "breeze-process" % "0.3-SNAPSHOT",
    "org.apache.mahout" % "mahout-core" % "0.7",
    "org.carrot2" % "carrot2-mini" % "3.6.1",
    "redis.clients" % "jedis" % "2.1.0"
  ) ++ stormDependencies ++ testDependencies

  lazy val stormDependencies = Seq(
    "storm" % "storm" % "0.8.2" % "provided"
  )

  lazy val rabbitMqDependencies = Seq(
    "com.rabbitmq" % "amqp-client" % "2.8.7"
  )
}