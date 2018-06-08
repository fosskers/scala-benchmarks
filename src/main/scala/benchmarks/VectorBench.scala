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
class VectorBench {

  def array(n: Int): Array[Int] = {
    val a: Array[Int] = new Array(n)
    var i: Int = 0

    while (i < n) {
      a(i) = i
      i += 1
    }

    a
  }

  def abuilder(n: Int): Array[Int] = {
    val b = new ArrayBuilder.ofInt
    var i: Int = 0

    /* Cheat and tell the builder how big you expect the result to be.
     * Introduces a ~2x slowdown vs Array.
     *
     * Underestimating the hint (say by 2) will introduce about a 4x
     * slowdown. Not hinting at all is about 5x slower overall.
     *
     * Rule of thumb: if you hint, try to overshoot.
     */
    b.sizeHint(n * 2)

    while (i < n) {
      b += i
      i += 1
    }

    b.result
  }

  def vector(n: Int): Vector[Int] = {
    val v = Vector.empty[Int]
    var i: Int = 0

    while (i < n) {
      i +: v
      i += 1
    }

    v
  }

  def vbuilder(n: Int): Vector[Int] = {
    val b = new VectorBuilder[Int]
    var i: Int = 0

    while (i < n) {
      b += i
      i += 1
    }

    b.result
  }

  def list(n: Int): List[Int] = {

    @tailrec def work(acc: List[Int], m: Int): List[Int] = m match {
      case _ if m == n => acc
      case _ => work(m :: acc, m + 1)
    }

    work(Nil, 0)
  }

  def ilist(n: Int): IList[Int] = {

    @tailrec def work(acc: IList[Int], m: Int): IList[Int] = m match {
      case _ if m == n => acc
      case _ => work(ICons(m, acc), m + 1)
    }

    work(INil(), 0)

  }

  def buffer(n: Int): List[Int] = {
    val b = new ListBuffer[Int]
    var i: Int = 0

    while (i < n) {
      i +=: b
      i += 1
    }

    b.result
  }

  def foryield(n: Int): IndexedSeq[Int] = for (i <- 0 to n) yield i

  @Benchmark
  def array1000: Array[Int] = array(1000)
  @Benchmark
  def array10000: Array[Int] = array(10000)
  @Benchmark
  def array100000: Array[Int] = array(100000)

  @Benchmark
  def abuilder1000: Array[Int] = abuilder(1000)
  @Benchmark
  def abuilder10000: Array[Int] = abuilder(10000)
  @Benchmark
  def abuilder100000: Array[Int] = abuilder(100000)

  @Benchmark
  def vector1000: Vector[Int] = vector(1000)
  @Benchmark
  def vector10000: Vector[Int] = vector(10000)
  @Benchmark
  def vector100000: Vector[Int] = vector(100000)

  @Benchmark
  def vbuilder1000: Vector[Int] = vbuilder(1000)
  @Benchmark
  def vbuilder10000: Vector[Int] = vbuilder(10000)
  @Benchmark
  def vbuilder100000: Vector[Int] = vbuilder(100000)

  @Benchmark
  def list1000: List[Int] = list(1000)
  @Benchmark
  def list10000: List[Int] = list(10000)
  @Benchmark
  def list100000: List[Int] = list(100000)

  @Benchmark
  def ilist1000: IList[Int] = ilist(1000)
  @Benchmark
  def ilist10000: IList[Int] = ilist(10000)
  @Benchmark
  def ilist100000: IList[Int] = ilist(100000)

  @Benchmark
  def buffer1000: List[Int] = buffer(1000)
  @Benchmark
  def buffer10000: List[Int] = buffer(10000)
  @Benchmark
  def buffer100000: List[Int] = buffer(100000)

  @Benchmark
  def foryield1000: IndexedSeq[Int] = foryield(1000)
  @Benchmark
  def foryield10000: IndexedSeq[Int] = foryield(10000)
  @Benchmark
  def foryield100000: IndexedSeq[Int] = foryield(100000)

}
