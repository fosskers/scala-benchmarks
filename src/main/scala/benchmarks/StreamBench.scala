package benchmarks

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

// --- //

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
class StreamBench {

  var list0: List[Int] = _
  var list1: List[Int] = _
  var list2: List[Int] = _
  var vec0: Vector[Int] = _
  var vec1: Vector[Int] = _
  var vec2: Vector[Int] = _
  var arr0: Array[Int] = _
  var arr1: Array[Int] = _
  var arr2: Array[Int] = _

  @Setup
  def setup: Unit = {
    list0 = List.range(1, 1000)
    list1 = List.range(1, 10000)
    list2 = List.range(1, 100000)
    vec0 = Vector.range(1, 1000)
    vec1 = Vector.range(1, 10000)
    vec2 = Vector.range(1, 100000)
    arr0 = Array.range(1, 1000)
    arr1 = Array.range(1, 10000)
    arr2 = Array.range(1, 100000)
  }

  @Benchmark
  def streamOps0: Int = Stream.range(1, 1000).map(_ + 1).filter(_ % 2 == 0).map(_ * 2).max
  @Benchmark
  def streamOps1: Int = Stream.range(1, 10000).map(_ + 1).filter(_ % 2 == 0).map(_ * 2).max
  @Benchmark
  def streamOps2: Int = Stream.range(1, 100000).map(_ + 1).filter(_ % 2 == 0).map(_ * 2).max

  @Benchmark
  def iterOps0: Int = Iterator.range(1, 1000).map(_ + 1).filter(_ % 2 == 0).map(_ * 2).max
  @Benchmark
  def iterOps1: Int = Iterator.range(1, 10000).map(_ + 1).filter(_ % 2 == 0).map(_ * 2).max
  @Benchmark
  def iterOps2: Int = Iterator.range(1, 100000).map(_ + 1).filter(_ % 2 == 0).map(_ * 2).max

  @Benchmark
  def listOps0: Int = list0.map(_ + 1).filter(_ % 2 == 0).map(_ * 2).max
  @Benchmark
  def listOps1: Int = list1.map(_ + 1).filter(_ % 2 == 0).map(_ * 2).max
  @Benchmark
  def listOps2: Int = list2.map(_ + 1).filter(_ % 2 == 0).map(_ * 2).max

  @Benchmark
  def vectorOps0: Int = vec0.map(_ + 1).filter(_ % 2 == 0).map(_ * 2).max
  @Benchmark
  def vectorOps1: Int = vec1.map(_ + 1).filter(_ % 2 == 0).map(_ * 2).max
  @Benchmark
  def vectorOps2: Int = vec2.map(_ + 1).filter(_ % 2 == 0).map(_ * 2).max

  @Benchmark
  def arrayOps0: Int = arr0.map(_ + 1).filter(_ % 2 == 0).map(_ * 2).max
  @Benchmark
  def arrayOps1: Int = arr1.map(_ + 1).filter(_ % 2 == 0).map(_ * 2).max
  @Benchmark
  def arrayOps2: Int = arr2.map(_ + 1).filter(_ % 2 == 0).map(_ * 2).max

}
