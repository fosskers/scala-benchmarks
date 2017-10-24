package benchmarks

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

// --- //

case class Pair(col: Int, row: Int)

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
class SetBench {
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
  def withDistinct: Iterator[Pair] = smallList.map { case Pair(c,r) => Pair(c + 1, r) }.distinct.iterator
  @Benchmark
  def withDistinct2: Iterator[Pair] = bigList.map { case Pair(c,r) => Pair(c + 1, r) }.distinct.iterator
  @Benchmark
  def withDistinct3: Iterator[Pair] = hugeList.map { case Pair(c,r) => Pair(c + 1, r) }.distinct.iterator
  @Benchmark
  def withDistinct4: Iterator[Pair] = gargList.map { case Pair(c,r) => Pair(c + 1, r) }.distinct.iterator

  @Benchmark
  def withSet: Iterator[Pair] = smallList.map { case Pair(c,r) => Pair(c + 1, r) }.toSet.iterator
  @Benchmark
  def withSet2: Iterator[Pair] = bigList.map { case Pair(c,r) => Pair(c + 1, r) }.toSet.iterator
  @Benchmark
  def withSet3: Iterator[Pair] = hugeList.map { case Pair(c,r) => Pair(c + 1, r) }.toSet.iterator
  @Benchmark
  def withSet4: Iterator[Pair] = gargList.map { case Pair(c,r) => Pair(c + 1, r) }.toSet.iterator

}
