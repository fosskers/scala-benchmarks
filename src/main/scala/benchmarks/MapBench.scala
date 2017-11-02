package benchmarks

import java.util.concurrent.TimeUnit

import scala.annotation.tailrec

import org.openjdk.jmh.annotations._

// --- //

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
class MapBench {

  var list0: List[Int] = _
  var list1: List[Int] = _
  var list2: List[Int] = _
  var array0: Array[Int] = _
  var array1: Array[Int] = _
  var array2: Array[Int] = _

  var listC0: List[Pair] = _
  var listC1: List[Pair] = _
  var listC2: List[Pair] = _
  var arrayC0: Array[Pair] = _
  var arrayC1: Array[Pair] = _
  var arrayC2: Array[Pair] = _

  @Setup
  def setup: Unit = {
    list0 = List.range(1, 100)
    list1 = List.range(1, 1000)
    list2 = List.range(1, 10000)
    array0 = Array.range(1, 100)
    array1 = Array.range(1, 1000)
    array2 = Array.range(1, 10000)

    listC0 = list0.map(n => Pair(n, n))
    listC1 = list1.map(n => Pair(n, n))
    listC2 = list2.map(n => Pair(n, n))
    arrayC0 = array0.map(n => Pair(n, n))
    arrayC1 = array1.map(n => Pair(n, n))
    arrayC2 = array2.map(n => Pair(n, n))
  }

  def arrayWhile(arr: Array[Int]): Array[Int] = {
    val newArr: Array[Int] = Array.ofDim(arr.length)
    var i: Int = 0

    while (i < arr.length) {
      newArr(i) = arr(i) * 37
      i += 1
    }

    newArr
  }

  def arrayWhileClass(arr: Array[Pair]): Array[Pair] = {
    val newArr: Array[Pair] = Array.ofDim(arr.length)
    var i: Int = 0

    while (i < arr.length) {
      val p: Pair = arr(i)
      newArr(i) = Pair(p.col * 37, p.row * 37)
      i += 1
    }

    newArr
  }

  def listMap(l: List[Int]): List[Int] = l.map(_ * 37)

  def listMapClass(l: List[Pair]): List[Pair] = l.map { case Pair(c, r) => Pair(c * 37, r * 37) }

  @Benchmark
  def list100: List[Int] = listMap(list0)
  @Benchmark
  def list1000: List[Int] = listMap(list1)
  @Benchmark
  def list10000: List[Int] = listMap(list2)

  @Benchmark
  def listClass100: List[Pair] = listMapClass(listC0)
  @Benchmark
  def listClass1000: List[Pair] = listMapClass(listC1)
  @Benchmark
  def listClass10000: List[Pair] = listMapClass(listC2)

  @Benchmark
  def array100: Array[Int] = arrayWhile(array0)
  @Benchmark
  def array1000: Array[Int] = arrayWhile(array1)
  @Benchmark
  def array10000: Array[Int] = arrayWhile(array2)

  @Benchmark
  def arrayClass100: Array[Pair] = arrayWhileClass(arrayC0)
  @Benchmark
  def arrayClass1000: Array[Pair] = arrayWhileClass(arrayC1)
  @Benchmark
  def arrayClass10000: Array[Pair] = arrayWhileClass(arrayC2)

}
