package benchmarks

import java.util.concurrent.TimeUnit

import cats.data.Chain
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

  var lazyList0: LazyList[Int] = _
  var lazyList1: LazyList[Int] = _
  var lazyList2: LazyList[Int] = _
  var lazyList3: LazyList[Int] = _
  var lazyList0b: LazyList[Int] = _
  var lazyList1b: LazyList[Int] = _
  var lazyList2b: LazyList[Int] = _
  var lazyList3b: LazyList[Int] = _

  var estream0: EStream[Int] = _
  var estream1: EStream[Int] = _
  var estream2: EStream[Int] = _
  var estream3: EStream[Int] = _
  var estream0b: EStream[Int] = _
  var estream1b: EStream[Int] = _
  var estream2b: EStream[Int] = _
  var estream3b: EStream[Int] = _

  var chain0: Chain[Int] = _
  var chain1: Chain[Int] = _
  var chain2: Chain[Int] = _
  var chain3: Chain[Int] = _
  var chain0b: Chain[Int] = _
  var chain1b: Chain[Int] = _
  var chain2b: Chain[Int] = _
  var chain3b: Chain[Int] = _

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

    lazyList0 = LazyList.range(1, 1000)
    lazyList1 = LazyList.range(1, 10000)
    lazyList2 = LazyList.range(1, 100000)
    lazyList3 = LazyList.range(1, 1000000)
    lazyList0b = LazyList.range(1, 1000)
    lazyList1b = LazyList.range(1, 10000)
    lazyList2b = LazyList.range(1, 100000)
    lazyList3b = LazyList.range(1, 1000000)

    estream0 = EStream.range(1, 1000)
    estream1 = EStream.range(1, 10000)
    estream2 = EStream.range(1, 100000)
    estream3 = EStream.range(1, 1000000)
    estream0b = EStream.range(1, 1000)
    estream1b = EStream.range(1, 10000)
    estream2b = EStream.range(1, 100000)
    estream3b = EStream.range(1, 1000000)

    chain0 = Chain.fromSeq(list0)
    chain1 = Chain.fromSeq(list1)
    chain2 = Chain.fromSeq(list2)
    chain3 = Chain.fromSeq(list3)
    chain0b = Chain.fromSeq(list0)
    chain1b = Chain.fromSeq(list1)
    chain2b = Chain.fromSeq(list2)
    chain3b = Chain.fromSeq(list3)
  }

  def list(a: List[Int], b: List[Int]): List[Int] = a ++ b
  def ilist(a: IList[Int], b: IList[Int]): IList[Int] = a ++ b
  def vector(a: Vector[Int], b: Vector[Int]): Vector[Int] = a ++ b
  def array(a: Array[Int], b: Array[Int]): Array[Int] = a ++ b
  def arrayC(a: Array[Pair], b: Array[Pair]): Array[Pair] = a ++ b
  def lazyList(a: LazyList[Int], b: LazyList[Int]): LazyList[Int] = a ++ b
  def estream(a: EStream[Int], b: EStream[Int]): EStream[Int] = a ++ b
  def chain(a: Chain[Int], b: Chain[Int]): Chain[Int] = a ++ b

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
  def lazyList1k: LazyList[Int] = lazyList(lazyList0 , lazyList0b)
  @Benchmark
  def lazyList10k: LazyList[Int] = lazyList(lazyList1 , lazyList1b)
  @Benchmark
  def lazyList100k: LazyList[Int] = lazyList(lazyList2 , lazyList2b)
  @Benchmark
  def lazyList1000k: LazyList[Int] = lazyList(lazyList3 , lazyList3b)

  @Benchmark
  def estream1k: EStream[Int] = estream(estream0 , estream0b)
  @Benchmark
  def estream10k: EStream[Int] = estream(estream1 , estream1b)
  @Benchmark
  def estream100k: EStream[Int] = estream(estream2 , estream2b)
  @Benchmark
  def estream1000k: EStream[Int] = estream(estream3 , estream3b)

  @Benchmark
  def chain1k: Chain[Int] = chain(chain0 , chain0b)
  @Benchmark
  def chain10k: Chain[Int] = chain(chain1 , chain1b)
  @Benchmark
  def chain100k: Chain[Int] = chain(chain2 , chain2b)
  @Benchmark
  def chain1000k: Chain[Int] = chain(chain3 , chain3b)
}
