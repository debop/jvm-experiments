package kr.kth.timeperiod.timerange

import org.joda.time.DateTime
import kr.kth.timeperiod._
import kr.kth.timeperiod.tools.WeekTools
import kr.kth.commons.tools.ScalaHash


/**
 * 주 단위의 기간을 표현합니다.
 */
abstract class WeekTimeRange(start: DateTime, weekCount: Int, calendar: ITimeCalendar)
	extends CalendarTimeRange(start, start.plusDays(weekCount * TimeSpec.DaysPerWeek), calendar) {

	private val _year: Int = start.getYear
	private val _startWeek: Int = WeekTools.getYearAndWeek(start, calendar).getWeekOfYear
	private val _weekCount = weekCount

	def this(start: DateTime, weekCount: Int) {
		this(start, weekCount, TimeCalendar.Default)
	}

	def this(year: Int, weekOfYear: Int, weekCount: Int, calendar: ITimeCalendar) {
		this(WeekRange.getPeriodOf(year, weekOfYear, weekCount, calendar).getStart, weekCount, calendar)
	}

	def this(year: Int, weekOfYear: Int, weekCount: Int) {
		this(year, weekOfYear, weekCount, TimeCalendar.Default)
	}

	def this(period: ITimePeriod, calendar: ITimeCalendar) {
		this(period.getStart.getYear, WeekTools.getYearAndWeek(period.getStart, calendar).getWeekOfYear, calendar)
	}

	def this(period: ITimePeriod) {
		this(period, TimeCalendar.Default)
	}


	def getYear = _year

	def getStartWeek = _startWeek

	def getWeekCount = _weekCount

	def getEndWeek = getStartWeek + getWeekCount - 1

	def getStartWeekOfYearName = getTimeCalendar.getWeekOfYearName(getYear, getStartWeek)

	def getEndWeekOfYearName = getTimeCalendar.getWeekOfYearName(getYear, getEndWeek)

	def getDays: IndexedSeq[DayRange] = {
		val startDay = Times.getStartOfYearWeek(getYear,
		                                        getStartWeek,
		                                        getTimeCalendar.getLocale,
		                                        getTimeCalendar.getWeekOfYearRule)
		val dayCount = getWeekCount * TimeSpec.DaysPerWeek

		(0 until dayCount).map(d => new DayRange(startDay.plusDays(d), getTimeCalendar))
	}

	override def isSamePeriod(other: ITimePeriod): Boolean =
		(other != null) && (other.isInstanceOf[WeekTimeRange]) && equals(other.asInstanceOf[WeekTimeRange])

	override def hashCode: Int = ScalaHash.compute(super.hashCode, _year, _startWeek, _weekCount)
}

/**
 * 1주 단위의 기간을 표현합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 17.
 */
class WeekRange(start: DateTime, calendar: ITimeCalendar) extends WeekTimeRange(start, 1, calendar) {

	def this(start: DateTime) {
		this(start, TimeCalendar.Default)
	}

	def this(calendar: ITimeCalendar) {
		this(Clock.getClock.now, calendar)
	}

	def this() {
		this(TimeCalendar.Default)
	}

	def this(year: Int, startWeek: Int, calendar: ITimeCalendar) {
		this(WeekRange.getPeriodOf(year, startWeek, 1, calendar).getStart, calendar)
	}

	def this(year: Int, startWeek: Int) {
		this(year, startWeek, TimeCalendar.Default)
	}

	def getWeekOfYear: Int = getStartWeek

	def getWeekOfYearName: String = getStartWeekOfYearName

	def getFirstDayOfWeek: DateTime = getStart

	def getLastDayOfWeek: DateTime = getStart.plusDays(TimeSpec.DaysPerWeek - 1)

	def getMultipleCalendarYears = {
		Times.getYearOfCalendar(getTimeCalendar, getFirstDayOfWeek) !=
			Times.getYearOfCalendar(getTimeCalendar, getLastDayOfWeek)
	}

	def addWeeks(weeks: Int): WeekRange = {
		val startOfWeek = Times.getStartOfYearWeek(getYear,
		                                           getStartWeek,
		                                           getTimeCalendar.getLocale,
		                                           getTimeCalendar.getWeekOfYearRule)
		new WeekRange(startOfWeek.plusDays(weeks * TimeSpec.DaysPerWeek), getTimeCalendar)
	}

	override protected def format(formatter: Option[ITimeFormatter]) = {
		val fmt = formatter.getOrElse(TimeFormatter.instance)
		fmt.getCalendarPeriod(getWeekOfYearName,
		                      fmt.getShortDate(getStart),
		                      fmt.getShortDate(getEnd),
		                      getDuration)
	}
}

object WeekRange {

	def getPeriodOf(moment: DateTime, weekCount: Int, calendar: ITimeCalendar): TimeRange = {
		val startOfWeek = Times.startTimeOfWeek(moment, calendar.getFirstDayOfWeek)
		new TimeRange(startOfWeek, weekCount * TimeSpec.DaysPerWeek)
	}

	def getPeriodOf(year: Int, weekOfYear: Int, weekCount: Int, calendar: ITimeCalendar): TimeRange = {
		val startWeek = WeekTools.getWeekRange(new YearAndWeek(year, weekOfYear), calendar)
		val startDay = startWeek.getStart
		val endDay = startDay.plusDays(weekCount * TimeSpec.DaysPerWeek)

		new TimeRange(startDay, endDay)
	}
}

/**
 * 주(Week) 단위의 컬렉션
 */
class WeekRangeCollection(moment: DateTime, weekCount: Int, calendar: ITimeCalendar)
	extends WeekTimeRange(moment, weekCount, calendar) {

	def this(moment: DateTime, weekCount: Int) {
		this(moment, weekCount, TimeCalendar.Default)
	}

	def this(year: Int, startWeek: Int, weekCount: Int, calendar: ITimeCalendar) {
		this(WeekRange.getPeriodOf(year, startWeek, weekCount, calendar).getStart, weekCount, calendar)
	}

	def this(year: Int, startWeek: Int, weekCount: Int) {
		this(year, startWeek, weekCount, TimeCalendar.Default)
	}

	def getWeeks: IndexedSeq[WeekRange] = {
		val start = getStart

		(0 until getWeekCount)
		.map(w => new WeekRange(start.plusDays(w * TimeSpec.DaysPerWeek),
		                        getTimeCalendar))
	}
}
