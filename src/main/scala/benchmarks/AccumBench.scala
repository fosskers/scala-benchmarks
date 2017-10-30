package benchmarks

import java.util.concurrent.TimeUnit

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer

import org.openjdk.jmh.annotations._

// --- //

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
class AccumBench {

  var list0: List[Int] = _
  var list1: List[Pair] = _
  var arr0: Array[Int] = _
  var arr1: Array[Pair] = _

  @Setup
  def setup: Unit = {
    list0 = List.range(1, 10000)
    list1 = List.range(1, 10000).map(n => Pair(n, n))
    arr0 = Array.range(1, 10000)
    arr1 = Array.range(1, 10000).map(n => Pair(n, n))
  }

  @Benchmark
  def bufferInt: ListBuffer[Int] = {

    val buffer = new ListBuffer[Int]
    var i: Int = 0

    while (i < arr0.length) {
      buffer.append(arr0(i))
      i += 1
    }

    buffer
  }
  @Benchmark
  def bufferPair: ListBuffer[Pair] = {

    val buffer = new ListBuffer[Pair]
    var i: Int = 0

    while (i < arr1.length) {
      buffer.append(arr1(i))
      i += 1
    }

    buffer
  }

  @Benchmark
  def recursionPair: List[Pair] = {

    @tailrec def work(acc: List[Pair], ps: List[Pair]): List[Pair] = ps match {
      case Nil => acc
      case h :: rest => work(h :: acc, rest)
    }

    work(Nil, list1)
  }
  @Benchmark
  def recursionPairCasted: List[Pair] = {

    @tailrec def work(acc: List[Pair], ps: List[Pair]): List[Pair] = ps match {
      case Nil => acc
      case h :: rest => work(h :: acc, rest)
    }

    work(Nil, arr1.toList)
  }
  @Benchmark
  def recursionInt: List[Int] = {

    @tailrec def work(acc: List[Int], ps: List[Int]): List[Int] = ps match {
      case Nil => acc
      case h :: rest => work(h :: acc, rest)
    }

    work(Nil, list0)
  }
  @Benchmark
  def recursionIntCasted: List[Int] = {

    @tailrec def work(acc: List[Int], ps: List[Int]): List[Int] = ps match {
      case Nil => acc
      case h :: rest => work(h :: acc, rest)
    }

    work(Nil, arr0.toList)
  }



}
