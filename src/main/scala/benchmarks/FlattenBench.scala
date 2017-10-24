package benchmarks

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

// --- //

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
class FlattenBench {
  var smallList: List[Pair] = _
  var bigList: List[Pair] = _
  var hugeList: List[Pair] = _
  var gargList: List[Pair] = _

  @Setup
  def setup(): Unit = {
    smallList = (List.range(1, 100) ++ List.range(1, 100)).map(n => Pair(n,n))
    bigList = (List.range(1, 1000) ++ List.range(1, 1000)).map(n => Pair(n,n))
    hugeList = (List.range(1, 10000) ++ List.range(1, 10000)).map(n => Pair(n,n))
    gargList = (List.range(1, 100000) ++ List.range(1, 100000)).map(n => Pair(n,n))
  }

  @Benchmark
  def mapFlatten1: List[Pair] = smallList.map { c => List(c) }.flatten
  @Benchmark
  def mapFlatten2: List[Pair] = bigList.map { c => List(c) }.flatten
  @Benchmark
  def mapFlatten3: List[Pair] = hugeList.map { c => List(c) }.flatten
  @Benchmark
  def mapFlatten4: List[Pair] = gargList.map { c => List(c) }.flatten

  @Benchmark
  def flatMap1: List[Pair] = smallList.flatMap { c => List(c) }
  @Benchmark
  def flatMap2: List[Pair] = bigList.flatMap { c => List(c) }
  @Benchmark
  def flatMap3: List[Pair] = hugeList.flatMap { c => List(c) }
  @Benchmark
  def flatMap4: List[Pair] = gargList.flatMap { c => List(c) }

}
