package benchmarks

import java.util.concurrent.TimeUnit

import cats.data.Chain
import cats.instances.int._
import cats.syntax.foldable._
import org.openjdk.jmh.annotations._
import scalaz.{IList, EphemeralStream}
import scalaz.Scalaz._

// --- //

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
class StreamBench {

  var list1: List[Int] = _
  var list2: List[Int] = _
  var ilist1: IList[Int] = _
  var ilist2: IList[Int] = _
  var vec1: Vector[Int] = _
  var vec2: Vector[Int] = _
  var arr1: Array[Int] = _
  var arr2: Array[Int] = _
  var str1: Stream[Int] = _
  var str2: Stream[Int] = _
  var estr1: EphemeralStream[Int] = _
  var estr2: EphemeralStream[Int] = _
  var chain1: Chain[Int] = _
  var chain2: Chain[Int] = _

  @Setup
  def setup: Unit = {
    list1 = List.range(1, 10000)
    list2 = List.range(10000, 1, -1)
    ilist1 = IList.fromList(list1)
    ilist2 = IList.fromList(list2)
    vec1 = Vector.range(1, 10000)
    vec2 = Vector.range(10000, 1, -1)
    arr1 = Array.range(1, 10000)
    arr2 = Array.range(10000, 1, -1)
    str1 = Stream.range(1, 10000)
    str2 = Stream.range(10000, 1, -1)
    estr1 = EphemeralStream.range(1, 10000)
    estr2 = EphemeralStream.fromStream(str2)
    chain1 = Chain.fromSeq(list1)
    chain2 = Chain.fromSeq(list2)
  }

  @Benchmark
  def streamMax: Int = str1.map(_ + 1).filter(_ % 2 == 0).map(_ * 2).max
  @Benchmark
  def streamHead: Int = str1.map(_ + 1).filter(_ % 2 == 0).map(_ * 2).head
  @Benchmark
  def streamReverse: Int = str1.reverse.head
  @Benchmark
  def streamSort: Int = str2.map(_ + 1).filter(_ % 2 == 0).map(_ * 2).sorted.head

  @Benchmark
  def ephemeralStreamMax: Option[Int] = estr1.map(_ + 1).filter(_ % 2 == 0).map(_ * 2).maximum
  @Benchmark
  def ephemeralStreamHead: Option[Int] = estr1.map(_ + 1).filter(_ % 2 == 0).map(_ * 2).headOption
  @Benchmark
  def ephemeralStreamReverse: Option[Int] = estr1.reverse.headOption

  @Benchmark
  def iterMax: Int = list1.iterator.map(_ + 1).filter(_ % 2 == 0).map(_ * 2).max
  @Benchmark
  def iterHead: Int = list1.iterator.map(_ + 1).filter(_ % 2 == 0).map(_ * 2).next

  @Benchmark
  def listMax: Int = list1.map(_ + 1).filter(_ % 2 == 0).map(_ * 2).max
  @Benchmark
  def listHead: Int = list1.map(_ + 1).filter(_ % 2 == 0).map(_ * 2).head
  @Benchmark
  def listReverse: Int = list1.reverse.head
  @Benchmark
  def listSort: Int = list2.map(_ + 1).filter(_ % 2 == 0).map(_ * 2).sorted.head

  @Benchmark
  def ilistMax: Option[Int] = ilist1.map(_ + 1).filter(_ % 2 == 0).map(_ * 2).maximum
  @Benchmark
  def ilistHead: Option[Int] = ilist1.map(_ + 1).filter(_ % 2 == 0).map(_ * 2).headOption
  @Benchmark
  def ilistReverse: Option[Int] = ilist1.reverse.headOption
  @Benchmark
  def ilistSort: Option[Int] = ilist2.map(_ + 1).filter(_ % 2 == 0).map(_ * 2).sorted.headOption

  @Benchmark
  def vectorMax: Int = vec1.map(_ + 1).filter(_ % 2 == 0).map(_ * 2).max
  @Benchmark
  def vectorHead: Int = vec1.map(_ + 1).filter(_ % 2 == 0).map(_ * 2).head
  @Benchmark
  def vectorReverse: Int = vec1.reverse.head
  @Benchmark
  def vectorSort: Int = vec2.map(_ + 1).filter(_ % 2 == 0).map(_ * 2).sorted.head

  @Benchmark
  def arrayMax: Int = arr1.map(_ + 1).filter(_ % 2 == 0).map(_ * 2).max
  @Benchmark
  def arrayHead: Int = arr1.map(_ + 1).filter(_ % 2 == 0).map(_ * 2).head
  @Benchmark
  def arrayReverse: Int = arr1.reverse.head
  @Benchmark
  def arraySort: Int = arr2.map(_ + 1).filter(_ % 2 == 0).map(_ * 2).sorted.head

  def chainMax: Option[Int] = chain1.map(_ + 1).filter(_ % 2 == 0).map(_ * 2).maximumOption
  @Benchmark
  def chainHead: Option[Int] = chain1.map(_ + 1).filter(_ % 2 == 0).map(_ * 2).headOption
  @Benchmark
  def chainReverse: Option[Int] = chain1.reverse.headOption
  @Benchmark
  def chainSort: Option[Int] = chain2.map(_ + 1).filter(_ % 2 == 0).map(_ * 2).sorted.headOption
}
