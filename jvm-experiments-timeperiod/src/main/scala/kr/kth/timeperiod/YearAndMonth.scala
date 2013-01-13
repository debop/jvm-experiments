package kr.kth.timeperiod

import beans.BeanProperty
import kr.kth.commons.ValueObjectBase

/**
 * 년/월 정보를 나타냅니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 26.
 */
class YearAndMonth(@BeanProperty var year: Int,
                   @BeanProperty var month: Int = 1) extends ValueObjectBase with Comparable[YearAndMonth] {

  def compareTo(other: YearAndMonth): Int = hashCode() compareTo other.hashCode()

  override def hashCode() = (year * 100 + month)

  protected override def buildStringHelper() =
    super.buildStringHelper()
      .add("year", year)
      .add("month", month)
}

