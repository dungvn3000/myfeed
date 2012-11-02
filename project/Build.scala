import sbt._
import Keys._
import Project._

object LinkerZBuild extends Build {

  lazy val linkerZ = Project("linkerz", file("."), settings = defaultSettings)

  lazy val linkerZCore = Project("linkerz_core", file("src/linkerz_core"), settings = defaultSettings).settings(
    libraryDependencies ++= coreDependencies
  )

  lazy val linkerZTest = Project("linkerz_test", file("src/linkerz_test"), settings = defaultSettings).settings(
    libraryDependencies ++= testDependencies
  ).dependsOn(linkerZCore)

  val coreDependencies = Seq(
    "commons-collections" % "commons-collections" % "3.2.1",
    "commons-digester" % "commons-digester" % "2.1"
  )

  val testDependencies = Seq(
    "junit" % "junit" % "4.10"
  )

}


