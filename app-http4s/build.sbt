ThisBuild / organization := "com.example"
ThisBuild / scalaVersion := "3.2.0"

lazy val http4sVersion  = "0.23.16"
lazy val circeVersion   = "0.14.1"
lazy val doobieVersion  = "1.0.0-RC1"

lazy val root = (project in file("."))
  .enablePlugins(SbtTwirl)
  .enablePlugins(JavaAppPackaging)
  .enablePlugins(EcrPlugin)
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
Docker / version    := "0.0.1"
Docker / maintainer := "sample@sample.com"
Docker / dockerBaseImage     := "amazoncorretto:8"
Docker / dockerExposedPorts  := Seq(8080, 8080)
Docker / daemonUserUid := None
Docker / daemonUser          := "daemon"

import com.amazonaws.regions.{Region, Regions}
Ecr / region           := Region.getRegion(Regions.AP_NORTHEAST_1)
Ecr / repositoryName   := "scala3-app-test"
Ecr / repositoryTags   := Seq(version.value, "latest")
Ecr / localDockerImage := (packageName in Docker).value + ":" + (version in Docker).value

// Create the repository before authentication takes place (optional)
// login in Ecr := ((login in Ecr) dependsOn (createRepository in Ecr)).value

// Authenticate and publish a local Docker image before pushing to ECR
// push in Ecr := ((push in Ecr) dependsOn (publishLocal in Docker, login in Ecr)).value

import ReleaseTransformations._
releaseVersionBump := sbtrelease.Version.Bump.Bugfix
releaseProcess := Seq[ReleaseStep](
  ReleaseStep(state => Project.extract(state).runTask(login in Ecr, state)._1),
  inquireVersions,
  runClean,
  setReleaseVersion,
  ReleaseStep(state => Project.extract(state).runTask(publishLocal in Docker, state)._1),
  ReleaseStep(state => Project.extract(state).runTask(push in Ecr, state)._1),
  commitReleaseVersion,
  tagRelease,
  setNextVersion,
  commitNextVersion,
  pushChanges
)
