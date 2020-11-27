name := "esl-interview"

version := "0.1"

scalaVersion := "2.11.0"
val finchVersion = "0.26.0"
val circeVersion = "0.12.3"
val scalatestVersion = "3.0.5"

libraryDependencies ++= Seq(
  "com.twitter" %% "finagle-http"  % "20.10.0",
  "com.twitter" %% "finatra-http" % "20.10.0",
  "com.github.finagle" %% "finch-core" % "0.22.0",
  "com.github.finagle" %% "finch-circe" % "0.22.0",
  "io.circe" %% "circe-generic" % "0.9.0",
  "io.circe" %% "circe-parser" % "0.9.0",
  "org.scalatest"      %% "scalatest"    % scalatestVersion % "test"
)