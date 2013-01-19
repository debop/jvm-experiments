package kr.kth.timeperiod

import kr.kth.commons.ValueObjectBase
import kr.kth.timeperiod.QuarterKind._

/**
 * 년/분기 정보를 표현합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 26
 */
class YearAndQuarter(year: Int, quarter: QuarterKind)
    extends ValueObjectBase with Ordered[YearAndQuarter] {

    private val _year: Int = year
    private val _quarter: QuarterKind = if (quarter != null) quarter else QuarterKind.First

    def this(year: Int, quarterNo: Int = 1) = this(year, QuarterKind(quarterNo))

    def getYear = _year

    def getQuarter = _quarter

    def compare(x: YearAndQuarter, y: YearAndQuarter) = x.hashCode.compareTo(y.hashCode)


    lazy val _hashCode = _year * 100 + _quarter.id

    override def hashCode: Int = _hashCode

    protected override def buildStringHelper =
        super.buildStringHelper
        .add("year", _year)
        .add("quarter", _quarter)

    def compare(that: YearAndQuarter) = hashCode compareTo that.hashCode
}

object YearAndQuarter {

    def apply(year: Int, quarter: QuarterKind): YearAndQuarter =
        new YearAndQuarter(year, quarter)

    def apply(year: Int, quarterNo: Int): YearAndQuarter =
        new YearAndQuarter(year, QuarterKind(quarterNo))

    def unapply(instance: YearAndQuarter) = (instance.getYear, instance.getQuarter)
}
