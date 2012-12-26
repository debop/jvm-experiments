package kr.kth.commons.timeperiod

import kr.kth.commons.base.ValueObjectBase
import beans.BeanProperty

/**
 * 년(Year), 반기(HalfYear) 정보를 가지는 Value Object 입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 26
 */
class YearAndHalfyear extends ValueObjectBase with Comparable[YearAndHalfyear] {

	@BeanProperty var year: Int = _
	@BeanProperty var halfyear: HalfYearKind = null

	def this(year: Int, halfyear: HalfYearKind) {
		this()
		this.year = year
		this.halfyear = halfyear
	}

	def this(year: Int, halfyear: Int = 1) {
		this(year, HalfYearKind.valueOf(halfyear))
	}

	def compareTo(other: YearAndHalfyear) = hashCode() - other.hashCode()

	override def hashCode: Int = getYear * 100 + getHalfyear.toInt

	protected override def buildStringHelper =
		super.buildStringHelper
			.add("year", year)
			.add("halfyear", halfyear)
}
