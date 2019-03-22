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
class MatchContainersBench {

  var list: List[Int] = _
  var ilist: IList[Int] = _
  var vec: Vector[Int] = _
  var arr: Array[Int] = _
  var seq: Seq[Int] = _
  var stream: Stream[Int] = _
  var chain: Chain[Int] = _

  @Setup
  def setup: Unit = {
    list = List.range(1, 10000)
    ilist = IList.fromList(list)
    vec = Vector.range(1, 10000)
    arr = Array.range(1, 10000)
    seq = Seq.range(1, 10000)
    stream = Stream.range(1, 10000)
    chain = Chain.fromSeq(list)
  }

  @Benchmark
  def lastListMatchCons: Option[Int] = {
    @tailrec def work(l: List[Int]): Option[Int] = l match {
      case Nil       => None
      case h :: Nil  => Some(h)
      case _ :: rest => work(rest)
    }

    work(list)
  }

  @Benchmark
  def lastListMatchGeneric: Option[Int] = {
    @tailrec def work(l: List[Int]): Option[Int] = l match {
      case Nil       => None
      case h +: Nil  => Some(h)
      case _ +: rest => work(rest)
    }

    work(list)
  }

  /* Unlike Haskell, `tail` on an empty list throws an Exception */

  @Benchmark
  def lastListIf: Option[Int] = {
    @tailrec def work(l: List[Int]): Option[Int] = {
      if (l.isEmpty) { None }
      else {
        val t = l.tail
        if (t.isEmpty) Some(l.head) else work(t)
      }
    }

    work(list)
  }

  @Benchmark
  def lastIListMatchCons: Option[Int] = {
    @tailrec def work(l: IList[Int]): Option[Int] = l match {
      case INil()           => None
      case ICons(h, INil()) => Some(h)
      case ICons(_, rest)   => work(rest)
    }

    work(ilist)
  }

  @Benchmark
  def lastVectorMatchGeneric: Option[Int] = {
    @tailrec def work(l: Vector[Int]): Option[Int] = l match {
      case Vector() => None
      case h +: Vector() => Some(h)
      case _ +: rest => work(rest)
    }

    work(vec)
  }

  @Benchmark
  def lastVectorIf: Option[Int] = {
    @tailrec def work(l: Vector[Int]): Option[Int] = {
      if (l.isEmpty) { None }
      else {
        val t = l.tail
        if (t.isEmpty) Some(l.head) else work(t)
      }
    }

    work(vec)
  }

  @Benchmark
  def lastArrayIf: Option[Int] = {
    @tailrec def work(l: Array[Int]): Option[Int] = {
      if (l.isEmpty) { None }
      else {
        val t = l.tail
        if (t.isEmpty) Some(l.head) else work(t)
      }
    }

    work(arr)
  }

  @Benchmark
  def lastSeqMatchGeneric: Option[Int] = {
    @tailrec def work(l: Seq[Int]): Option[Int] = l match {
      case Seq() => None
      case h +: Seq() => Some(h)
      case _ +: rest => work(rest)
    }

    work(seq)
  }

  @Benchmark
  def lastSeqIf: Option[Int] = {
    @tailrec def work(l: Seq[Int]): Option[Int] = {
      if (l.isEmpty) { None }
      else {
        val t = l.tail
        if (t.isEmpty) Some(l.head) else work(t)
      }
    }

    work(seq)
  }

  @Benchmark
  def lastStreamMatch: Option[Int] = {
    @tailrec def work(l: Stream[Int]): Option[Int] = l match {
      case Stream() => None
      case h #:: Stream() => Some(h)
      case _ #:: rest => work(rest)
    }

    work(stream)
  }

  @Benchmark
  def lastStreamMatchGeneric: Option[Int] = {
    @tailrec def work(l: Stream[Int]): Option[Int] = l match {
      case Stream() => None
      case h +: Stream() => Some(h)
      case _ +: rest => work(rest)
    }

    work(stream)
  }

  @Benchmark
  def lastStreamIf: Option[Int] = {
    @tailrec def work(l: Stream[Int]): Option[Int] = {
      if (l.isEmpty) { None }
      else {
        val t = l.tail
        if (t.isEmpty) Some(l.head) else work(t)
      }
    }

    work(stream)
  }

  @Benchmark
  def lastChainMatch: Option[Int] = {
    @tailrec def work(l: Chain[Int]): Option[Int] = l.uncons match {
      case None                      => None
      case Some((h, t)) if t.isEmpty => Some(h)
      case Some((_, rest))           => work(rest)
    }

    work(chain)
  }

  @Benchmark
  def lastChainMatchGeneric: Option[Int] = {
    @tailrec def work(l: Chain[Int]): Option[Int] = l match {
      case _ if l.isEmpty             => None
      case (h: Int) +: t if t.isEmpty => Some(h)
      case _ +: (rest: Chain[Int])    => work(rest)
    }

    work(chain)
  }

  @Benchmark
  def lastChainIf: Option[Int] = {
    @tailrec def work(l: Chain[Int]): Option[Int] = {
      if (l.isEmpty) { None }
      else {
        val (h, t) = l.uncons.get
        if (t.isEmpty) Some(h) else work(t)
      }
    }

    work(chain)
  }
}
