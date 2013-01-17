package kr.kth.timeperiod.timerange

import org.joda.time.DateTime
import kr.kth.timeperiod._
import kr.kth.commons.tools.ScalaHash


abstract class HourTimeRange(start: DateTime, hourCount: Int, calendar: ITimeCalendar)
	extends CalendarTimeRange(start, start.plusHours(hourCount), calendar) {

	private val _hourCount = hourCount
	private val _endHour = start.plusHours(hourCount).getHourOfDay

	def this(year: Int, month: Int, day: Int, hour: Int, hourCount: Int, calendar: ITimeCalendar) {
		this(new DateTime(year, month, day, hour, 0, 0), hourCount, calendar)
	}

	def this(year: Int, month: Int, day: Int, hour: Int, hourCount: Int) {
		this(year, month, day, hour, hourCount, TimeCalendar.Default)
	}


	def getHourCount = _hourCount

	override def getEndHour = _endHour

	def getMinutes: Iterable[MinuteRange] = {
		val start = getStart
		val calendar = getTimeCalendar

		for {
			h <- 0 until getHourCount
			m <- 0 until TimeSpec.MinutesPerHour
		} yield new MinuteRange(start.plusHours(h).plusMinutes(m), calendar)
	}

	override def hashCode: Int = ScalaHash.compute(super.hashCode, _hourCount)

	protected override def buildStringHelper() =
		super.buildStringHelper()
		.add("hourCount", _hourCount)
		.add("endHour", _endHour)
}

/**
 * 단위 시간 (1시간) 단위의 간격을 표현합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 17.
 */
class HourRange(moment: DateTime, calendar: ITimeCalendar) extends HourTimeRange(moment, 1, calendar) {

	def this(moment: DateTime) {
		this(moment, TimeCalendar.Default)
	}

	def this(calendar: ITimeCalendar) {
		this(Clock.getClock.now, calendar)
	}

	def this() {
		this(TimeCalendar.Default)
	}

	def this(year: Int, month: Int, day: Int, hour: Int, calendar: ITimeCalendar) {
		this(new DateTime(year, month, day, hour, 0, 0), calendar)
	}

	def this(year: Int, month: Int, day: Int, hour: Int) {
		this(year, month, day, hour, TimeCalendar.Default)
	}

	def getYear = getStartYear

	def getMonth = getStartMonth

	def getDay = getStartDay

	def getHour = getStartHour

	def getPreviousHour: HourRange = addHours(-1)

	def getNextHour: HourRange = addHours(1)

	def addHours(hours: Int): HourRange = {
		var startHour = Times.trimToHour(getStart, getStartHour)
		return new HourRange(startHour.plusHours(hours), getTimeCalendar)
	}

	override def format(formatter: Option[ITimeFormatter]) = {
		val fmt = formatter.getOrElse(TimeFormatter.instance)
		fmt.getCalendarPeriod(fmt.getShortDate(getStart),
		                      fmt.getShortTime(getStart),
		                      fmt.getShortTime(getEnd),
		                      getDuration)
	}
}


/**
 * 단위 시간 기간을 나타내는 {@link HourRange} 의 컬렉션입니다.
 */
class HourRangeCollection(moment: DateTime, hourCount: Int, calendar: ITimeCalendar)
	extends HourTimeRange(moment, hourCount, calendar) {

	def this(moment: DateTime, hourCount: Int) {
		this(moment, hourCount, TimeCalendar.Default)
	}

	def this(year: Int, month: Int, day: Int, hour: Int, hourCount: Int, calendar: ITimeCalendar) {
		this(new DateTime(year, month, day, hour, 0, 0), hourCount, calendar)
	}

	def this(year: Int, month: Int, day: Int, hour: Int, hourCount: Int) {
		this(year, month, day, hour, hourCount, TimeCalendar.Default)
	}

	def getHours: IndexedSeq[HourRange] = {
		var startHour = Times.trimToHour(getStart, getStartHour)
		(0 until getHourCount)
		.map(x => new HourRange(startHour.plusHours(x), getTimeCalendar))
	}

	override def format(formatter: Option[ITimeFormatter]) = {
		val fmt = formatter.getOrElse(TimeFormatter.instance)
		fmt.getCalendarPeriod(fmt.getShortDate(getStart),
		                      fmt.getShortDate(getEnd),
		                      fmt.getShortTime(getStart),
		                      fmt.getShortTime(getEnd),
		                      getDuration)
	}
}
