name := """constat-web-java"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  cache,
  javaWs,
  "com.adrianhurt" %% "play-bootstrap3" % "0.4.2"
)
