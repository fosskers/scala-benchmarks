package benchmarks

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

// --- //

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
class MatchBench {

  val nums: Set[Int] = Set(1,2,3,4,5)

  @Benchmark
  def checkWithMatch0: Option[Int] = Nil match {
    case _ if nums.contains(6) => Some(6)
    case _ => None
  }

  @Benchmark
  def checkWithMatch1: Option[Int] = Nil match {
    case _ if nums.contains(6) => Some(6)
    case _ if nums.contains(7) => Some(7)
    case _ => None
  }

  @Benchmark
  def checkWithMatch2: Option[Int] = Nil match {
    case _ if nums.contains(6) => Some(6)
    case _ if nums.contains(7) => Some(7)
    case _ if nums.contains(8) => Some(8)
    case _ => None
  }

  @Benchmark
  def checkWithIfs0: Option[Int] = {
    if (nums.contains(6)) { Some(6) }
    else { None }
  }

  @Benchmark
  def checkWithIfs1: Option[Int] = {
    if (nums.contains(6)) { Some(6) }
    else if (nums.contains(7)) { Some(7) }
    else { None }
  }

  @Benchmark
  def checkWithIfs2: Option[Int] = {
    if (nums.contains(6)) { Some(6) }
    else if (nums.contains(7)) { Some(7) }
    else if (nums.contains(8)) { Some(8) }
    else { None }
  }
}
