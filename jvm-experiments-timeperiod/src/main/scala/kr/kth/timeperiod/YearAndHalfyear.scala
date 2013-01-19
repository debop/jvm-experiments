package kr.kth.timeperiod

import HalfYearKind._
import kr.kth.commons.ValueObjectBase
import org.joda.time.DateTime

/**
 * 년(Year), 반기(HalfYear) 정보를 가지는 Value Object 입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 26
 */
class YearAndHalfyear(year: Int,
                      halfyear: HalfYearKind = HalfYearKind.First)
    extends ValueObjectBase with Ordered[YearAndHalfyear] {

    private val _year: Int = year
    private val _halfyear: HalfYearKind = halfyear

    def this(year: Int, halfyear: Int) {
        this(year, HalfYearKind(halfyear))
    }

    def getYear = _year

    def getHalfyear = _halfyear

    lazy val _hash = _year * 100 + _halfyear.id

    override def hashCode: Int = _hash

    protected override def buildStringHelper =
        super.buildStringHelper
        .add("year", _year)
        .add("halfyear", _halfyear)

    def compare(that: YearAndHalfyear) = hashCode compareTo that.hashCode
}

object YearAndHalfyear {

    def apply(): YearAndHalfyear = apply(Clock.getClock.today)

    def apply(today: DateTime): YearAndHalfyear = {
        //val today = ClockProxy.getClock.today()
        new YearAndHalfyear(today.getYear, Times.halfyearOf(today))
    }

    def apply(year: Int, halfyear: HalfYearKind): YearAndHalfyear =
        new YearAndHalfyear(year, halfyear)

    def apply(year: Int, halfyear: Int): YearAndHalfyear =
        new YearAndHalfyear(year, halfyear)

    def unapply(instance: YearAndHalfyear) = (instance.getYear, instance.getHalfyear)
}
