ThisBuild / organization := "com.example"
ThisBuild / scalaVersion := "3.2.0"

lazy val http4sVersion  = "0.23.16"
lazy val circeVersion   = "0.14.1"
lazy val doobieVersion  = "1.0.0-RC1"

lazy val root = (project in file("."))
  .enablePlugins(SbtTwirl)
  .enablePlugins(JavaAppPackaging)
  .enablePlugins(DockerPlugin)
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

// --- [ Dockerfileの定義 ] -----------------
// https://www.scala-sbt.org/sbt-native-packager/formats/docker.html
Docker / version    := "0.0.1"
Docker / maintainer := "sample@sample.com"
// amazoncorretto:11 (java -v 11) をDocker Imageのベースに使用
dockerBaseImage     := "amazoncorretto:11"
Docker / dockerExposedPorts  := Seq(8080, 8080)
// デフォルトでは以下が作成され、これを利用する。non-root user を実行ユーザーに指定することがベストプラクティスなため。
// daemonUser: "demiourgos728" / daemonUserUid: 1001
Docker / daemonUserUid       := None
Docker / daemonUser          := "daemon"

// --- [ Docker imageをpush先のECRを指定 ] ----------------
import com.amazonaws.regions.{ Region, Regions }
Ecr / region           := Region.getRegion(Regions.AP_NORTHEAST_1)
Ecr / repositoryName   := "scala3-app-test"
Ecr / repositoryTags   := Seq(version.value, "latest")
Ecr / localDockerImage := (Docker / packageName).value + ":" + (Docker / version).value

// --- [ リリースコマンド設定 ] ----------------
// https://github.com/sbt/sbt-release
import ReleaseTransformations._
// releaseVersionBump := sbtrelease.Version.Bump.Bugfix
releaseProcess := Seq[ReleaseStep](
  runClean,
  ReleaseStep(state => Project.extract(state).runTask(Ecr / login, state)._1),
  // inquireVersions,
  // setReleaseVersion,
  ReleaseStep(state => Project.extract(state).runTask(Docker / publishLocal, state)._1),
  ReleaseStep(state => Project.extract(state).runTask(Ecr / push, state)._1),
  // commitReleaseVersion,
  // tagRelease,
  // setNextVersion,
  // commitNextVersion,
  // pushChanges
)
