package kr.kth.commons.timeperiod

import kr.kth.commons.base.ValueObjectBase

/**
 * Year 와 WeekOfYear 를 표현합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 26.
 */
class YearAndWeek(var year: Int = 0, var weekOfYear: Int = 1) extends ValueObjectBase with Comparable[YearAndWeek] {


	def getYear = year

	def setYear(year: Int) {
		this.year = year
	}

	def getWeekOfYear = weekOfYear

	def setWeekOfYear(weekOfYear: Int) {
		this.weekOfYear = weekOfYear
	}

	def compareTo(other: YearAndWeek): Int = hashCode() compareTo other.hashCode()

	override def hashCode() = year * 100 + weekOfYear

	protected override def buildStringHelper() =
		super.buildStringHelper().add("year", year).add("weekOfYear", weekOfYear)
}
