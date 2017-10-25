package benchmarks

import java.util.concurrent.TimeUnit

import scala.annotation.tailrec

import org.openjdk.jmh.annotations._

// --- //

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
class FoldBench {

  var list: List[Int] = _
  var vector: Vector[Int] = _
  var array: Array[Int] = _
  var stream: Stream[Int] = _

  @Setup
  def setup: Unit = {
    list = List.range(1, 10000)
    vector = Vector.range(1, 10000)
    array = Array.range(1, 10000)
    stream = Stream.range(1, 10000)
  }

  @Benchmark
  def iterFoldLeft: Int = Iterator.range(1, 10000).foldLeft(0)(_ + _)
  @Benchmark
  def iterFoldRight: Int = Iterator.range(1, 10000).foldRight(0)(_ + _)

  @Benchmark
  def listFoldLeft: Int = list.foldLeft(0)(_ + _)
  @Benchmark
  def listFoldRight: Int = list.foldRight(0)(_ + _)
  @Benchmark
  def listTailrec: Int = {
    @tailrec def work(l: List[Int], acc: Int): Int = l match {
      case Nil => acc
      case h :: rest => work(rest, h + acc)
    }

    work(list, 0)
  }
  @Benchmark
  def listWhile: Int = {
    var i: Int = 0
    var l: List[Int] = list

    while (!l.isEmpty) {
      i += l.head
      l = l.tail
    }

    i
  }
  @Benchmark
  def listSum: Int = list.sum

  @Benchmark
  def vectorFoldLeft: Int = vector.foldLeft(0)(_ + _)
  @Benchmark
  def vectorFoldRight: Int = vector.foldRight(0)(_ + _)
  @Benchmark
  def vectorWhile: Int = {
    var n: Int = 0
    var i: Int = vector.length - 1

    while (i >= 0) {
      n += vector(i)
      i -= 1
    }

    n
  }
  @Benchmark
  def vectorSum: Int = vector.sum

  @Benchmark
  def arrayFoldLeft: Int = array.foldLeft(0)(_ + _)
  @Benchmark
  def arrayFoldRight: Int = array.foldRight(0)(_ + _)
  @Benchmark
  def arrayWhile: Int = {
    var n: Int = 0
    var i: Int = array.length - 1

    while (i >= 0) {
      n += array(i)
      i -= 1
    }

    n
  }
  @Benchmark
  def arraySum: Int = array.sum

  @Benchmark
  def streamFoldLeft: Int = stream.foldLeft(0)(_ + _)
  @Benchmark
  def streamFoldRight: Int = stream.foldRight(0)(_ + _)
  @Benchmark
  def streamTailrec: Int = {
    @tailrec def work(l: Stream[Int], acc: Int): Int = l match {
      case _ if l.isEmpty => acc
      case h #:: rest => work(rest, h + acc)
    }

    work(stream, 0)
  }
  @Benchmark
  def streamWhile: Int = {
    var i: Int = 0
    var l: Stream[Int] = stream

    while (!l.isEmpty) {
      i += l.head
      l = l.tail
    }

    i
  }
  @Benchmark
  def streamSum: Int = stream.sum

}
