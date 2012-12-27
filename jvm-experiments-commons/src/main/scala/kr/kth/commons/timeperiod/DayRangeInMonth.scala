package kr.kth.commons.timeperiod

import kr.kth.commons.base.{Guard, ValueObjectBase}
import kr.kth.commons.tools.HashTool

/**
 * 같은 달내의 일자 간격을 나타냅니다. (2월 5일 ~ 2월 15일)
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 27
 */
class DayRangeInMonth extends ValueObjectBase with Comparable[DayRangeInMonth] {

	private var min: Int = _
	private var max: Int = _

	def this(minDay: Int, maxDay: Int) {
		this()
		Guard.shouldBeBetween(minDay, 1, TimeSpec.MaxDaysPerMonth, "minDay")
		Guard.shouldBeBetween(maxDay, 1, TimeSpec.MaxDaysPerMonth, "maxDay")

		if (minDay <= maxDay) {
			this.min = minDay
			this.max = maxDay
		} else {
			this.min = maxDay
			this.max = minDay
		}
	}

	def getMin = min

	def getMax = max

	def isSingleDay = (min == max)

	def hasInside(day: Int): Boolean = (min <= day && day <= max)

	def compareTo(other: DayRangeInMonth): Int = this.min compare other.min

	override def hashCode = HashTool.compute((this.min, this.max))

	protected override def buildStringHelper =
		super.buildStringHelper()
			.add("min", min)
			.add("max", max)
}
