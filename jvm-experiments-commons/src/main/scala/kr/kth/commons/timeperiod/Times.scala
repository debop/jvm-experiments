package kr.kth.commons.timeperiod

import org.joda.time.DateTime
import grizzled.slf4j.Logger
import java.util.{Date, Locale}
import kr.kth.commons.base.{Guard, NotImplementedException}
import collection.JavaConversions
import kr.kth.commons.tools.StringTool
import timerange.TimeRange
import tools.TimeTool
import collection.mutable.ArrayBuffer

/**
 * DateTime 관련 Object
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 27
 */
object Times {

	lazy val log            = Logger[this.type]
	lazy val isDebugEnabled = log.isDebugEnabled

	val NullStr   = "null"
	val UnixEpoch = new DateTime withMillis 0

	def getNow: DateTime = DateTime.now

	def getNowTime: Long = getNow.getMillis

	def getToday: DateTime = getNow.withTimeAtStartOfDay()

	/**
	 * 기간을 문자열로 표현합니다.
	 */
	def asString(period: ITimePeriod) = if (period != null) period.getDurationDescription else NullStr

	def toDateTime(str: String, defaultVal: DateTime = UnixEpoch): DateTime = {
		try {
			DateTime.parse(str)
		} catch {
			case e: Exception => defaultVal
		}
	}

	def toDateTime(str: String, valueFactory: () => DateTime): DateTime = {
		if (StringTool.isEmpty(str)) UnixEpoch

		val factory = if (valueFactory != null) valueFactory else () => Times.UnixEpoch
		try {
			DateTime.parse(str)
		} catch {
			case e: Exception => valueFactory.apply()
		}
	}

	def datePart(moment: DateTime): DateTime = moment.withTimeAtStartOfDay

	def timePart(moment: DateTime): DateTime = new DateTime() withMillis moment.getMillisOfDay

	//
	// Calendar 관련
	//

	def getYearOf(year: Int, monthOfYear: Int, startMonthOfYear: Int): Int = {
		if (monthOfYear >= startMonthOfYear) year else year - 1
	}

	def getYearOf(moment: DateTime, startMonthOfYear: Int): Int =
		getYearOf(moment.getYear, moment.getMonthOfYear, startMonthOfYear)


	def getYearOfCalendar(calendar: ITimeCalendar, moment: DateTime): Int =
		getYearOf(calendar.getYear(moment), calendar.getMonth(moment), calendar.getBaseMonthOfYear)


	def nextHalfyear(startHalfyear: HalfYearKind, startYear: Int = 0) =
		addHalfyear(startHalfyear, startYear, 1)

	def previousHalfyear(startHalfyear: HalfYearKind, startYear: Int = 0) =
		addHalfyear(startHalfyear, startYear, -1)

	def addHalfyear(startHalfyear: HalfYearKind, startYear: Int = 0, count: Int = 1): YearAndHalfyear = {
		val offsetYear = Math.abs(count) / TimeSpec.HalfyearsPerYear + 1
		val startHalfyearCount = ((startYear + offsetYear) * TimeSpec.HalfyearsPerYear) + (startHalfyear.toInt - 1)
		val targetHalfyearCount = startHalfyearCount + count

		val year = (targetHalfyearCount / TimeSpec.HalfyearsPerYear) - offsetYear
		val halfyear = (targetHalfyearCount % TimeSpec.HalfyearsPerYear) + 1

		new YearAndHalfyear(year, halfyear)
	}

	def getHalfyearOfMonth(monthOfYear: Int, yearStartMonth: Int = TimeSpec.CalendarYearStartMonth): HalfYearKind = {
		var yearMonthIndex = monthOfYear - 1
		val yearStartMonthIndex = yearStartMonth - 1
		if (yearMonthIndex < yearStartMonthIndex)
			yearMonthIndex += TimeSpec.MonthsPerYear
		val deltaMonths = yearMonthIndex - yearStartMonthIndex

		HalfYearKind.valueOf((deltaMonths / TimeSpec.MonthsPerHalfyear) + 1)
	}

	def getMonthsOfHalfyear(halfyear: HalfYearKind): Array[Int] =
		if (halfyear == HalfYearKind.First) TimeSpec.FirstHalfyearMonths
		else TimeSpec.SecondHalfyearMonths


	def nextQuarter(startQuarter: QuarterKind, startYear: Int = 0) = addQuarter(startQuarter, startYear, 1)

	def previousQuarter(startQuarter: QuarterKind, startYear: Int = 0) = addQuarter(startQuarter, startYear, -1)

	def addQuarter(startQuarter: QuarterKind, startYear: Int = 0, count: Int = 0): YearAndQuarter = {
		val offsetYear = (Math.abs(count) / TimeSpec.QuartersPerYear) + 1
		val startQuarterCount = ((startYear + offsetYear) * TimeSpec.QuartersPerYear) + (startQuarter.toInt - 1)
		val targetQuarterCount = startQuarterCount - count

		val year = (targetQuarterCount / TimeSpec.QuartersPerYear) - offsetYear
		val quarter = targetQuarterCount % TimeSpec.QuartersPerYear + 1

		new YearAndQuarter(year, quarter)
	}

	def getQuarterOfMonth(monthOfYear: Int, yearBaseMonth: Int = TimeSpec.CalendarYearStartMonth): QuarterKind = {
		var monthOfYearIndex = monthOfYear - 1
		val yearStartMonthIndex = yearBaseMonth - 1
		if (monthOfYear < yearStartMonthIndex)
			monthOfYearIndex += TimeSpec.MonthsPerYear
		val deltaMonths = monthOfYearIndex - yearStartMonthIndex

		QuarterKind.valueOf((deltaMonths / TimeSpec.MonthsPerQuarter) + 1)
	}

	def getMonthsOfQuarter(quarter: QuarterKind): Array[Int] = {
		quarter match {
			case QuarterKind.First => TimeSpec.FirstQuarterMonths
			case QuarterKind.Second => TimeSpec.SecondQuarterMonths
			case QuarterKind.Third => TimeSpec.ThirdQuarterMonths
			case QuarterKind.Fouth => TimeSpec.FourthQuarterMonths

			case _ => throw new IllegalArgumentException("quarter")
		}
	}

	def nextMonth(startMonth: Int): YearAndMonth = addMonth(0, startMonth, 1)

	def previousMonth(startMonth: Int): YearAndMonth = addMonth(0, startMonth, -1)

	def addMonth(current: YearAndMonth, count: Int): YearAndMonth =
		addMonth(current.year, current.month, count)

	def addMonth(startYear: Int, startMonth: Int, count: Int): YearAndMonth = {

		if (log.isDebugEnabled)
			log.debug("월을 추가합니다. year=[{}], month=[{}], count=[{}]", startYear, startMonth, count)

		val offsetYear = (Math.abs(count) / TimeSpec.MonthsPerYear) + 1
		val startMonthCount = ((startYear + offsetYear) * TimeSpec.MonthsPerYear) + (startMonth - 1)
		val targetMonthCount = startMonthCount + count

		val year = (targetMonthCount / TimeSpec.MonthsPerYear) - offsetYear
		val month = (targetMonthCount % TimeSpec.MonthsPerYear) + 1

		new YearAndMonth(year, month)
	}

	def getDaysInMonth(year: Int, month: Int) =
		new DateTime().withDate(year, month, 1)
		.plusMonths(1)
		.minusDays(1)
		.getDayOfMonth


	/**
	 * 지정된 시간이 속한 주(Week)의 첫번째 요일을 가져옵니다. (한국/미국은 한주의 시작은 일요일이고, ISO 8601 에서는 월요일이다)
	 */
	def getStartOfWeek(moment: DateTime, firstDayOfWeek: Int = TimeSpec.FirstOfDayOfWeek): DateTime = {
		var currentDay = datePart(moment)
		while (currentDay.getDayOfWeek != firstDayOfWeek) {
			currentDay = currentDay.minusDays(1)
		}
		currentDay
	}

	def getWeekOfYear(moment: DateTime,
	                  locale: Locale = Locale.getDefault,
	                  weekOfYearRule: WeekOfYearRuleKind = WeekOfYearRuleKind.Caleandar): YearAndWeek = {
		// TODO: WeekTool 에 있는 내용을 여기로 이동 시킬 것
		throw new NotImplementedException()
	}

	def getWeeksOfYear(year: Int,
	                   locale: Locale = Locale.getDefault,
	                   weekOfYearRule: WeekOfYearRuleKind = WeekOfYearRuleKind.Caleandar): Int = {
		if (log.isDebugEnabled)
			log.debug("해당년도[{}]의 주차 수를 구합니다. locale=[{}], weekOfYearRule=[{}]", year, locale, weekOfYearRule)

		var currentDay = new DateTime().withDate(year, 12, 31)
		var yearAndWeek = getWeekOfYear(currentDay, locale, weekOfYearRule)
		while (yearAndWeek.year != year) {
			currentDay = currentDay.minusDays(1)
			yearAndWeek = getWeekOfYear(currentDay, locale, weekOfYearRule)
		}
		val weeks = yearAndWeek.weekOfYear

		if (log.isDebugEnabled)
			log.debug("해당년도[{}]의 주차 수 [{}]를 구했습니다!!! locale=[{}], weekOfYearRule=[{}]",
			          year, weeks, locale, weekOfYearRule)
		weeks
	}

	/**
	 * 년, 주차에 해당하는 첫번째 날짜를 반환한다.
	 * @return
	 */
	def getStartOfYearWeek(year: Int,
	                       weekOfYear: Int = 1,
	                       locale: Locale = Locale.getDefault,
	                       weekOfYearRule: WeekOfYearRuleKind = WeekOfYearRuleKind.Caleandar): DateTime = {
		Guard.shouldNotBeNull(locale, "locale")
		Guard.shouldBeBetween(weekOfYear, 1, TimeSpec.MaxWeeksPerYear, "weekOfYear")

		if (log.isDebugEnabled)
			log.debug("해당 주차의 시작일을 구합니다. year=[{}], weekOfYear=[{}], locale=[{}], weekOfYearRule=[{}]",
			          year, weekOfYear, locale, weekOfYearRule)

		var weekday = new DateTime().withDate(year, 1, 1).plusDays(weekOfYear * TimeSpec.DaysPerWeek)
		var current = getWeekOfYear(weekday, locale, weekOfYearRule)

		// end date of week
		while (current.weekOfYear != weekOfYear) {
			weekday = weekday.minusDays(1)
			current = getWeekOfYear(weekday, locale, weekOfYearRule)
		}
		// end of previous week
		while (current.weekOfYear == weekOfYear) {
			weekday = weekday.minusDays(1)
			current = getWeekOfYear(weekday, locale, weekOfYearRule)
		}

		weekday.plusDays(1)
	}

	def dayStart(moment: DateTime): DateTime = moment.withTimeAtStartOfDay()

	def nextDayOfWeek(dayOfWeek: DayOfWeek): DayOfWeek = addDays(dayOfWeek, 1)

	def previousDayOfWeek(dayOfWeek: DayOfWeek): DayOfWeek = addDays(dayOfWeek, -1)

	/**
	 * 지정한 요일에 특정 일수를 가감한 날의 요일을 구한다.
	 */
	def addDays(dayOfWeek: DayOfWeek, days: Int = 0): DayOfWeek = {
		if (days % TimeSpec.DaysPerWeek == 0)
			return dayOfWeek

		val weeks = Math.abs(days) / TimeSpec.DaysPerWeek + 1
		val offset = weeks * TimeSpec.DaysPerWeek + dayOfWeek.toInt
		val targetOffset = offset + days

		DayOfWeek.valueOf(targetOffset)
	}

	//
	// << Collections >>
	//

	def toTimePeriodCollection[T <: ITimePeriod](sequence: Iterable[T]): TimePeriodCollection = {
		new TimePeriodCollection(JavaConversions.asJavaIterable(sequence))
	}

	//
	// << Compares >>
	//

	/**
	 * 두 일자가 지정한 기간까지 모두 같은지 검사한다
	 */
	def isSameTime(left: DateTime, right: DateTime, periodKind: PeriodKind): Boolean = {
		periodKind match {
			case PeriodKind.Year => isSameYear(left, right)
			case PeriodKind.Halfyear => isSameHalfyear(left, right)
			case PeriodKind.Quarter => isSameQuarter(left, right)
			case PeriodKind.Month => isSameMonth(left, right)
			case PeriodKind.Week => isSameWeek(left, right, null, WeekOfYearRuleKind.Caleandar)
			case PeriodKind.Day => isSameDay(left, right)
			case PeriodKind.Hour => isSameHour(left, right)
			case PeriodKind.Minute => isSameMinute(left, right)
			case PeriodKind.Second => isSameSecond(left, right)
			case PeriodKind.Millisecond => isSameMillisecond(left, right)
			case _ => isSameDateTime(left, right)
		}
	}

	/**
	 * 두 일자가 년도가 같은지 검사합니다.
	 */
	def isSameYear(left: DateTime, right: DateTime, yearBaseMonth: Int = TimeSpec.CalendarYearStartMonth) =
		getYearOf(left, yearBaseMonth) == getYearOf(right, yearBaseMonth)

	/**
	 * 두 일자가 년도, 반기까지 같은지 검사한다.
	 */
	def isSameHalfyear(left: DateTime, right: DateTime, yearBaseMonth: Int = TimeSpec.CalendarYearStartMonth) = {
		isSameYear(left, right, yearBaseMonth) &&
		getHalfyearOfMonth(left.getMonthOfYear, yearBaseMonth) == getHalfyearOfMonth(right.getMonthOfYear, yearBaseMonth)
	}

	/**
	 * 두 일자가 년도, 분기까지 같은지 검사한다.
	 */
	def isSameQuarter(left: DateTime, right: DateTime, yearBaseMonth: Int = TimeSpec.CalendarYearStartMonth) = {
		isSameYear(left, right, yearBaseMonth) &&
		getQuarterOfMonth(left.getMonthOfYear, yearBaseMonth) == getHalfyearOfMonth(right.getMonthOfYear, yearBaseMonth)
	}

	/**
	 * 두 일자가 년, 월까지 같은지 검사한다.
	 */
	def isSameMonth(left: DateTime, right: DateTime) = {
		isSameYear(left, right) && (left.getMonthOfYear == right.getMonthOfYear)
	}

	def isSameWeek(left: DateTime,
	               right: DateTime,
	               locale: Locale,
	               weekOfYearRule: WeekOfYearRuleKind = WeekOfYearRuleKind.Caleandar) = {
		val leftYearWeek = getWeekOfYear(left, locale, weekOfYearRule)
		val rightYearWeek = getWeekOfYear(right, locale, weekOfYearRule)

		leftYearWeek == rightYearWeek
	}

	def isSameDay(left: DateTime, right: DateTime) = {
		isSameMonth(left, right) && (left.getDayOfMonth == right.getDayOfMonth)
	}

	def isSameHour(left: DateTime, right: DateTime) = {
		isSameDay(left, right) && (left.getHourOfDay == right.getHourOfDay)
	}

	def isSameMinute(left: DateTime, right: DateTime) = {
		isSameHour(left, right) && (left.getMinuteOfHour == right.getMinuteOfHour)
	}

	def isSameSecond(left: DateTime, right: DateTime) = {
		isSameMinute(left, right) && (left.getSecondOfMinute == right.getSecondOfMinute)
	}

	def isSameMillisecond(left: DateTime, right: DateTime) = left.getMillis == right.getMillis

	def isSameDateTime(left: DateTime, right: DateTime) = left == right

	//
	// << Current >>
	//

	/**
	 * 현 날짜가 속한 년의 시작 일자를 나타냅니다.
	 * @return
	 */
	def currentCalendarYear: DateTime = currentYear(TimeSpec.CalendarYearStartMonth)

	/**
	 * 현재 날짜가 속한 년의 시작일을 반환합니다. (한 해의 시작월이 다를 수 있습니다.)
	 */
	def currentYear(yearStartMonth: Int = TimeSpec.CalendarYearStartMonth): DateTime = {
		val now = getNow
		var year = now.getYear
		if (now.getMonthOfYear < yearStartMonth)
			year -= 1

		new DateTime().withDate(year, yearStartMonth, 1)
	}

	def currentCalendarHalfyear: DateTime = currentHalfyear(TimeSpec.CalendarYearStartMonth)

	def currentHalfyear(yearStartMonth: Int = TimeSpec.CalendarYearStartMonth): DateTime = {
		val now = getNow
		val halfyear = getHalfyearOfMonth(yearStartMonth, now.getMonthOfYear)
		val months = (halfyear.toInt - 1) * TimeSpec.MonthsPerHalfyear

		currentYear(yearStartMonth).plusMonths(months)
	}

	def currentCalendarQuarter: DateTime = currentQuarter(TimeSpec.CalendarYearStartMonth)

	def currentQuarter(yearStartMonth: Int = TimeSpec.CalendarYearStartMonth): DateTime = {
		val now = getNow
		val quarter = getQuarterOfMonth(yearStartMonth, now.getMonthOfYear)
		val months = quarter.toInt * TimeSpec.MonthsPerQuarter

		currentYear(yearStartMonth).plusMonths(months)
	}

	def currentMonth(): DateTime = TimeTool.trimToDay(getNow)

	def currentWeek(): DateTime = currentWeek(Locale.getDefault)

	def currentWeek(locale: Locale): DateTime = startTimeOfWeek(getNow, locale)

	def curerntWeek(firstDayOfWeek: DayOfWeek): DateTime = startTimeOfWeek(getNow, firstDayOfWeek)

	def currentDay: DateTime = getToday

	def currentHour: DateTime = TimeTool.trimToMinute(getNow)

	def currentMinute: DateTime = TimeTool.trimToSecond(getNow)

	def currentSecond: DateTime = TimeTool.trimToMillis(getNow)

	def currentMillisecond: DateTime = getNow

	//
	// << Start / End TimeOfYear >>
	//

	def startTimeOfYear(moment: DateTime): DateTime = {
		return startTimeOfYear(moment, TimeSpec.CalendarYearStartMonth)
	}

	def startTimeOfYear(moment: DateTime, startMonthOfYear: Int): DateTime = {
		val monthOffset: Int = moment.getMonthOfYear - startMonthOfYear
		val year: Int = if (monthOffset < 0) moment.getYear - 1 else moment.getYear
		val result: DateTime = new DateTime(year, startMonthOfYear, 1, 0, 0)
		if (log.isDebugEnabled) log.debug("DateTime [{}]이 속한 Year의 시작일은 [{}] 입니다. startMonthOfYear=[{}]", moment, result, startMonthOfYear)
		return result
	}

	def startTimeOfYear(year: Int): DateTime = {
		return startTimeOfYear(year, TimeSpec.CalendarYearStartMonth)
	}

	def startTimeOfYear(year: Int, startMonthOfYear: Int): DateTime = {
		return new DateTime(year, startMonthOfYear, 1, 0, 0)
	}

	def endTimeOfYear(moment: DateTime): DateTime = {
		return endTimeOfYear(moment, TimeSpec.CalendarYearStartMonth)
	}

	def endTimeOfYear(moment: DateTime, startMonthOfYear: Int): DateTime = {
		return startTimeOfYear(moment, startMonthOfYear).plusYears(1).minus(TimeSpec.MinTicks)
	}

	def endTimeOfYear(year: Int): DateTime = {
		return endTimeOfYear(year, TimeSpec.CalendarYearStartMonth)
	}

	def endTimeOfYear(year: Int, startMonthOfYear: Int): DateTime = {
		return endTimeOfYear(startTimeOfYear(year, startMonthOfYear), startMonthOfYear)
	}

	/**
	 * 전년도 시작 시각
	 */
	def startTimeOfLastYear(moment: DateTime): DateTime = {
		return startTimeOfYear(moment.getYear - 1)
	}

	/**
	 * 전년도 마지막 시각
	 */
	def endTimeOfLastYear(moment: DateTime): DateTime = {
		return endTimeOfYear(moment.getYear - 1)
	}

	//
	// << Start / End TimeOfHalfyear >>
	//

	def startTimeOfHalfyear(moment: DateTime): DateTime = startTimeOfHalfyear(moment, TimeSpec.CalendarYearStartMonth)

	def startTimeOfHalfyear(moment: DateTime, startMonthOfYear: Int): DateTime = {
		throw new NotImplementedException()
	}

	def startTimeOfHalfyear(year: Int, halfYearKind: HalfYearKind): DateTime =
		startTimeOfHalfyear(year, halfYearKind, TimeSpec.CalendarYearStartMonth)

	def startTimeOfHalfyear(year: Int, halfyearKind: HalfYearKind, startMonthOfYear: Int): DateTime = {
		val month: Int = (halfyearKind.toInt - 1) * TimeSpec.MonthsPerHalfyear + 1
		startTimeOfHalfyear(new DateTime(year, month, 1, 0, 0), startMonthOfYear)
	}

	def endTimeOfHalfyear(moment: DateTime): DateTime = endTimeOfHalfyear(moment, TimeSpec.CalendarYearStartMonth)

	def endTimeOfHalfyear(moment: DateTime, startMonthOfYear: Int): DateTime =
		startTimeOfHalfyear(moment).plusMonths(TimeSpec.MonthsPerHalfyear).minus(TimeSpec.MinPositiveDuration)

	def endTimeOfHalfyear(year: Int, halfYearKind: HalfYearKind): DateTime =
		endTimeOfHalfyear(year, halfYearKind, TimeSpec.CalendarYearStartMonth)

	def endTimeOfHalfyear(year: Int, halfyearKind: HalfYearKind, startMonthOfYear: Int): DateTime = {
		val month: Int = (halfyearKind.toInt - 1) * TimeSpec.MonthsPerHalfyear + 1
		endTimeOfHalfyear(new DateTime(year, month, 1, 0, 0), startMonthOfYear)
	}

	//
	// << startTimeOfMonth / endTimeOfMonth >>
	//

	/**
	 * 지정한 일자의 해당월의 시작일
	 */

	def startTimeOfMonth(moment: DateTime): DateTime = moment.withTimeAtStartOfDay().withDayOfMonth(1)

	/**
	 * 지정한 일자의 해당월의 시작일
	 */
	def startTimeOfMonth(year: Int, month: MonthKind): DateTime = new DateTime(year, month.toInt, 1, 0, 0)

	/**
	 * 지정한 년/월의 시작일자
	 */
	def startTimeOfMonth(year: Int, monthOfYear: Int): DateTime = new DateTime(year, monthOfYear, 1, 0, 0)

	def endTimeOfMonth(moment: DateTime): DateTime =
		startTimeOfMonth(moment).plusMonths(1).minus(TimeSpec.MinPeriodDuration)

	def endTimeOfMonth(year: Int, month: MonthKind): DateTime =
		startTimeOfMonth(year, month).plusMonths(1).minus(TimeSpec.MinPeriodDuration)

	def endTimeOfMonth(year: Int, month: Int = 1): DateTime =
		startTimeOfMonth(year, month).plusMonths(1).minus(TimeSpec.MinPeriodDuration)

	def startTimeOfLastMonth(current: DateTime): DateTime = startTimeOfMonth(current.minusMonths(1))

	def endTimeOfLastMonth(current: DateTime): DateTime = endTimeOfMonth(current.minusMonths(1))


	//
	// << Iterables >>
	//

	def forEachYears(period: ITimePeriod): ArrayBuffer[ITimePeriod] = {
		if (log.isDebugEnabled)
			log.debug("기간 [{}]에 대해 Year 단위로 열거합니다")

		if (period == null || period.isAnyTime)
			ArrayBuffer.empty

		assertHasPeriod(period)

		val result: ArrayBuffer[ITimePeriod] = new ArrayBuffer[ITimePeriod]()

		if (period.getStart.getYear == period.getEnd.getYear) {
			result += getTimeRange(period.getStart, period.getEnd)
		} else {
			result += getTimeRange(period.getStart, endTimeOfYear(period.getStart))

			var currentTime = startTimeOfYear(period.getStart).plusYears(1)
			val endYear: Int = period.getEnd.getYear

			while (currentTime.getYear < endYear) {
				result += getYearRange(currentTime)
				currentTime = currentTime.plusYears(1)
			}
			if (currentTime.getMillis < period.getEnd.getMillis)
				result += getTimeRange(startTimeOfYear(currentTime), period.getEnd))
		}
		result

	}

	def assertHasPeriod(period: ITimePeriod) {
		Guard.assertTrue(period.hasPeriod, "명시적인 기간이 설정되지 않았습니다. period=[{}]", period)
	}


	//
	// << Math >>
	//

	def min(a: Date, b: Date): Date = {
		if (a != null && b != null) {
			if ((a.compareTo(b) < 0)) a else b
		}
		if (a != null) a else b
	}

	def max(a: Date, b: Date): Date = {
		if (a != null && b != null) {
			if ((a.compareTo(b) > 0)) a else b
		}
		if (a != null) a else b
	}

	def min(a: DateTime, b: DateTime): DateTime = {
		if (a != null && b != null)
			if ((a.compareTo(b) < 0)) a else b

		if (a != null) a else b
	}

	def max(a: DateTime, b: DateTime): DateTime = {
		if (a != null && b != null)
			if ((a.compareTo(b) > 0)) a else b

		if (a != null) a else b
	}

	def adjustPeriod(start: Date, end: Date) {
		if (start != null && end != null) {
			if (start.compareTo(end) > 0) {
				(end, start)
			}
		}
		(start, end)
	}

	def adjustPeriod(start: Date, duration: Long) {
		if (start == null || duration == null)
			(start, duration)

		if (duration < 0L) (new Date(start.getTime + duration), -duration) else (start, duration)
	}

	def adjustPeriod(start: DateTime, end: DateTime) = {
		if (start == null || end == null)
			(start, end)

		if (start.compareTo(end) > 0) (end, start) else (start, end)
	}

	def adjustPeriod(start: DateTime, duration: Long) = {
		if (start == null || duration == null)
			(start, duration)

		if (duration < 0) (start.plus(duration), -duration) else (start, duration)
	}

	//
	// << Period >>
	//


	def getTimeBlock(start: DateTime, duration: Long): TimeBlock = {
		new TimeBlock(start, duration, false)
	}

	def getTimeBlock(start: DateTime, end: DateTime): TimeBlock = {
		new TimeBlock(start, end, false)
	}

	def getTimeRange(start: DateTime, duration: Long): TimeRange = {
		new TimeRange(start, duration, false)
	}

	def getTimeRange(start: DateTime, end: DateTime): TimeRange = {
		new TimeRange(start, end, false)
	}

	def getRelativeYearPeriod(start: DateTime, years: Int): TimeRange = {
		new TimeRange(start, start.plusYears(years))
	}

	def getRelativeMonthPeriod(start: DateTime, months: Int): TimeRange = {
		new TimeRange(start, start.plusMonths(months))
	}

	def getRelativeDayPeriod(start: DateTime, days: Int): TimeRange = {
		new TimeRange(start, start.plusDays(days))
	}

	//
	// << Relation >>
	//

	def hasInside(period: ITimePeriod, target: DateTime): Boolean = {
		Guard.shouldNotBeNull(period, "period")
		Guard.shouldNotBeNull(target, "target")

		val hasInside: Boolean = (target.compareTo(period.getStart) >= 0) && (target.compareTo(period.getEnd) <= 0)

		if (isDebugEnabled)
			log.debug("기간[{}]에 대상날짜[{}]가 포함(Inside)되는지 여부를 검사. hasInside=[{}]",
			          asString(period), target, hasInside)

		hasInside
	}

	def hasInside(period: ITimePeriod, target: ITimePeriod): Boolean =
		hasInside(period, target.getStart) && hasInside(period, target.getEnd)

	def isAnytime(period: ITimePeriod): Boolean = period != null && period.isAnyTime

	def isNotAnytime(period: ITimePeriod): Boolean = period != null && !period.isAnyTime

	/**
	 * 두 기간이 어떤 관계인지 파악합니다. (선/후/중첩 등)
	 */
	def getRelation(period: ITimePeriod, target: ITimePeriod): PeriodRelation = {

		Guard.shouldNotBeNull(period, "period")
		Guard.shouldNotBeNull(target, "target")

		var relation: PeriodRelation = PeriodRelation.NoRelation

		if (period.getStart.compareTo(target.getEnd) > 0) {
			relation = PeriodRelation.After
		} else if (period.getEnd.compareTo(target.getStart) < 0) {
			relation = PeriodRelation.Before
		} else if ((period.getStart == target.getStart) && (period.getEnd == target.getEnd)) {
			relation = PeriodRelation.ExactMatch
		} else if (period.getStart == target.getEnd) {
			relation = PeriodRelation.StartTouching
		} else if (period.getEnd == target.getStart) {
			relation = PeriodRelation.EndTouching
		} else if (hasInside(period, target)) {
			if (period.getStart == target.getStart) {
				relation = PeriodRelation.EnclosingStartTouching
			}
			else {
				relation = if (((period.getEnd == target.getEnd))) PeriodRelation.EnclosingEndTouching else PeriodRelation.Enclosing
			}
		} else {
			val insideStart: Boolean = hasInside(target, period.getStart)
			val insideEnd: Boolean = hasInside(target, period.getEnd)
			if (insideStart && insideEnd) {
				relation = if ((period.getStart == target.getStart)) PeriodRelation.InsideStartTouching else if (((period.getEnd == target.getEnd))) PeriodRelation.InsideEndTouching else PeriodRelation.Inside
			}
			else if (insideStart) {
				relation = PeriodRelation.StartInside
			}
			else if (insideEnd) {
				relation = PeriodRelation.EndInside
			}
		}
		if (isDebugEnabled)
			log.debug("period[{}], target[{}] 의 relation=[{}] 입니다.", asString(period), asString(target), relation)

		relation
	}

	/**
	 * 두 기간의 교집합에 해당하는 기간이 있는지 검사한다.
	 */
	def intersectsWith(period: ITimePeriod, target: ITimePeriod): Boolean = {
		Guard.shouldNotBeNull(period, "period")
		Guard.shouldNotBeNull(target, "target")

		val isIntersected: Boolean =
			hasInside(period, target.getStart) ||
			hasInside(period, target.getEnd) ||
			(target.getStart.compareTo(period.getStart) < 0 && target.getEnd.compareTo(period.getEnd) > 0)

		if (isDebugEnabled)
			log.debug("period=[{}], target=[{}]에 교차구간이 있는지 확인합니다. isIntersected=[{}]",
			          asString(period), asString(target), isIntersected)

		isIntersected
	}

	/**
	 * 두 기간이 겹치는 기간이 있는지 검사한다.
	 */
	def overlapsWith(period: ITimePeriod, target: ITimePeriod): Boolean = {
		Guard.shouldNotBeNull(period, "period")
		Guard.shouldNotBeNull(target, "target")

		val relation: PeriodRelation = getRelation(period, target)
		val isOverlaps: Boolean =
			relation != PeriodRelation.After &&
			relation != PeriodRelation.StartTouching &&
			relation != PeriodRelation.EndTouching &&
			relation != PeriodRelation.Before &&
			relation != PeriodRelation.NoRelation
		if (isDebugEnabled)
			log.debug("period=[{}], target=[{}]이 overlap 되는지 확인합니다. isOverlaps=[{}]",
			          asString(period), asString(target), isOverlaps)

		isOverlaps
	}

	/**
	 * 두 기간의 교집합을 반환한다.
	 */
	def getIntersectionBlock(period: ITimePeriod, target: ITimePeriod): TimeBlock = {
		Guard.shouldNotBeNull(period, "period")
		Guard.shouldNotBeNull(target, "target")

		var intersectionBlock: TimeBlock = null

		if (intersectsWith(period, target)) {
			val start: DateTime = max(period.getStart, target.getStart)
			val end: DateTime = min(period.getEnd, target.getEnd)
			intersectionBlock = new TimeBlock(start, end, period.isReadonly)
		}
		if (isDebugEnabled)
			log.debug("period=[{}], target=[{}]의 교집합 TimeBlock=[{}]을 구했습니다.",
			          asString(period), asString(target), asString(intersectionBlock))

		intersectionBlock
	}

	def getUnionBlock(period: ITimePeriod, target: ITimePeriod): TimeBlock = {
		Guard.shouldNotBeNull(period, "period")
		Guard.shouldNotBeNull(target, "target")

		val start: DateTime = min(period.getStart, target.getStart)
		val end: DateTime = max(period.getEnd, target.getEnd)
		val unionBlock: TimeBlock = new TimeBlock(start, end, period.isReadonly)

		if (isDebugEnabled)
			log.debug("period=[{}]와 target=[{}]의 합집합 TimeBlock=[{}]을 구했습니다.",
			          asString(period), asString(target), asString(unionBlock))

		unionBlock
	}

	/**
	 * 두 기간의 교집합을 구하여 TimeRange 인스턴스를 빌드하여 반환합니다.
	 */
	def getIntersectionRange(period: ITimePeriod, target: ITimePeriod): TimeRange = {
		Guard.shouldNotBeNull(period, "period")
		Guard.shouldNotBeNull(target, "target")
		var intersectionRange: TimeRange = null
		if (intersectsWith(period, target)) {
			val start: DateTime = max(period.getStart, target.getStart)
			val end: DateTime = min(period.getEnd, target.getEnd)
			intersectionRange = new TimeRange(start, end, period.isReadonly)
		}
		if (log.isDebugEnabled) log.debug("period=[{}]와 target=[{}]의 교집합 TimeRange=[{}]을 구했습니다.", asString(period),
		                                  asString(target), asString(intersectionRange))
		return intersectionRange
	}

	/**
	 * 두 기간의 합집합을 구하여 {@link TimeRange} 인스턴스를 빌드하여 반환합니다.
	 */
	def getUnionRange(period: ITimePeriod, target: ITimePeriod): TimeRange = {
		Guard.shouldNotBeNull(period, "period")
		Guard.shouldNotBeNull(target, "target")
		var unionRange: TimeRange = null
		if (intersectsWith(period, target)) {
			val start: DateTime = min(period.getStart, target.getStart)
			val end: DateTime = max(period.getEnd, target.getEnd)
			unionRange = new TimeRange(start, end, period.isReadonly)
		}
		if (log.isDebugEnabled)
			log.debug("period=[{}]와 target=[{}]의 합집합 TimeRange=[{}]을 구했습니다.", asString(period), asString(target), asString(unionRange))
		return unionRange
	}

	//
	// << Trim >>
	//

	def trimToYear(moment: DateTime): DateTime = moment.withDate(moment.getYear, 1, 1)

	def trimToMonth(moment: DateTime, monthOfYear: Int = 1): DateTime = new DateTime().withDate(moment.getYear, monthOfYear, 1)

	def trimToDay(moment: DateTime, dayOfMonth: Int = 1): DateTime = moment.withTimeAtStartOfDay().withDayOfMonth(dayOfMonth)

	def trimToHour(moment: DateTime, hoursOfDay: Int = 0): DateTime = trimToMinute(moment).withHourOfDay(hoursOfDay)

	def trimToMinute(moment: DateTime, minutesOfHour: Int = 0): DateTime = trimToSecond(moment).withMinuteOfHour(minutesOfHour)

	def trimToSecond(moment: DateTime, secondsOfMinute: Int = 0): DateTime = trimToMillis(moment).withSecondOfMinute(secondsOfMinute)

	def trimToMillis(moment: DateTime, millisOfSecond: Int = 0): DateTime = moment.withMillisOfSecond(millisOfSecond)

	//
	// << Validation >>
	//

	def assertValidPeriod(start: DateTime, end: DateTime) {
		if (start != null && end != null)
			Guard.assertTrue(start.getMillis <= end.getMillis,
			                 "시작 시각이 완료 시각보다 이전 시각이어야 합니다. Start=[%s], End=[%s]", start, end);
	}

	def assertMutable(period: ITimePeriod) {
		Guard.assertTrue(period.isReadonly == false, "ITimePeriod 가 읽기 전용이면 안됩니다. period=" + period)
	}

	def allItemsAreEqual[T <: ITimePeriod](left: Iterable[T], right: Iterable[T]): Boolean = {

		if (left.size != right.size)
			false

		val leftIter = left.iterator
		val rightIter = right.iterator
		while (leftIter.hasNext && rightIter.hasNext) {
			if (leftIter.next() != rightIter.next())
				false
		}
		true
	}

	def isWeekday(dayOfWeek: DayOfWeek): Boolean = !isWeekend(dayOfWeek)

	def isWeekend(dayOfWeek: DayOfWeek): Boolean = (dayOfWeek == DayOfWeek.Saturday) || (dayOfWeek == DayOfWeek.Sunday)
}
