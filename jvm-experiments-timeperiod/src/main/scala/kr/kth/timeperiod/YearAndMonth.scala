package kr.kth.timeperiod

import kr.kth.commons.ValueObjectBase

/**
 * 년/월 정보를 나타냅니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 26.
 */
class YearAndMonth(year: Int, month: Int)
    extends ValueObjectBase with Ordered[YearAndMonth] {

    private val _year = year
    private val _month = month

    def getYear = _year

    def getMonth = _month

    lazy val _hash = (_year * 100 + _month)

    override def hashCode = _hash

    protected override def buildStringHelper() =
        super.buildStringHelper()
        .add("year", _year)
        .add("month", _month)

    def compare(that: YearAndMonth) = hashCode compareTo that.hashCode
}

object YearAndMonth {
    def apply(year: Int, month: Int): YearAndMonth = new YearAndMonth(year, month)

    def unapply(instance: YearAndMonth) = (instance.getYear, instance.getMonth)
}

