package kr.kth.timeperiod.timerange

import org.joda.time.DateTime
import kr.kth.timeperiod._
import DayOfWeek._
import kr.kth.commons.tools.ScalaHash
import scala.collection.immutable.IndexedSeq

/**
 * 일단위 기간을 표현하는 추상 클래스입니다.
 * @param start
 * @param dayCount
 * @param calendar
 */
abstract class DayTimeRange(start: DateTime, dayCount: Int, calendar: ITimeCalendar)
	extends CalendarTimeRange(start, start.plusDays(dayCount), calendar) {

	private val _dayCount = dayCount

	def this(year: Int, monthOfYear: Int, dayOfMonth: Int, dayCount: Int, calendar: ITimeCalendar) {
		this(new DateTime().withDate(year, monthOfYear, dayOfMonth), dayCount, calendar)
	}

	def this(year: Int, monthOfYear: Int, dayOfMonth: Int, dayCount: Int) {
		this(year, monthOfYear, dayOfMonth, dayCount, TimeCalendar.Default)
	}

	def getDayCount = _dayCount

	def getStartDayOfWeek: DayOfWeek = getTimeCalendar.getDayOfWeek(getStart)

	def getStartDayName: String = getTimeCalendar.getDayName(getStartDayOfWeek)

	def getEndDayOfWeek: DayOfWeek = getTimeCalendar.getDayOfWeek(getEnd)

	def getEndDayName: String = getTimeCalendar.getDayName(getEndDayOfWeek)

	def getHours: Iterable[HourRange] = {
		val startDay = getStartDayStart
		val calendar = getTimeCalendar

		for {
			d <- (0 until getDayCount)
			h <- (0 until TimeSpec.HoursPerDay)
		}
		yield new HourRange(startDay.plusDays(d).plusHours(h), calendar)
	}

	override def hashCode: Int = ScalaHash.compute(super.hashCode, getDayCount)

	protected override def buildStringHelper() =
		super.buildStringHelper()
		.add("dayCount", getDayCount)
}

/**
 * 일단위 기간을 표현합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 17.
 */
class DayRange(moment: DateTime, calendar: ITimeCalendar) extends DayTimeRange(moment, 1, calendar) {

	def this(calendar: ITimeCalendar) {
		this(Clock.getClock.now, calendar)
	}

	def this(moment: DateTime) {
		this(moment, TimeCalendar.Default)
	}

	def this() {
		this(TimeCalendar.Default)
	}

	def this(year: Int, monthOfYear: Int, dayOfMonth: Int, calendar: ITimeCalendar) {
		this(new DateTime(year, monthOfYear, dayOfMonth, 0, 0), calendar)
	}

	def this(year: Int, monthOfYear: Int, dayOfMonth: Int) {
		this(new DateTime(year, monthOfYear, dayOfMonth, 0, 0))
	}

	def getYear = getStartYear

	def getMonth = getStartMonth

	def getDay = getStartDay

	def getDayOfWeek: DayOfWeek = getStartDayOfWeek

	def getDayName = getStartDayName

	def getPreviousDay = addDays(-1)

	def getNextDay = addDays(1)

	def addDays(days: Int): DayRange = {
		new DayRange(Times.getDatePart(getStart).plusDays(days), getTimeCalendar)
	}

	override def format(formatter: Option[ITimeFormatter]) = {
		val fmt = formatter.getOrElse(TimeFormatter.instance)
		fmt.getCalendarPeriod(getDayName,
		                      fmt.getShortDate(getStart),
		                      fmt.getShortDate(getEnd),
		                      getDuration)
	}
}

/**
 * 일단위 기간을 표현하는 `DayRange` 의 컬렉션을 표현합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 17.
 */
class DayRangeCollection(moment: DateTime, dayCount: Int, calendar: ITimeCalendar)
	extends DayTimeRange(moment, dayCount, calendar) {

	def this(moment: DateTime, dayCount: Int) {
		this(moment, dayCount, TimeCalendar.Default)
	}

	def this(year: Int, monthOfYear: Int, dayOfMonth: Int, dayCount: Int, calendar: ITimeCalendar) {
		this(new DateTime().withDate(year, monthOfYear, dayOfMonth), dayCount, calendar)
	}

	def this(year: Int, monthOfYear: Int, dayOfMonth: Int, dayCount: Int) {
		this(year, monthOfYear, dayOfMonth, dayCount, TimeCalendar.Default)
	}

	def getDays: IndexedSeq[DayRange] = {
		val startDay = getStart

		(0 until getDayCount).map(d => new DayRange(startDay.plusDays(d), getTimeCalendar))
	}

	override def format(formatter: Option[ITimeFormatter]) = {
		val fmt = formatter.getOrElse(TimeFormatter.instance)
		fmt.getCalendarPeriod(getStartDayName,
		                      getEndDayName,
		                      fmt.getShortDate(getStart),
		                      fmt.getShortDate(getEnd),
		                      getDuration)

	}
}