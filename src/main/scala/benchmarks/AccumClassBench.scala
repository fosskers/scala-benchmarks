package benchmarks

import java.util.concurrent.TimeUnit

import scala.annotation.tailrec
import scala.collection.immutable.VectorBuilder
import scala.collection.mutable.{ArrayBuilder, ListBuffer}

import org.openjdk.jmh.annotations._
import scalaz.{IList, ICons, INil}

// --- //

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
class AccumClassBench {

  def array(n: Int): Array[Pair] = {
    val a: Array[Pair] = new Array(n)
    var i: Int = 0

    while (i < n) {
      a(i) = Pair(i, i)
      i += 1
    }

    a
  }

  def abuilder(n: Int): Array[Pair] = {
    val b = ArrayBuilder.make[Pair]
    var i: Int = 0

    b.sizeHint(n * 2)

    while (i < n) {
      b += Pair(i, i)
      i += 1
    }

    b.result
  }

  def vector(n: Int): Vector[Pair] = {
    val v = Vector.empty[Pair]
    var i: Int = 0

    while (i < n) {
      Pair(i, i) +: v
      i += 1
    }

    v
  }

  def vbuilder(n: Int): Vector[Pair] = {
    val b = new VectorBuilder[Pair]
    var i: Int = 0

    while (i < n) {
      b += Pair(i, i)
      i += 1
    }

    b.result
  }

  def list(n: Int): List[Pair] = {

    @tailrec def work(acc: List[Pair], m: Int): List[Pair] = m match {
      case _ if m == n => acc
      case _ => work(Pair(m, m) :: acc, m + 1)
    }

    work(Nil, 0)
  }

  def ilist(n: Int): IList[Pair] = {

    @tailrec def work(acc: IList[Pair], m: Int): IList[Pair] = m match {
      case _ if m == n => acc
      case _ => work(ICons(Pair(m, m), acc), m + 1)
    }

    work(INil(), 0)
  }

  def buffer(n: Int): List[Pair] = {
    val b = new ListBuffer[Pair]
    var i: Int = 0

    while (i < n) {
      Pair(i, i) +=: b
      i += 1
    }

    b.result
  }

  def foryield(n: Int): IndexedSeq[Pair] = for (i <- 0 to n) yield Pair(i, i)

  @Benchmark
  def array1000: Array[Pair] = array(1000)
  @Benchmark
  def array10000: Array[Pair] = array(10000)
  @Benchmark
  def array100000: Array[Pair] = array(100000)

  @Benchmark
  def abuilder1000: Array[Pair] = abuilder(1000)
  @Benchmark
  def abuilder10000: Array[Pair] = abuilder(10000)
  @Benchmark
  def abuilder100000: Array[Pair] = abuilder(100000)

  @Benchmark
  def vector1000: Vector[Pair] = vector(1000)
  @Benchmark
  def vector10000: Vector[Pair] = vector(10000)
  @Benchmark
  def vector100000: Vector[Pair] = vector(100000)

  @Benchmark
  def vbuilder1000: Vector[Pair] = vbuilder(1000)
  @Benchmark
  def vbuilder10000: Vector[Pair] = vbuilder(10000)
  @Benchmark
  def vbuilder100000: Vector[Pair] = vbuilder(100000)

  @Benchmark
  def list1000: List[Pair] = list(1000)
  @Benchmark
  def list10000: List[Pair] = list(10000)
  @Benchmark
  def list100000: List[Pair] = list(100000)

  @Benchmark
  def ilist1000: IList[Pair] = ilist(1000)
  @Benchmark
  def ilist10000: IList[Pair] = ilist(10000)
  @Benchmark
  def ilist100000: IList[Pair] = ilist(100000)

  @Benchmark
  def buffer1000: List[Pair] = buffer(1000)
  @Benchmark
  def buffer10000: List[Pair] = buffer(10000)
  @Benchmark
  def buffer100000: List[Pair] = buffer(100000)

  @Benchmark
  def foryield1000: IndexedSeq[Pair] = foryield(1000)
  @Benchmark
  def foryield10000: IndexedSeq[Pair] = foryield(10000)
  @Benchmark
  def foryield100000: IndexedSeq[Pair] = foryield(100000)

}
