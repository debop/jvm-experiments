package kr.kth.timeperiod

import kr.kth.commons.ValueObjectBase

/**
 * Year 와 WeekOfYear 를 표현합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 26.
 */
class YearAndWeek(val year: Int = 0, val weekOfYear: Int = 1)
    extends ValueObjectBase with Ordered[YearAndWeek] {

    lazy val _hash = year * 100 + weekOfYear

    override def hashCode: Int = _hash

    protected override def buildStringHelper() = {
        super.buildStringHelper()
        .add("year", year)
        .add("weekOfYear", weekOfYear)
    }

    def compare(that: YearAndWeek) = hashCode compareTo that.hashCode
}

object YearAndWeek {
    def apply(year: Int = 0, weekOfYear: Int = 1): YearAndWeek =
        new YearAndWeek(year, weekOfYear)

    def unapply(yw: YearAndWeek) = (yw.year, yw.weekOfYear)
}
