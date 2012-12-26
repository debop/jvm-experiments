package kr.kth.commons.timeperiod

import kr.kth.commons.base.{Guard, ValueObjectBase}
import beans.BeanProperty

/**
 * 년/분기 정보를 표현합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 26
 */
class YearAndQuarter extends ValueObjectBase with Comparable[YearAndQuarter] {

	@BeanProperty var year: Option[Int] = _
	@BeanProperty var quarter: QuarterKind = _

	def this(year: Option[Int], quarter: QuarterKind = QuarterKind.First) {
		this()
		this.year = year
		this.quarter = quarter
	}

	def this(year: Option[Int], quarter: Int = 1) {
		this(year, QuarterKind.valueOf(quarter))
	}

	def compareTo(other: YearAndQuarter): Int = {
		Guard.shouldNotBeNull(other, "other")
		hashCode() - other.hashCode
	}

	override def hashCode = {
		year.getOrElse(0) * 100 + (if (quarter != null) quarter.intValue() else 0)
	}


	protected override def buildStringHelper =
		super.buildStringHelper
			.add("year", year)
			.add("quarter", quarter)
}
