ThisBuild / organization := "com.example"
ThisBuild / scalaVersion := "3.2.0"
ThisBuild / version      := "0.1.0-SNAPSHOT"

lazy val http4sVersion  = "0.23.16"
lazy val circeVersion   = "0.14.1"
lazy val doobieVersion  = "1.0.0-RC1"

lazy val root = (project in file("."))
  .enablePlugins(SbtTwirl)
  .settings(
    name := "scala3-app-test",
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-dsl" % http4sVersion,
      "org.http4s" %% "http4s-circe" % http4sVersion,
      "org.http4s" %% "http4s-ember-server" % http4sVersion,
      "org.http4s" %% "http4s-ember-client" % http4sVersion,

      "io.circe" %% "circe-core"     % circeVersion,
      "io.circe" %% "circe-generic"  % circeVersion,
      "io.circe" %% "circe-parser"   % circeVersion,

      "mysql"        % "mysql-connector-java" % "8.0.30",
      "org.tpolecat" %% "doobie-core"      % doobieVersion,
    )
  )

TwirlKeys.templateImports := Seq.empty
Compile / TwirlKeys.compileTemplates / sourceDirectories := (Compile / unmanagedSourceDirectories).value
