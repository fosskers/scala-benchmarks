package object benchmarks {
  // Override references to `Stream` within `package benchmarks` to point to
  // `s.c.i.LazyList` instead of `s.c.i.Stream` which was deprecated in Scala
  // 2.13.
  //
  // Note that if for some reason this file is not included during compilation
  // (for example, if sbt fails to include the Scala 2.13-specific source
  // directory), then the benchmarks will still compile fine, albeit with
  // warnings that `Stream` is deprecated.
  type Stream[+A] = scala.collection.immutable.LazyList[A]
  val Stream = scala.collection.immutable.LazyList
}
