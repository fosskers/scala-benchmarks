package benchmarks

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

// --- //

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
class SortBench {

  var list0: List[Int] = _
  var list1: List[Int] = _
  var list2: List[Int] = _
  var vec0: Vector[Int] = _
  var vec1: Vector[Int] = _
  var vec2: Vector[Int] = _
  var arr0: Array[Int] = _
  var arr1: Array[Int] = _
  var arr2: Array[Int] = _

  @Setup
  def setup: Unit = {
    list0 = List.range(1, 1000)
    list1 = List.range(1, 10000)
    list2 = List.range(1, 100000)
    vec0 = Vector.range(1, 1000)
    vec1 = Vector.range(1, 10000)
    vec2 = Vector.range(1, 100000)
    arr0 = Array.range(1, 1000)
    arr1 = Array.range(1, 10000)
    arr2 = Array.range(1, 100000)
  }

  @Benchmark
  def sortLazyList0: LazyList[Int] = LazyList.range(1, 1000).sorted
  @Benchmark
  def sortLazyList1: LazyList[Int] = LazyList.range(1, 10000).sorted
  @Benchmark
  def sortLazyList2: LazyList[Int] = LazyList.range(1, 100000).sorted

  @Benchmark
  def sortList0: List[Int] = list0.sorted
  @Benchmark
  def sortList1: List[Int] = list1.sorted
  @Benchmark
  def sortList2: List[Int] = list2.sorted

  @Benchmark
  def sortVector0: Vector[Int] = vec0.sorted
  @Benchmark
  def sortVector1: Vector[Int] = vec1.sorted
  @Benchmark
  def sortVector2: Vector[Int] = vec2.sorted

  @Benchmark
  def sortArray0: Array[Int] = arr0.sorted
  @Benchmark
  def sortArray1: Array[Int] = arr1.sorted
  @Benchmark
  def sortArray2: Array[Int] = arr2.sorted

}

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
class BackwardSortBench {

  var list0: List[Int] = _
  var list1: List[Int] = _
  var list2: List[Int] = _
  var vec0: Vector[Int] = _
  var vec1: Vector[Int] = _
  var vec2: Vector[Int] = _
  var arr0: Array[Int] = _
  var arr1: Array[Int] = _
  var arr2: Array[Int] = _

  @Setup
  def setup: Unit = {
    list0 = List.range(1000, 1, -1)
    list1 = List.range(10000, 1, -1)
    list2 = List.range(100000, 1, -1)
    vec0 = Vector.range(1000, 1, -1)
    vec1 = Vector.range(10000, 1, -1)
    vec2 = Vector.range(100000, 1, -1)
    arr0 = Array.range(1000, 1, -1)
    arr1 = Array.range(10000, 1, -1)
    arr2 = Array.range(100000, 1, -1)
  }

  @Benchmark
  def sortLazyList0: LazyList[Int] = LazyList.range(1000, 1, -1).sorted
  @Benchmark
  def sortLazyList1: LazyList[Int] = LazyList.range(10000, 1, -1).sorted
  @Benchmark
  def sortLazyList2: LazyList[Int] = LazyList.range(100000, 1, -1).sorted

  @Benchmark
  def sortList0: List[Int] = list0.sorted
  @Benchmark
  def sortList1: List[Int] = list1.sorted
  @Benchmark
  def sortList2: List[Int] = list2.sorted

  @Benchmark
  def sortVector0: Vector[Int] = vec0.sorted
  @Benchmark
  def sortVector1: Vector[Int] = vec1.sorted
  @Benchmark
  def sortVector2: Vector[Int] = vec2.sorted

  @Benchmark
  def sortArray0: Array[Int] = arr0.sorted
  @Benchmark
  def sortArray1: Array[Int] = arr1.sorted
  @Benchmark
  def sortArray2: Array[Int] = arr2.sorted

}
