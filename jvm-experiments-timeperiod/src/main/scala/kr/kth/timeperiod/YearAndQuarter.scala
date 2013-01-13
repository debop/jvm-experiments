package kr.kth.timeperiod

import beans.BeanProperty
import kr.kth.commons.ValueObjectBase
import QuarterKind._

/**
 * 년/분기 정보를 표현합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 26
 */
class YearAndQuarter(@BeanProperty var year: Int,
                     @BeanProperty var quarter: QuarterKind = QuarterKind.First)
  extends ValueObjectBase with Comparable[YearAndQuarter] {

  def this(year: Int, quarter: Int = 1) = this(year, QuarterKind(quarter))

  def compareTo(other: YearAndQuarter): Int = hashCode().compareTo(other.hashCode())

  override def hashCode(): Int = year * 100 + (if (quarter != null) quarter.id else 0)

  protected override def buildStringHelper =
    super.buildStringHelper
      .add("year", year)
      .add("quarter", quarter)
}
