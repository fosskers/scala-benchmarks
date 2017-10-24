name := """scala-benchmarks"""

version := "1.0.0"

scalaVersion := "2.12.3"

/* To run benchmarks:
    jmh:run -t 1 -f 1 -wi 5 -i 5 .*Bench.*
 */
enablePlugins(JmhPlugin)
