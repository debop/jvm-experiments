package kr.kth.timeperiod

import kr.kth.commons.ValueObjectBase

/**
 * Year 와 WeekOfYear 를 표현합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 26.
 */
class YearAndWeek(year: Int = 0, weekOfYear: Int = 1)
    extends ValueObjectBase with Ordered[YearAndWeek] {

    private val _year = year
    private val _weekOfYear = weekOfYear

    def getYear = _year

    def getWeekOfYear = _weekOfYear

    lazy val _hash = _year * 100 + _weekOfYear

    override def hashCode: Int = _hash

    protected override def buildStringHelper() = {
        super.buildStringHelper()
        .add("year", _year)
        .add("weekOfYear", _weekOfYear)
    }

    def compare(that: YearAndWeek) = hashCode compareTo that.hashCode
}

object YearAndWeek {

    def apply(year: Int = 0, weekOfYear: Int = 1): YearAndWeek =
        new YearAndWeek(year, weekOfYear)

    def unapply(yw: YearAndWeek) = (yw.getYear, yw.getWeekOfYear)
}
