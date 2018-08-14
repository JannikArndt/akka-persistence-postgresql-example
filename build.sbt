name := "akka-persistence"

scalaVersion := "2.12.6"

version := "1.0"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.14",
  "com.typesafe.akka" %% "akka-testkit" % "2.5.14" % Test,
  "com.typesafe.akka" %% "akka-persistence" % "2.5.14",
  "org.json4s" %% "json4s-native" % "3.6.0",
  "com.okumin" %% "akka-persistence-sql-async" % "0.5.1",
  "com.github.mauricio" %% "postgresql-async" % "0.2.+",
  "org.scalatest" %% "scalatest" % "3.0.5" % Test,
  "org.slf4j" % "slf4j-simple" % "1.7.25",
)
