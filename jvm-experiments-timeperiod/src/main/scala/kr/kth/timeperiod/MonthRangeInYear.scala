package kr.kth.timeperiod

import kr.kth.commons.{Guard, ValueObjectBase}

/**
 * 한 해 내에서의 월단위의 기간을 표현합니다. ( 1월 ~ 12월, 4월~4월)
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 27
 */
class MonthRangeInYear(val minMonth: Int, val maxMonth: Int)
    extends ValueObjectBase with Ordered[MonthRangeInYear] {

    def isSingleMonth = (minMonth == maxMonth)

    def hasInside(month: Int) = minMonth <= month && month <= maxMonth

    override def hashCode() = minMonth * 100 + maxMonth

    protected override def buildStringHelper() =
        super.buildStringHelper()
        .add("minMonth", minMonth)
        .add("maxMonth", maxMonth)

    def compare(that: MonthRangeInYear) = minMonth compareTo that.minMonth
}

object MonthRangeInYear {

    def apply(min: Int = 1, max: Int = 1): MonthRangeInYear = {
        Guard.shouldBeBetween(min, 1, TimeSpec.MonthsPerYear, "min")
        Guard.shouldBeBetween(max, 1, TimeSpec.MonthsPerYear, "min")

        if (min < max) new MonthRangeInYear(min, max)
        else new MonthRangeInYear(max, min)
    }

    def unapply(monthRangeInYear: MonthRangeInYear) =
        (monthRangeInYear.minMonth, monthRangeInYear.maxMonth)
}
