package kr.kth.commons.timeperiod

import beans.BeanProperty
import kr.kth.commons.base.ValueObjectBase

/**
 * Year 와 WeekOfYear 를 표현합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 26.
 */
class YearAndWeek(@BeanProperty var year: Int = 0,
                  @BeanProperty var weekOfYear: Int = 1) extends ValueObjectBase with Comparable[YearAndWeek] {

  def compareTo(other: YearAndWeek): Int = hashCode() compareTo other.hashCode()

  override def hashCode() = year * 100 + weekOfYear

  protected override def buildStringHelper() =
    super.buildStringHelper()
      .add("year", year)
      .add("weekOfYear", weekOfYear)
}
