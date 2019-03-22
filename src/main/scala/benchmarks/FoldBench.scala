package benchmarks

import java.util.concurrent.TimeUnit

import scala.annotation.tailrec

import cats.data.Chain
import org.openjdk.jmh.annotations._
import scalaz.{IList, ICons, INil, EphemeralStream => EStream}

// --- //

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
class FoldBench {

  var list: List[Int] = _
  var ilist: IList[Int] = _
  var vector: Vector[Int] = _
  var array: Array[Int] = _
  var stream: Stream[Int] = _
  var estream: EStream[Int] = _
  var chain: Chain[Int] = _

  @Setup
  def setup: Unit = {
    list = List.range(1, 10000)
    ilist = IList.fromList(list)
    vector = Vector.range(1, 10000)
    array = Array.range(1, 10000)
    stream = Stream.range(1, 10000)
    estream = EStream.range(1, 10000)
    chain = Chain.fromSeq(list)
  }

  @Benchmark
  def iterFoldLeft: Int = list.iterator.foldLeft(0)(_ + _)
  @Benchmark
  def iterFoldRight: Int = list.iterator.foldRight(0)(_ + _)
  @Benchmark
  def iterWhile: Int = {
    var n: Int = 0
    val iter: Iterator[Int] = list.iterator

    while (iter.hasNext) {
      n += iter.next
    }

    n
  }

  @Benchmark
  def listFoldLeft: Int = list.foldLeft(0)(_ + _)
  @Benchmark
  def listFoldRight: Int = list.foldRight(0)(_ + _)
  @Benchmark
  def listTailrec: Int = {
    @tailrec def work(l: List[Int], acc: Int): Int = l match {
      case h :: rest => work(rest, h + acc)
      case Nil => acc
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
  def ilistFoldLeft: Int = ilist.foldLeft(0)(_ + _)
  @Benchmark
  def ilistFoldRight: Int = ilist.foldRight(0)(_ + _)
  @Benchmark
  def ilistTailrec: Int = {
    @tailrec def work(l: IList[Int], acc: Int): Int = l match {
      case ICons(h, tail) => work(tail, h + acc)
      case INil() => acc
    }

    work(ilist, 0)
  }

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

  @Benchmark
  def estreamFoldLeft: Int = estream.foldLeft(0) { acc => { n => acc + n } }
  @Benchmark
  def estreamFoldRight: Int = estream.foldRight(0) { n => { acc => n + acc } }

  @Benchmark
  def chainFoldLeft: Int = chain.foldLeft(0)(_ + _)
  @Benchmark
  def chainFoldRight: Int = chain.foldRight(0)(_ + _)
  @Benchmark
  def chainTailrec: Int = {
    @tailrec def work(l: Chain[Int], acc: Int): Int = l.uncons match {
      case None            => acc
      case Some((h, rest)) => work(rest, h + acc)
    }

    work(chain, 0)
  }
  @Benchmark
  def chainWhile: Int = {
    var i: Int = 0
    var l: Chain[Int] = chain
    var uc = l.uncons

    while (!uc.isEmpty) {
      i += uc.get._1
      l = uc.get._2
      uc = l.uncons
    }

    i
  }
}
