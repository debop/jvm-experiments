package kr.kth.commons.timeperiod

import kr.kth.commons.base.{Guard, ValueObjectBase}
import kr.kth.commons.tools.ScalaHash

/**
 * 같은 달내의 일자 간격을 나타냅니다. (2월 5일 ~ 2월 15일)
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 27
 */
class DayRangeInMonth(val min: Int, val max: Int) extends ValueObjectBase with Comparable[DayRangeInMonth] {

  def isSingleDay = (min == max)

  def hasInside(day: Int): Boolean = (min <= day && day <= max)

  def compareTo(other: DayRangeInMonth): Int = this.min compare other.min

  override def hashCode = ScalaHash.compute(this.min, this.max)

  protected override def buildStringHelper =
    super.buildStringHelper()
      .add("min", min)
      .add("max", max)
}

object DayRangeInMonth {

  def apply(min: Int, max: Int): DayRangeInMonth = {
    Guard.shouldBeBetween(min, 1, TimeSpec.MaxDaysPerMonth, "min")
    Guard.shouldBeBetween(max, 1, TimeSpec.MaxDaysPerMonth, "max")

    if (min <= max) new DayRangeInMonth(min, max)
    else new DayRangeInMonth(max, min)
  }
}
