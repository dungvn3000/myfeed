import sbt._
import Keys._
import Project._
import sbtassembly.Plugin._
import AssemblyKeys._

object MyFeedBuild extends Build {

  val appOrganization = "vn.myfeed"
  val appName = "myfeed"
  val appVersion = "0.1.0-SNAPSHOT"
  val appScalaVersion = "2.10.0"

  lazy val sharedSetting = defaultSettings ++ Seq(
    version := appVersion,
    organization := appOrganization,
    scalaVersion := appScalaVersion,
    resolvers ++= Seq(
      "twitter4j" at "http://twitter4j.org/maven2",
      "clojars.org" at "http://clojars.org/repo",
      "thischwa-repro" at "http://maven-repo.thischwa.de/",
      "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
      "Expecty Repository" at "https://raw.github.com/pniederw/expecty/master/m2repo/",
      //On macos
      //"Local Maven Repository" at "file:///Volumes/Data/Java/m2repository/",
      "Local Maven Repository" at "file://" + Path.userHome.absolutePath + "/.m2/repository",
      Resolver.file("Local Repository", file(Path.userHome.absolutePath + "/.ivy2/local"))(Resolver.ivyStylePatterns)
    )
  )

  lazy val myfeed = Project(appName, file("."), settings = sharedSetting).aggregate(
    core, model, logger, scalaStorm, urlBuilder, crawler, dao
  )

  lazy val core = Project("core", file("core"), settings = sharedSetting).settings(
    libraryDependencies ++= coreDependencies
  )

  lazy val model = Project("model", file("model"), settings = sharedSetting).settings(
    libraryDependencies ++= modelDependencies ++ testDependencies
  )

  lazy val dao = Project("dao", file("dao"), settings = sharedSetting).settings(
    libraryDependencies ++= testDependencies
  ).dependsOn(core, model)


  lazy val logger = Project("logger", file("logger"), settings = sharedSetting).settings(
    libraryDependencies ++= testDependencies
  ).dependsOn(core, dao)

  lazy val crawler = Project("crawler", file("crawler"), settings = sharedSetting ++ assemblySettings).settings(
    jarName in assembly := "crawler.jar",
    libraryDependencies ++= crawlerTopologyDependencies
  ).dependsOn(
    core, dao, logger, scalaStorm, urlBuilder
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
    "org.scalanlp" %% "breeze-process" % "0.3",
    "com.github.sonic" %% "sonic_parser" % "0.0.1",
    "ch.sentric" % "url-normalization" % "1.0.0"
  ) ++ stormDependencies ++ testDependencies

  lazy val deliveryDependencies = Seq(
    "org.scalanlp" %% "breeze-process" % "0.3",
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