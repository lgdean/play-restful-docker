import com.typesafe.sbt.packager.docker._

name := """play-restful-docker"""

version := "0.1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, JavaAppPackaging, DockerPlugin)

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

scalaVersion := "2.11.8"

resolvers += Resolver.mavenLocal
resolvers += Resolver.jcenterRepo
resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
resolvers += "ldaume OSS Public" at "https://maven.reinvent-software.de/nexus/content/repositories/public"

libraryDependencies ++= Seq(
  cache,
  javaWs,

  // Commons
  "org.apache.commons" % "commons-lang3" % "3.5",
  "com.google.guava" % "guava" % "20.0",
  "org.apache.commons" % "commons-collections4" % "4.1",
  "commons-io" % "commons-io" % "2.5",
  "org.unbescape" % "unbescape" % "1.1.4.RELEASE",

  // Json
  "com.jayway.jsonpath" % "json-path" % "2.2.0",

  // Testing
  "org.assertj" % "assertj-core" % "3.5.2" % "test",
  "org.assertj" % "assertj-guava" % "3.1.0" % "test" exclude("com.google.guava", "guava")
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

initialize := {
  val _ = initialize.value
  if (sys.props("java.specification.version") != "1.8")
    sys.error("Java 8 is required for this project.")
}

// --------------------
// ------ DOCKER ------
// --------------------
// build with activator docker:publishLocal

// change to smaller base image
dockerBaseImage := "frolvlad/alpine-oraclejdk8:latest"
dockerCommands := dockerCommands.value.flatMap {
  case cmd@Cmd("FROM", _) => List(cmd, Cmd("RUN", "apk update && apk add bash"))
  case other => List(other)
}

// setting a maintainer which is used for all packaging types</pre>
maintainer := "Leonard Daume"

// exposing the play ports
dockerExposedPorts in Docker := Seq(9000, 9443)

// publish to repo
//dockerRepository := Some("quay.io/")
//dockerUpdateLatest := true

// run this with: docker run -p 9000:9000 <name>:<version>
