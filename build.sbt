name := "moviesinthesummer"

version := "0.1"

scalaVersion := "2.11.7"

lazy val root = (project in file(".")).enablePlugins(PlayScala, BuildInfoPlugin).
  settings(
      buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
      buildInfoPackage := "sbtbuild",
      buildInfoObject := "Info",
      buildInfoOptions += BuildInfoOption.ToJson,
      buildInfoOptions += BuildInfoOption.BuildTime
  )

routesGenerator := InjectedRoutesGenerator

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

libraryDependencies ++= Seq(
    "com.typesafe.play" %% "play-slick" % "2.0.0",
    "com.typesafe.play" %% "play-slick-evolutions" % "2.0.0",
    "com.h2database" % "h2" % "1.4.187",
    "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0" % "test",
    specs2 % Test,
    "com.github.tototoshi" %% "slick-joda-mapper" % "2.2.0"
)

resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"

fork in run := true