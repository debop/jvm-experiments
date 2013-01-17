package kr.kth.timeperiod.timerange

import kr.kth.timeperiod._
import org.joda.time.DateTime
import kr.kth.commons.tools.ScalaHash
import kr.kth.commons.Guard

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

/**
 * 1년 단위의 기간을 표현합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 16.
 */
class YearRange(startYear: Int, calendar: ITimeCalendar) extends YearTimeRange(startYear, 1, calendar) {

	def this(moment: DateTime, calendar: ITimeCalendar) {
		this(Times.getYearOfCalendar(calendar, moment), calendar)
	}

	def this(calendar: ITimeCalendar) {
		this(Clock.getClock.now, calendar)
	}

	def this() {
		this(TimeCalendar.Default)
	}

	def getYearValue = getStartYear

	def getYearName = getStartYearName

	def isCalendarYear = getYearBaseMonth == TimeSpec.CalendarYearStartMonth

	def getPreviousYear = addYears(-1)

	def getNextYear = addYears(1)

	def addYears(value: Int): YearRange = {
		val startTime = new DateTime(getStartYear, getYearBaseMonth, 1)
		new YearRange(startTime.plusYears(value), getTimeCalendar)
	}
}

/**
 * 1년 단위의 기간을 표현하는 {@link YearRange} 의 컬렉션을 표현합니다.
 */
class YearRangeCollection(startYear: Int, yearCount: Int, calendar: ITimeCalendar)
	extends YearTimeRange(startYear, yearCount, calendar) {

	def this(moment: DateTime, yearCount: Int, calendar: ITimeCalendar) {
		this(Times.getYearOfCalendar(calendar, moment), yearCount, calendar)
	}

	def this(moment: DateTime, yearCount: Int) {
		this(moment, yearCount, TimeCalendar.Default)
	}

	def getYears: Iterable[YearRange] = {
		val year = getStartYear
		val calendar = getTimeCalendar
		(0 until getYearCount).map(x => new YearRange(year + x, calendar))
	}

	def format(formatter: ITimeFormatter): String = {
		formatter.getCalendarPeriod(getStartYearName,
		                            getEndYearName,
		                            formatter.getShortDate(getStart),
		                            formatter.getShortDate(getEnd),
		                            getDuration)
	}
}


