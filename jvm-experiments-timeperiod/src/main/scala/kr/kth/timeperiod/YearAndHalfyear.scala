package kr.kth.timeperiod

import beans.BeanProperty
import org.joda.time.DateTime
import kr.kth.commons.ValueObjectBase
import HalfYearKind._

/**
 * 년(Year), 반기(HalfYear) 정보를 가지는 Value Object 입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 26
 */
class YearAndHalfyear(@BeanProperty val year: Int,
                      @BeanProperty val halfyear: HalfYearKind = HalfYearKind.First)
  extends ValueObjectBase with Comparable[YearAndHalfyear] {

  def this(year: Int, halfyear: Int = 1) = this(year, HalfYearKind(halfyear))

  def compareTo(other: YearAndHalfyear) = hashCode compareTo other.hashCode

  override def hashCode: Int = year * 100 + halfyear.id

  protected override def buildStringHelper =
    super.buildStringHelper
      .add("year", year)
      .add("halfyear", halfyear)
}

object YearAndHalfyear {

  def apply(): YearAndHalfyear = apply(Clock.getClock.today)

  def apply(today: DateTime): YearAndHalfyear = {
    //val today = ClockProxy.getClock.today()
    new YearAndHalfyear(today.getYear, Times.halfyearOf(today))
  }
}
