name := """api-constat-scala"""


version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  ws,
  //"com.typesafe.play" % "play-streams-experimental_2.11" % "2.4.0-M3",
  "com.softwaremill" %% "reactive-kafka" % "0.5.0",
  "com.typesafe.akka" %% "akka-stream-experimental" % "1.0-RC2",
  "org.reactivemongo" %% "play2-reactivemongo" % "0.10.5.0.akka23"
)
