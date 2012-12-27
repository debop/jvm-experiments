package kr.kth.commons.timeperiod

import kr.kth.commons.base.TimeVal
import kr.kth.commons.tools.HashTool

/**
 * 특정요일의 한시간단위의 기간을 표현한다. (예: 금요일 1시~ 5시)
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 27
 */
class DayHourRange(val dayOfWeek: Int, start: TimeVal, end: TimeVal) extends HourRangeInDay(start, end) {

	def this(dayOfWeek: Int, startHour: Int, endHour: Int) {
		this(dayOfWeek, new TimeVal(startHour), new TimeVal(endHour))
	}

	def this(dayOfWeek: Int, hour: Int) = this(dayOfWeek, hour, hour)

	override def hashCode =
		HashTool.compute((super.hashCode(), dayOfWeek))

	protected override def buildStringHelper =
		super.buildStringHelper.add("dayOfWeek", dayOfWeek)
}
