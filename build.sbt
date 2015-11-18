name := """play-restful-docker"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, JavaAppPackaging, DockerPlugin)

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

scalaVersion := "2.11.7"

resolvers += Resolver.mavenLocal

libraryDependencies ++= Seq(
  cache,
  javaWs,
  "org.assertj" % "assertj-core" % "3.1.0" % "test",
  "org.apache.commons" % "commons-lang3" % "3.4"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

// --------------------
// ------ DOCKER ------
// --------------------
// build with activator:publishLocal

// setting a maintainer which is used for all packaging types</pre>
maintainer := "Me"

// exposing the play ports
dockerExposedPorts in Docker := Seq(9000, 9443)

// publish to repo
//dockerRepository := Some("quay.io/")
//dockerUpdateLatest := true

// run this with: docker run -p 9000:9000 <name>:<version>