ThisBuild / organization := "com.example"
// ThisBuild / scalaVersion := "2.13.8"
ThisBuild / scalaVersion := "3.2.0"
ThisBuild / version      := "0.1.0-SNAPSHOT"

lazy val http4sVersion = "0.23.16"
lazy val doobieVersion  = "1.0.0-RC1"

lazy val root = (project in file("."))
  .enablePlugins(SbtTwirl)
  .settings(
    name := "scala3-app-test",
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-dsl" % http4sVersion,
      "org.http4s" %% "http4s-ember-server" % http4sVersion,
      "org.http4s" %% "http4s-ember-client" % http4sVersion,

      "mysql"        % "mysql-connector-java" % "8.0.30",
      "org.tpolecat" %% "doobie-core"      % doobieVersion,
    )
  )

// unmanagedSourceDirectories in Compile += (baseDirectory { _ / "src" / "main" / "twirl" }).value
// TwirlKeys.templateImports += "src.main.twirl._"
// Compile / TwirlKeys.compileTemplates / sourceDirectories := (Compile / unmanagedSourceDirectories).value

TwirlKeys.templateImports := Seq.empty
// sourceDirectories in (Compile, TwirlKeys.compileTemplates) := Seq(baseDirectory.value / "src")
Compile / TwirlKeys.compileTemplates / sourceDirectories := (Compile / unmanagedSourceDirectories).value
