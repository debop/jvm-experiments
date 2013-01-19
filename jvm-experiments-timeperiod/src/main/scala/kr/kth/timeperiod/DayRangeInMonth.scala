package kr.kth.timeperiod

import kr.kth.commons.tools.ScalaHash
import kr.kth.commons.{Guard, ValueObjectBase}

/**
 * 같은 달내의 일자 간격을 나타냅니다. (2월 5일 ~ 2월 15일)
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 27
 */
class DayRangeInMonth(min: Int, max: Int)
    extends ValueObjectBase with Ordered[DayRangeInMonth] {

    private val _min = min
    private val _max = max

    def isSingleDay = (_min == _max)

    def getMin = _min

    def getMax = _max

    def hasInside(day: Int): Boolean = (getMin <= day && day <= getMax)

    override def hashCode = ScalaHash.compute(_min, _max)

    protected override def buildStringHelper =
        super.buildStringHelper()
        .add("min", _min)
        .add("max", _max)

    def compare(that: DayRangeInMonth) = getMin compareTo that.getMin
}

object DayRangeInMonth {

    def apply(min: Int, max: Int): DayRangeInMonth = {
        Guard.shouldBeBetween(min, 1, TimeSpec.MaxDaysPerMonth, "min")
        Guard.shouldBeBetween(max, 1, TimeSpec.MaxDaysPerMonth, "max")

        if (min <= max) DayRangeInMonth(min, max)
        else DayRangeInMonth(max, min)
    }

    def unapply(dayRangeInMonth: DayRangeInMonth) = (dayRangeInMonth.getMin, dayRangeInMonth.getMax)
}
