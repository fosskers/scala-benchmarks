package benchmarks

import java.util.concurrent.TimeUnit

import scala.annotation.tailrec

import cats.data.Chain
import org.openjdk.jmh.annotations._
import scalaz.{IList, ICons, INil}

// --- //

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
class FoldClassBench {

  var list: List[Pair] = _
  var ilist: IList[Pair] = _
  var vector: Vector[Pair] = _
  var array: Array[Pair] = _
  var lazyList: LazyList[Pair] = _
  var chain: Chain[Pair] = _

  @Setup
  def setup: Unit = {
    list = List.range(1, 10000).map(n => Pair(n, n))
    ilist = IList.fromList(list)
    vector = Vector.range(1, 10000).map(n => Pair(n, n))
    array = Array.range(1, 10000).map(n => Pair(n, n))
    lazyList = LazyList.range(1, 10000).map(n => Pair(n, n))
    chain = Chain.fromSeq(list)
  }

  @Benchmark
  def iterFoldLeft: Pair = list.iterator.foldLeft(Pair(0,0))(_ + _)
  @Benchmark
  def iterFoldRight: Pair = list.iterator.foldRight(Pair(0,0))(_ + _)
  @Benchmark
  def iterWhile: Pair = {
    var n: Pair = Pair(0,0)
    val iter: Iterator[Pair] = list.iterator

    while (iter.hasNext) {
      n = n + iter.next
    }

    n
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
  def ilistFoldLeft: Pair = ilist.foldLeft(Pair(0,0))(_ + _)
  @Benchmark
  def ilistFoldRight: Pair = ilist.foldRight(Pair(0,0))(_ + _)
  @Benchmark
  def ilistTailrec: Pair = {
    @tailrec def work(l: IList[Pair], acc: Pair): Pair = l match {
      case ICons(h, tail) => work(tail, h + acc)
      case INil() => acc
    }

    work(ilist, Pair(0,0))
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
  def lazyListFoldLeft: Pair = lazyList.foldLeft(Pair(0,0))(_ + _)
  @Benchmark
  def lazyListFoldRight: Pair = lazyList.foldRight(Pair(0,0))(_ + _)
  @Benchmark
  def lazyListTailrec: Pair = {
    @tailrec def work(l: LazyList[Pair], acc: Pair): Pair = l match {
      case _ if l.isEmpty => acc
      case h #:: rest => work(rest, h + acc)
    }

    work(lazyList, Pair(0,0))
  }
  @Benchmark
  def lazyListWhile: Pair = {
    var i: Pair = Pair(0,0)
    var l: LazyList[Pair] = lazyList

    while (!l.isEmpty) {
      i = i + l.head
      l = l.tail
    }

    i
  }

  @Benchmark
  def chainFoldLeft: Pair = chain.foldLeft(Pair(0,0))(_ + _)
  @Benchmark
  def chainFoldRight: Pair = chain.foldRight(Pair(0,0))(_ + _)
  @Benchmark
  def chainTailrec: Pair = {
    @tailrec def work(l: Chain[Pair], acc: Pair): Pair = l.uncons match {
      case None => acc
      case Some((h, rest)) => work(rest, h + acc)
    }

    work(chain, Pair(0,0))
  }
  @Benchmark
  def chainWhile: Pair = {
    var i: Pair = Pair(0,0)
    var l: Chain[Pair] = chain
    var uc = l.uncons

    while (!uc.isEmpty) {
      i += uc.get._1
      l = uc.get._2
      uc = l.uncons
    }

    i
  }
}
