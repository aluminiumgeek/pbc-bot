resolvers += Resolver.sonatypeRepo("snapshots")

lazy val commonSettings = Seq(
  organization := "com.eugenzyx",
  name         := "bot",
  version      := "0.1-SNAPSHOT",
  scalaVersion := "2.11.7"
)

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      "info.mukel" %% "telegrambot4s" % "1.0.3-SNAPSHOT",
      "org.scalaj" %% "scalaj-http" % "2.2.1",
      "net.liftweb" %% "lift-json" % "2.6+",
      "com.github.scopt" %% "scopt" % "3.4.0"
    )
  )
