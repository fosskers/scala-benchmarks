package benchmarks

import java.util.concurrent.TimeUnit

import scala.annotation.tailrec

import org.openjdk.jmh.annotations._

// --- //

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
class FoldClassBench {

  var list: List[Pair] = _
  var vector: Vector[Pair] = _
  var array: Array[Pair] = _
  var stream: Stream[Pair] = _

  @Setup
  def setup: Unit = {
    list = List.range(1, 10000).map(n => Pair(n, n))
    vector = Vector.range(1, 10000).map(n => Pair(n, n))
    array = Array.range(1, 10000).map(n => Pair(n, n))
    stream = Stream.range(1, 10000).map(n => Pair(n, n))
  }

  @Benchmark
  def listFoldLeft: Pair = list.foldLeft(Pair(0,0))(_ + _)
  @Benchmark
  def listFoldRight: Pair = list.foldRight(Pair(0,0))(_ + _)
  @Benchmark
  def listTailrec: Pair = {
    @tailrec def work(l: List[Pair], acc: Pair): Pair = l match {
      case Nil => acc
      case h :: rest => work(rest, h + acc)
    }

    work(list, Pair(0,0))
  }
  @Benchmark
  def listWhile: Pair = {
    var i: Pair = Pair(0,0)
    var l: List[Pair] = list

    while (!l.isEmpty) {
      i = i + l.head
      l = l.tail
    }

    i
  }

  @Benchmark
  def vectorFoldLeft: Pair = vector.foldLeft(Pair(0,0))(_ + _)
  @Benchmark
  def vectorFoldRight: Pair = vector.foldRight(Pair(0,0))(_ + _)
  @Benchmark
  def vectorWhile: Pair = {
    var n: Pair = Pair(0,0)
    var i: Int = vector.length - 1

    while (i >= 0) {
      n = n + vector(i)
      i -= 1
    }

    n
  }

  @Benchmark
  def arrayFoldLeft: Pair = array.foldLeft(Pair(0,0))(_ + _)
  @Benchmark
  def arrayFoldRight: Pair = array.foldRight(Pair(0,0))(_ + _)
  @Benchmark
  def arrayWhile: Pair = {
    var n: Pair = Pair(0,0)
    var i: Int = array.length - 1

    while (i >= 0) {
      n = n + array(i)
      i -= 1
    }

    n
  }

  @Benchmark
  def streamFoldLeft: Pair = stream.foldLeft(Pair(0,0))(_ + _)
  @Benchmark
  def streamFoldRight: Pair = stream.foldRight(Pair(0,0))(_ + _)
  @Benchmark
  def streamTailrec: Pair = {
    @tailrec def work(l: Stream[Pair], acc: Pair): Pair = l match {
      case _ if l.isEmpty => acc
      case h #:: rest => work(rest, h + acc)
    }

    work(stream, Pair(0,0))
  }
  @Benchmark
  def streamWhile: Pair = {
    var i: Pair = Pair(0,0)
    var l: Stream[Pair] = stream

    while (!l.isEmpty) {
      i = i + l.head
      l = l.tail
    }

    i
  }

}
