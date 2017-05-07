name := "project"

version := "1.0"

scalaVersion := "2.12.2"
val akkaVersion = "2.4.12"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream-kafka" % "0.13",
  "com.typesafe.akka" %% "akka-http" % "10.0.5"
)