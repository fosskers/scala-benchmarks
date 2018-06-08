package benchmarks

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._
import scalaz.{IList, EphemeralStream => EStream}

// --- //

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
class ConcatBench {

  var list0: List[Int] = _
  var list1: List[Int] = _
  var list2: List[Int] = _
  var list3: List[Int] = _
  var list0b: List[Int] = _
  var list1b: List[Int] = _
  var list2b: List[Int] = _
  var list3b: List[Int] = _

  var ilist0: IList[Int] = _
  var ilist1: IList[Int] = _
  var ilist2: IList[Int] = _
  var ilist3: IList[Int] = _
  var ilist0b: IList[Int] = _
  var ilist1b: IList[Int] = _
  var ilist2b: IList[Int] = _
  var ilist3b: IList[Int] = _

  var vector0: Vector[Int] = _
  var vector1: Vector[Int] = _
  var vector2: Vector[Int] = _
  var vector3: Vector[Int] = _
  var vector0b: Vector[Int] = _
  var vector1b: Vector[Int] = _
  var vector2b: Vector[Int] = _
  var vector3b: Vector[Int] = _

  var array0: Array[Int] = _
  var array1: Array[Int] = _
  var array2: Array[Int] = _
  var array3: Array[Int] = _
  var array0b: Array[Int] = _
  var array1b: Array[Int] = _
  var array2b: Array[Int] = _
  var array3b: Array[Int] = _

  var arrayC0: Array[Pair] = _
  var arrayC1: Array[Pair] = _
  var arrayC2: Array[Pair] = _
  var arrayC3: Array[Pair] = _
  var arrayC0b: Array[Pair] = _
  var arrayC1b: Array[Pair] = _
  var arrayC2b: Array[Pair] = _
  var arrayC3b: Array[Pair] = _

  var stream0: Stream[Int] = _
  var stream1: Stream[Int] = _
  var stream2: Stream[Int] = _
  var stream3: Stream[Int] = _
  var stream0b: Stream[Int] = _
  var stream1b: Stream[Int] = _
  var stream2b: Stream[Int] = _
  var stream3b: Stream[Int] = _

  var estream0: EStream[Int] = _
  var estream1: EStream[Int] = _
  var estream2: EStream[Int] = _
  var estream3: EStream[Int] = _
  var estream0b: EStream[Int] = _
  var estream1b: EStream[Int] = _
  var estream2b: EStream[Int] = _
  var estream3b: EStream[Int] = _

  @Setup
  def setup: Unit = {
    list0 = List.range(1, 1000)
    list1 = List.range(1, 10000)
    list2 = List.range(1, 100000)
    list3 = List.range(1, 1000000)
    list0b = List.range(1, 1000)
    list1b = List.range(1, 10000)
    list2b = List.range(1, 100000)
    list3b = List.range(1, 1000000)

    ilist0 = IList.fromList(list0)
    ilist1 = IList.fromList(list1)
    ilist2 = IList.fromList(list2)
    ilist3 = IList.fromList(list3)
    ilist0b = IList.fromList(list0)
    ilist1b = IList.fromList(list1)
    ilist2b = IList.fromList(list2)
    ilist3b = IList.fromList(list3)

    vector0 = Vector.range(1, 1000)
    vector1 = Vector.range(1, 10000)
    vector2 = Vector.range(1, 100000)
    vector3 = Vector.range(1, 1000000)
    vector0b = Vector.range(1, 1000)
    vector1b = Vector.range(1, 10000)
    vector2b = Vector.range(1, 100000)
    vector3b = Vector.range(1, 1000000)

    array0 = Array.range(1, 1000)
    array1 = Array.range(1, 10000)
    array2 = Array.range(1, 100000)
    array3 = Array.range(1, 1000000)
    array0b = Array.range(1, 1000)
    array1b = Array.range(1, 10000)
    array2b = Array.range(1, 100000)
    array3b = Array.range(1, 1000000)

    arrayC0 = Array.range(1, 1000).map(n => Pair(n, n))
    arrayC1 = Array.range(1, 10000).map(n => Pair(n, n))
    arrayC2 = Array.range(1, 100000).map(n => Pair(n, n))
    arrayC3 = Array.range(1, 1000000).map(n => Pair(n, n))
    arrayC0b = Array.range(1, 1000).map(n => Pair(n, n))
    arrayC1b = Array.range(1, 10000).map(n => Pair(n, n))
    arrayC2b = Array.range(1, 100000).map(n => Pair(n, n))
    arrayC3b = Array.range(1, 1000000).map(n => Pair(n, n))

    stream0 = Stream.range(1, 1000)
    stream1 = Stream.range(1, 10000)
    stream2 = Stream.range(1, 100000)
    stream3 = Stream.range(1, 1000000)
    stream0b = Stream.range(1, 1000)
    stream1b = Stream.range(1, 10000)
    stream2b = Stream.range(1, 100000)
    stream3b = Stream.range(1, 1000000)

    estream0 = EStream.range(1, 1000)
    estream1 = EStream.range(1, 10000)
    estream2 = EStream.range(1, 100000)
    estream3 = EStream.range(1, 1000000)
    estream0b = EStream.range(1, 1000)
    estream1b = EStream.range(1, 10000)
    estream2b = EStream.range(1, 100000)
    estream3b = EStream.range(1, 1000000)
  }

  def list(a: List[Int], b: List[Int]): List[Int] = a ++ b
  def ilist(a: IList[Int], b: IList[Int]): IList[Int] = a ++ b
  def vector(a: Vector[Int], b: Vector[Int]): Vector[Int] = a ++ b
  def array(a: Array[Int], b: Array[Int]): Array[Int] = a ++ b
  def arrayC(a: Array[Pair], b: Array[Pair]): Array[Pair] = a ++ b
  def stream(a: Stream[Int], b: Stream[Int]): Stream[Int] = a ++ b
  def estream(a: EStream[Int], b: EStream[Int]): EStream[Int] = a ++ b

  @Benchmark
  def list1k: List[Int] = list(list0, list0b)
  @Benchmark
  def list10k: List[Int] = list(list1, list1b)
  @Benchmark
  def list100k: List[Int] = list(list2, list2b)
  @Benchmark
  def list1000k: List[Int] = list(list3, list3b)

  @Benchmark
  def ilist1k: IList[Int] = ilist(ilist0, ilist0b)
  @Benchmark
  def ilist10k: IList[Int] = ilist(ilist1, ilist1b)
  @Benchmark
  def ilist100k: IList[Int] = ilist(ilist2, ilist2b)
  @Benchmark
  def ilist1000k: IList[Int] = ilist(ilist3, ilist3b)

  @Benchmark
  def vector1k: Vector[Int] = vector(vector0 , vector0b)
  @Benchmark
  def vector10k: Vector[Int] = vector(vector1 , vector1b)
  @Benchmark
  def vector100k: Vector[Int] = vector(vector2 , vector2b)
  @Benchmark
  def vector1000k: Vector[Int] = vector(vector3 , vector3b)

  @Benchmark
  def array1k: Array[Int] = array(array0 , array0b)
  @Benchmark
  def array10k: Array[Int] = array(array1 , array1b)
  @Benchmark
  def array100k: Array[Int] = array(array2 , array2b)
  @Benchmark
  def array1000k: Array[Int] = array(array3 , array3b)

  @Benchmark
  def arrayC1k: Array[Pair] = arrayC(arrayC0 , arrayC0b)
  @Benchmark
  def arrayC10k: Array[Pair] = arrayC(arrayC1 , arrayC1b)
  @Benchmark
  def arrayC100k: Array[Pair] = arrayC(arrayC2 , arrayC2b)
  @Benchmark
  def arrayC1000k: Array[Pair] = arrayC(arrayC3 , arrayC3b)

  @Benchmark
  def stream1k: Stream[Int] = stream(stream0 , stream0b)
  @Benchmark
  def stream10k: Stream[Int] = stream(stream1 , stream1b)
  @Benchmark
  def stream100k: Stream[Int] = stream(stream2 , stream2b)
  @Benchmark
  def stream1000k: Stream[Int] = stream(stream3 , stream3b)

  @Benchmark
  def estream1k: EStream[Int] = estream(estream0 , estream0b)
  @Benchmark
  def estream10k: EStream[Int] = estream(estream1 , estream1b)
  @Benchmark
  def estream100k: EStream[Int] = estream(estream2 , estream2b)
  @Benchmark
  def estream1000k: EStream[Int] = estream(estream3 , estream3b)
}
