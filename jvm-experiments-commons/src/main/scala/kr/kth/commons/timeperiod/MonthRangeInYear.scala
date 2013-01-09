package kr.kth.commons.timeperiod

import beans.BeanProperty
import kr.kth.commons.base.{Guard, ValueObjectBase}

/**
 * 한 해 내에서의 월단위의 기간을 표현합니다. ( 1월 ~ 12월, 4월~4월)
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 27
 */
class MonthRangeInYear(@BeanProperty var min: Int,
                       @BeanProperty var max: Int) extends ValueObjectBase with Comparable[MonthRangeInYear] {

    def isSingleMonth = (min == max)

    def compareTo(other: MonthRangeInYear) = hashCode() compareTo other.hashCode()

    override def hashCode() = max * 100 + min

    protected override def buildStringHelper() =
        super.buildStringHelper()
            .add("min", min)
            .add("max", max)
}

object MonthRangeInYear {

    def apply(min: Int = 1, max: Int = 1): MonthRangeInYear = {
        Guard.shouldBeBetween(min, 1, TimeSpec.MonthsPerYear, "min")
        Guard.shouldBeBetween(max, 1, TimeSpec.MonthsPerYear, "min")

        if (min < max) new MonthRangeInYear(min, max)
        else new MonthRangeInYear(max, min)
    }
}
