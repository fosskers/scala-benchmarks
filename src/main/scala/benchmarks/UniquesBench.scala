package benchmarks

import java.util.concurrent.{ TimeUnit, ConcurrentHashMap }

import scala.collection.mutable.{ Set => MSet, HashSet => MHashSet }
import scala.collection.JavaConverters._

import org.openjdk.jmh.annotations._

// --- //

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
class UniquesBench {

  var arr0: Array[Int] = _
  var arr1: Array[Int] = _
  var arr2: Array[Int] = _

  @Setup
  def setup: Unit = {
    arr0 = Array.range(0, 50) ++ Array.range(0, 50)
    arr1 = Array.range(0, 500) ++ Array.range(0, 500)
    arr2 = Array.range(0, 5000) ++ Array.range(0, 5000)
  }

  @Benchmark
  def hashmap100: ConcurrentHashMap[(Int, Int), Unit] = hashmap(arr0)
  @Benchmark
  def hashmap1000: ConcurrentHashMap[(Int, Int), Unit] = hashmap(arr1)
  @Benchmark
  def hashmap10000: ConcurrentHashMap[(Int, Int), Unit] = hashmap(arr2)

  def hashmap(arr: Array[Int]): ConcurrentHashMap[(Int, Int), Unit] = {
    val c = new ConcurrentHashMap[(Int, Int), Unit]
    var i: Int = 0
    val len: Int = arr.length

    while (i < len) {
      val n = arr(i)

      c.put((n, n), Unit)
      i += 1
    }

    c
  }

  @Benchmark
  def set100: Set[(Int, Int)] = set(arr0)
  @Benchmark
  def set1000: Set[(Int, Int)] = set(arr1)
  @Benchmark
  def set10000: Set[(Int, Int)] = set(arr2)

  def set(arr: Array[Int]): Set[(Int, Int)] = {
    var s: Set[(Int, Int)] = Set.empty
    var i: Int = 0
    val len: Int = arr.length

    while (i < len) {
      val n = arr(i)

      s = s + ((n, n))
      i += 1
    }

    s
  }

  @Benchmark
  def mset100: MSet[(Int, Int)] = mset(arr0)
  @Benchmark
  def mset1000: MSet[(Int, Int)] = mset(arr1)
  @Benchmark
  def mset10000: MSet[(Int, Int)] = mset(arr2)

  def mset(arr: Array[Int]): MSet[(Int, Int)] = {
    val s: MSet[(Int, Int)] = MSet.empty
    var i: Int = 0
    val len: Int = arr.length

    while (i < len) {
      val n = arr(i)

      s += ((n, n))
      i += 1
    }

    s
  }

}
