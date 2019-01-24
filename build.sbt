name := """scala-benchmarks"""

version := "1.0.0"

scalaVersion := "2.12.8"

crossScalaVersions := Seq(scalaVersion.value, "2.13.0-M5")

scalacOptions := Seq(
  "-opt:l:inline",
  "-opt-inline-from:**",
  "-deprecation",
  "-Ywarn-value-discard",
  "-Ywarn-unused:imports",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen"
)

Compile / unmanagedSourceDirectories ++= {
  val mainSourceDir = baseDirectory.value / "src" / "main" / "scala"
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, 13)) =>
      Seq(file(mainSourceDir.getPath + "-2.13"))
    case _ => Nil
  }
}

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % "7.2.27"
)

/* To run benchmarks:
    jmh:run -t 1 -f 1 -wi 5 -i 5 .*Bench.*
 */
enablePlugins(JmhPlugin)
