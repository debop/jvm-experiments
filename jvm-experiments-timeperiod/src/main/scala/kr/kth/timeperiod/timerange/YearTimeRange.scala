package kr.kth.timeperiod.timerange

import kr.kth.timeperiod.{TimeRange, ITimeCalendar}
import kr.kth.commons.Guard
import org.joda.time.DateTime
import kr.kth.commons.tools.ScalaHash

/**
 * kr.kth.timeperiod.timerange.YearTimeRange
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 16.
 */
abstract class YearTimeRange(val startYear: Int,
                             val yearCount: Int,
                             calendar: ITimeCalendar)
	extends YearCalendarTimeRange(YearTimeRange.getPeriodOf(calendar.getBaseMonthOfYear,
	                                                        startYear,
	                                                        yearCount),
	                              calendar) {

	override def getBaseYear = startYear

	def getYearCount = yearCount

	def getStartYearName = getTimeCalendar.getYearName(getStartYear)

	def getEndYearName = getTimeCalendar.getYearName(getEndYear)


	override def hashCode: Int = ScalaHash.compute(super.hashCode, yearCount)

	protected override def buildStringHelper() =
		super.buildStringHelper()
		.add("yearCount", yearCount)
}

object YearTimeRange {

	def getPeriodOf(baseMonthOfYear: Int, year: Int, yearCount: Int): TimeRange = {
		Guard.shouldBePositiveNumber(yearCount, "yearCount")

		val start = new DateTime(year, baseMonthOfYear, 1)
		TimeRange(start, start.plusYears(yearCount))
	}
}
