package kr.kth.commons.timeperiod

import kr.kth.commons.base.{Guard, ValueObjectBase}

/**
 * 한 해 내에서의 월단위의 기간을 표현합니다. ( 1월 ~ 12월, 4월~4월)
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 27
 */
class MonthRangeInYear extends ValueObjectBase with Comparable[MonthRangeInYear] {

	private var min: Int = _
	private var max: Int = _

	def this(min: Int = 1, max: Int = 1) {
		this()
		Guard.shouldBeBetween(min, 1, TimeSpec.MonthsPerYear, "min")
		Guard.shouldBeBetween(max, 1, TimeSpec.MonthsPerYear, "min")

		if (min < max) {
			this.min = min
			this.max = max
		} else {
			this.min = max
			this.max = min
		}
	}

	def getMin: Int = min

	def getMax: Int = max

	def isSingleMonth = (min == max)

	def compareTo(other: MonthRangeInYear) = hashCode() compareTo other.hashCode()

	override def hashCode() = max * 100 + min

	protected override def buildStringHelper() =
		super.buildStringHelper()
			.add("min", min)
			.add("max", max)
}
