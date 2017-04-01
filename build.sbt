val ScalaVer = "2.12.1"

val Cats = "0.9.0"

lazy val commonSettings = Seq(
  name    := "typeclass-webinar"
, version := "0.1.0"
, scalaVersion := ScalaVer
, libraryDependencies += "org.typelevel"  %% "cats" % Cats
, scalacOptions ++= Seq(
      "-deprecation",
      "-encoding", "UTF-8",
      "-feature",
      "-language:existentials",
      "-language:higherKinds",
      "-language:implicitConversions",
      "-language:experimental.macros",
      "-unchecked",
      // "-Xfatal-warnings",
      "-Xlint",
      // "-Yinline-warnings",
      "-Ywarn-dead-code",
      "-Xfuture",
      "-Ypartial-unification")
)

lazy val root = (project in file("."))
  .settings(commonSettings)
  .settings(
    initialCommands := "import typeclasswebinar._"
  )
