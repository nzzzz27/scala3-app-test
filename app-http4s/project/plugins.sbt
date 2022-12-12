addSbtPlugin("com.github.sbt" % "sbt-native-packager" % "1.9.11")
addSbtPlugin("com.typesafe.play" % "sbt-twirl" % "1.6.0-M7")
addSbtPlugin("com.mintbeans" % "sbt-ecr" % "0.16.0")
addSbtPlugin("com.github.sbt" % "sbt-release" % "1.1.0")

ThisBuild / libraryDependencySchemes ++= Seq(
  "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always
)
