package benchmarks

import java.util.concurrent.TimeUnit

import scala.annotation.tailrec

import org.openjdk.jmh.annotations._

// --- //

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
class MatchContainersBench {

  var list: List[Int] = _
  var vec: Vector[Int] = _
  var arr: Array[Int] = _
  var seq: Seq[Int] = _
  var stream: Stream[Int] = _

  @Setup
  def setup: Unit = {
    list = List.range(1, 10000)
    vec = Vector.range(1, 10000)
    arr = Array.range(1, 10000)
    seq = Seq.range(1, 10000)
    stream = Stream.range(1, 10000)
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

}
