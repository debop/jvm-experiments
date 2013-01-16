package kr.kth.timeperiod.timerange

import kr.kth.timeperiod._

/**
 * kr.kth.timeperiod.timerange.YearCalendarTimeRange
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 16.
 */
abstract class YearCalendarTimeRange(period: ITimePeriod, calendar: ITimeCalendar)
	extends CalendarTimeRange(period, calendar) {

	def getYearBaseMonth = getTimeCalendar.getBaseMonthOfYear

	def getBaseYear = getStartYear
}
