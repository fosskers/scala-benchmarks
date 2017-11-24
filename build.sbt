name := """scala-benchmarks"""

version := "1.0.0"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % "7.2.16"
)

/* To run benchmarks:
    jmh:run -t 1 -f 1 -wi 5 -i 5 .*Bench.*
 */
enablePlugins(JmhPlugin)

/*
 QUESTIONS:

 What is Vector good for?
 What is Stream good for?
 Where does List beat Array?

 */
