package kr.kth.commons.timeperiod

import kr.kth.commons.base.ValueObjectBase

/**
 * 년/월 정보를 나타냅니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 26.
 */
class YearAndMonth(val year: Int, val month: Int = 1) extends ValueObjectBase with Comparable[YearAndMonth] {

	override def hashCode() = (year * 100 + month)

	protected override def buildStringHelper() =
		super.buildStringHelper()
			.add("year", year)
			.add("month", month)

	def compareTo(other: YearAndMonth): Int = hashCode() compareTo other.hashCode()
}
