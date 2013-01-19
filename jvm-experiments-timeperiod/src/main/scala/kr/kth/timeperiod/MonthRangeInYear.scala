package kr.kth.timeperiod

import kr.kth.commons.{Guard, ValueObjectBase}

/**
 * 한 해 내에서의 월단위의 기간을 표현합니다. ( 1월 ~ 12월, 4월~4월)
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 27
 */
class MonthRangeInYear(min: Int, var max: Int)
    extends ValueObjectBase with Ordered[MonthRangeInYear] {

    private val _min: Int = min
    private val _max: Int = max

    def getMin = _min

    def getMax = _max

    def isSingleMonth = (_min == _max)

    def hasInside(month: Int) = getMin <= month && month <= getMax

    override def hashCode() = _max * 100 + _min

    protected override def buildStringHelper() =
        super.buildStringHelper()
        .add("min", _min)
        .add("max", _max)

    def compare(that: MonthRangeInYear) = getMin compareTo that.getMin
}

object MonthRangeInYear {

    def apply(min: Int = 1, max: Int = 1): MonthRangeInYear = {
        Guard.shouldBeBetween(min, 1, TimeSpec.MonthsPerYear, "min")
        Guard.shouldBeBetween(max, 1, TimeSpec.MonthsPerYear, "min")

        if (min < max) new MonthRangeInYear(min, max)
        else new MonthRangeInYear(max, min)
    }

    def unapply(monthRangeInYear: MonthRangeInYear) =
        (monthRangeInYear.getMin, monthRangeInYear.getMax)
}
