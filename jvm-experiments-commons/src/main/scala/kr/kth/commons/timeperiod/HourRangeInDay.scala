package kr.kth.commons.timeperiod

import kr.kth.commons.base.{Guard, TimeVal, ValueObjectBase}
import kr.kth.commons.tools.HashTool
import java.util.Objects

/**
 * 1일 (Day) 안의 한시간 단위를 나타내는 HourRange 를 표현합니다. ( 9시 ~ 6시)
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 26
 */
class HourRangeInDay extends ValueObjectBase with Comparable[HourRangeInDay] {

	private var start: TimeVal = _
	private var end: TimeVal = _

	def this(start: TimeVal, end: TimeVal) {
		this()
		if (start.compareTo(end) <= 0) {
			this.start = start
			this.end = end
		}
		else {
			this.start = end
			this.end = start
		}
	}

	def this(startHour: Int, endHour: Int) {
		this(new TimeVal(startHour), new TimeVal(endHour))
	}

	def this(hour: Int) {
		this(hour, hour)
	}

	def getStart = start

	def getEnd = end

	def isMoment = Objects.equals(start, end)

	def hasInside(hour: Int): Boolean = hasInside(new TimeVal(hour))

	def hasInside(target: TimeVal): Boolean = (target.compareTo(getStart) >= 0) && (target.compareTo(getEnd) <= 0)

	def compareTo(other: HourRangeInDay): Int = {
		Guard.shouldNotBeNull(other, "other")
		start.compareTo(other.start)
	}

	override def hashCode: Int = HashTool.compute(start, end)

	protected override def buildStringHelper = {
		super.buildStringHelper
			.add("start", start)
			.add("end", end)
	}


}
