name := """succubus"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "com.github.nscala-time" %% "nscala-time" % "1.6.0",  
  "net.debasishg" % "redisclient_2.9.2" % "2.6",
  "commons-httpclient" % "commons-httpclient" % "3.1",
  "org.scalaj" %% "scalaj-http" % "1.1.1"
)
