package kr.kth.timeperiod

import kr.kth.commons.ValueObjectBase

/**
 * 년/월 정보를 나타냅니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 26.
 */
class YearAndMonth(val year: Int, val month: Int)
    extends ValueObjectBase with Ordered[YearAndMonth] {

    lazy val _hash = (year * 100 + month)

    override def hashCode = _hash

    protected override def buildStringHelper() =
        super.buildStringHelper()
        .add("year", year)
        .add("month", month)

    def compare(that: YearAndMonth) = hashCode compareTo that.hashCode
}

object YearAndMonth {
    def apply(year: Int, month: Int): YearAndMonth = new YearAndMonth(year, month)

    def unapply(instance: YearAndMonth) = (instance.year, instance.month)
}

