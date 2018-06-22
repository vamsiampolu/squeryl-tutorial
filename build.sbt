organization := "com.example"
scalaVersion := "2.12.6"
version      := "0.1.0-SNAPSHOT"
name         := "squeryl-tutorial"

libraryDependencies ++= Seq(
  "org.squeryl" %% "squeryl" % "0.9.7",
  "com.h2database" % "h2" % "1.2.127"
)
