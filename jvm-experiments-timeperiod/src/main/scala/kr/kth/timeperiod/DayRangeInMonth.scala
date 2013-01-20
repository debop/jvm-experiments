package kr.kth.timeperiod

import kr.kth.commons.tools.ScalaHash
import kr.kth.commons.{Guard, ValueObjectBase}

/**
 * 같은 달내의 일자 간격을 나타냅니다. (2월 5일 ~ 2월 15일)
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 27
 */
class DayRangeInMonth(val minDay: Int, val maxDay: Int)
    extends ValueObjectBase with Ordered[DayRangeInMonth] {

    def isSingleDay = (minDay == maxDay)

    def hasInside(day: Int): Boolean = (minDay <= day && day <= maxDay)

    override def hashCode = ScalaHash.compute(minDay, maxDay)

    protected override def buildStringHelper =
        super.buildStringHelper()
        .add("minDay", minDay)
        .add("maxDay", maxDay)

    def compare(that: DayRangeInMonth) = minDay compareTo that.minDay
}

object DayRangeInMonth {

    def apply(min: Int, max: Int): DayRangeInMonth = {
        Guard.shouldBeBetween(min, 1, TimeSpec.MaxDaysPerMonth, "min")
        Guard.shouldBeBetween(max, 1, TimeSpec.MaxDaysPerMonth, "max")

        if (min <= max) DayRangeInMonth(min, max)
        else DayRangeInMonth(max, min)
    }

    def unapply(dayRangeInMonth: DayRangeInMonth) = (dayRangeInMonth.minDay, dayRangeInMonth.maxDay)
}
