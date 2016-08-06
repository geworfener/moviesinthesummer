logLevel := Level.Warn

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.5.0")

addSbtPlugin("com.jamesward" %% "play-auto-refresh" % "0.0.14")

addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.6.1")