package kr.kth.commons.timeperiod

import org.joda.time.{Duration, DateTime}
import grizzled.slf4j.Logger
import java.util.{Calendar, Date, Locale}
import kr.kth.commons.base.{Guard, NotImplementedException}
import collection.JavaConversions
import kr.kth.commons.tools.StringTool
import timerange.{YearRangeCollection, YearRange, TimeRange}
import tools.TimeTool
import collection.mutable.ArrayBuffer

/**
 * DateTime 관련 Object
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 27
 */
object Times {

	lazy val log = Logger[this.type]
	lazy val isDebugEnabled = log.isDebugEnabled

	val NullStr = "null"
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

		val factory = if (valueFactory != null) valueFactory else (() => Times.UnixEpoch)
		try {
			DateTime.parse(str)
		} catch {
			case e: Exception => factory.apply()
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
		new DateTime().withDate(year, month, 1).plusMonths(1).minusDays(1).getDayOfMonth


	/**
	 * 지정된 시간이 속한 주(Week)의 첫번째 요일을 가져옵니다. (한국/미국은 한주의 시작은 일요일이고, ISO 8601 에서는 월요일이다)
	 */
	def getStartOfWeek(moment: DateTime, firstDayOfWeek: DayOfWeek = TimeSpec.FirstDayOfWeek): DateTime = {
		var currentDay = datePart(moment)
		while (currentDay.getDayOfWeek != firstDayOfWeek.toInt) {
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

		// end datePart of week
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

	def currentWeek(locale: Locale): DateTime = {
		val firstDayOfWeek = Calendar.getInstance(locale).getFirstDayOfWeek
		startTimeOfWeek(getNow, DayOfWeek.valueOf(firstDayOfWeek))
	}

	def curerntWeek(firstDayOfWeek: DayOfWeek): DateTime = startTimeOfWeek(getNow, firstDayOfWeek)

	def currentDay: DateTime = getToday

	def currentHour: DateTime = TimeTool.trimToMinute(getNow)

	def currentMinute: DateTime = TimeTool.trimToSecond(getNow)

	def currentSecond: DateTime = TimeTool.trimToMillis(getNow)

	def currentMillisecond: DateTime = getNow

	//
	// << Start / End TimeOfYear >>
	//

	def startTimeOfYear(moment: DateTime): DateTime = startTimeOfYear(moment, TimeSpec.CalendarYearStartMonth)

	def startTimeOfYear(moment: DateTime, startMonthOfYear: Int): DateTime = {
		val monthOffset: Int = moment.getMonthOfYear - startMonthOfYear
		val year: Int = if (monthOffset < 0) moment.getYear - 1 else moment.getYear

		val result: DateTime = new DateTime(year, startMonthOfYear, 1, 0, 0)

		if (log.isDebugEnabled)
			log.debug("DateTime [{}]이 속한 Year의 시작일은 [{}] 입니다. startMonthOfYear=[{}]", moment, result, startMonthOfYear)

		result
	}

	def startTimeOfYear(year: Int): DateTime = startTimeOfYear(year, TimeSpec.CalendarYearStartMonth)

	def startTimeOfYear(year: Int, startMonthOfYear: Int): DateTime = new DateTime(year, startMonthOfYear, 1, 0, 0)

	def endTimeOfYear(moment: DateTime): DateTime = endTimeOfYear(moment, TimeSpec.CalendarYearStartMonth)

	def endTimeOfYear(moment: DateTime, startMonthOfYear: Int): DateTime =
		startTimeOfYear(moment, startMonthOfYear).plusYears(1).minus(TimeSpec.MinTicks)

	def endTimeOfYear(year: Int): DateTime = endTimeOfYear(year, TimeSpec.CalendarYearStartMonth)

	def endTimeOfYear(year: Int, startMonthOfYear: Int): DateTime =
		endTimeOfYear(startTimeOfYear(year, startMonthOfYear), startMonthOfYear)

	/**
	 * 전년도 시작 시각
	 */
	def startTimeOfLastYear(moment: DateTime): DateTime = startTimeOfYear(moment.getYear - 1)

	/**
	 * 전년도 마지막 시각
	 */
	def endTimeOfLastYear(moment: DateTime): DateTime = endTimeOfYear(moment.getYear - 1)

	//
	// << Start / End TimeOfHalfyear >>
	//

	/**
	 * 지정한 날짜가 속한 반기(Halfyear)의 시작일
	 */
	def startTimeOfHalfyear(moment: DateTime, startMonthOfYear: Int = TimeSpec.CalendarYearStartMonth): DateTime = {
		val halfyear = getHalfyearOfMonth(startMonthOfYear, moment.getMonthOfYear).toInt
		val months = (halfyear - 1) * TimeSpec.MonthsPerHalfyear
		val result = startTimeOfYear(moment, startMonthOfYear).plusMonths(months)

		if (log.isDebugEnabled)
			log.debug("일자[{}]가 속한 반기의 시작일은 [{}]입니다. startMonthOfYear=[{}]", moment, result, startMonthOfYear)

		result
	}

	/**
	 * 지정한 년도와 반기(Halfyear)의 시작일
	 */
	def startTimeOfHalfyearByYear(year: Int,
	                              halfyearKind: HalfYearKind,
	                              startMonthOfYear: Int = TimeSpec.CalendarYearStartMonth): DateTime = {
		val month = (halfyearKind.toInt - 1) * TimeSpec.MonthsPerHalfyear + 1
		startTimeOfHalfyear(new DateTime().withDate(year, month, 1), startMonthOfYear)
	}

	/**
	 * 지정한 날짜가 속한 반기(Halfyear)의 마지막 일
	 */
	def endTimeOfHalfyear(moment: DateTime, startMonthOfYear: Int = TimeSpec.CalendarYearStartMonth): DateTime =
		startTimeOfHalfyear(moment).plusMonths(TimeSpec.MonthsPerHalfyear).minus(TimeSpec.MinPositiveDuration)

	/**
	 * 지정한 년도와 반기(Halfyear)의 마지막 일
	 */
	def endTimeOfHalfyearByYear(year: Int,
	                            halfyearKind: HalfYearKind,
	                            startMonthOfYear: Int = TimeSpec.CalendarYearStartMonth): DateTime = {
		val month = (halfyearKind.toInt - 1) * TimeSpec.MonthsPerHalfyear + 1
		endTimeOfHalfyear(new DateTime().withDate(year, month, 1), startMonthOfYear)
	}

	//
	// startTimeOfQuarter / endTimeOfQuarter
	//

	def startTimeOfQuarter(moment: DateTime, startMonthOfYear: Int = TimeSpec.CalendarYearStartMonth): DateTime = {
		val quarter = getQuarterOfMonth(moment.getMonthOfYear, startMonthOfYear).toInt
		val months = (quarter - 1) * TimeSpec.MonthsPerQuarter

		val result = startTimeOfYear(moment, startMonthOfYear).plusMonths(months)

		if (log.isDebugEnabled)
			log.debug("일자 [{}]이 속한 Halfyear의 시작일은 [{}]입니다. startMonthOfYear=[{}]", moment, result, startMonthOfYear)

		result
	}

	def startTimeOfQuarterByYear(year: Int,
	                             quarter: QuarterKind,
	                             yearStartMonth: Int = TimeSpec.CalendarYearStartMonth): DateTime = {
		val months = (quarter.toInt - 1) * TimeSpec.MonthsPerQuarter
		new DateTime().withDate(year, yearStartMonth, 1).plusMonths(months)
	}

	def endTimeOfQuarter(moment: DateTime, startMonthOfYear: Int = TimeSpec.CalendarYearStartMonth): DateTime =
		startTimeOfQuarter(moment, startMonthOfYear)
		.plusMonths(TimeSpec.MonthsPerQuarter)
		.minus(TimeSpec.MinPositiveDuration)

	def endTimeOfQuarterByYear(year: Int,
	                           quarter: QuarterKind,
	                           yearStartMonth: Int = TimeSpec.CalendarYearStartMonth): DateTime = {
		startTimeOfQuarterByYear(year, quarter, yearStartMonth)
		.plusMonths(TimeSpec.MonthsPerQuarter)
		.minus(TimeSpec.MinPositiveDuration)
	}

	def startOfLastQuarter(current: DateTime): DateTime =
		startTimeOfQuarterByYear(current.getYear, lastQuarterOf(current))

	def endOfLastQuarter(current: DateTime): DateTime =
		endTimeOfQuarterByYear(current.getYear, lastQuarterOf(current))

	//
	// << startTimeOfMonth / endTimeOfMonth >>
	//

	/**
	 * 지정한 일자의 해당월의 시작일자
	 */
	def startTimeOfMonth(moment: DateTime): DateTime = new DateTime().withDate(moment.getYear, moment.getMonthOfYear, 1)

	/**
	 * 지정한 년/월의 시작일자
	 */
	def startTimeOfMonthByYear(year: Int, monthOfYear: Int = TimeSpec.CalendarYearStartMonth): DateTime =
		new DateTime().withDate(year, monthOfYear, 1)

	/**
	 * 지정한 일자의 해당월의 종료일자
	 */
	def endTimeOfMonth(moment: DateTime): DateTime =
		startTimeOfMonth(moment).plusMonths(1).minus(TimeSpec.MinPositiveDuration)

	/**
	 * 지정한 년/월의 종료일자
	 */
	def endTimeOfMonthByYear(year: Int, monthOfYear: Int = TimeSpec.CalendarYearStartMonth): DateTime =
		startTimeOfMonthByYear(year, monthOfYear).plusMonths(1).minus(TimeSpec.MinPositiveDuration)

	/**
	 * 지정한 일자의 전월의 시작 시각
	 */
	def startTimeOfLastMonth(current: DateTime): DateTime = startTimeOfMonth(current.minusMonths(1))

	/**
	 * 지정한 일자의 전월의 마직막 시각
	 */
	def endTimeOfLastMonth(current: DateTime): DateTime = endTimeOfMonth(current.minusMonths(1))


	//
	// startTimeOfWeek / endTimeOfWeek
	//

	def startTimeOfWeek(moment: DateTime, firstDayOfWeek: DayOfWeek = TimeSpec.FirstDayOfWeek): DateTime = {
		val currentFirstDay = firstDayOfWeek
		var day = moment
		while (day.getDayOfWeek != currentFirstDay.toInt)
			day = day.minusDays(1)

		day.withTimeAtStartOfDay()
	}

	def startTimeOfWeekByLocale(moment: DateTime, locale: Locale): DateTime =
		startTimeOfWeek(moment, dayOfWeekByCalendarToJodaTime(locale))


	def endTimeOfWeek(moment: DateTime, firstDayOfWeek: DayOfWeek = TimeSpec.FirstDayOfWeek): DateTime =
		startTimeOfWeek(moment, firstDayOfWeek)
		.plusDays(TimeSpec.DaysPerWeek)
		.minus(TimeSpec.MinPositiveDuration)

	def endTimeOfWeekByLocale(moment: DateTime, locale: Locale): DateTime =
		endTimeOfWeek(moment, dayOfWeekByCalendarToJodaTime(locale))

	/**
	 * Calendar 요일 상수를 joda time의 요일 상수로 변환합니다. (Calendar는 SUNDAY=1 이고 joda는 ISO 규정에 따라 정에 MONDAY=1 이다 )
	 */
	private def dayOfWeekByCalendarToJodaTime(locale: Locale): DayOfWeek = {
		val dayOfWeek = (Calendar.getInstance(locale).getFirstDayOfWeek + 5) % 7 + 1
		DayOfWeek.valueOf(dayOfWeek)
	}


	def startTimeOfLastWeek(current: DateTime, firstDayOfWeek: DayOfWeek = TimeSpec.FirstDayOfWeek): DateTime =
		startTimeOfWeek(current, firstDayOfWeek).minusDays(TimeSpec.DaysPerWeek)

	def endTimeOfLastWeek(current: DateTime, firstDayOfWeek: DayOfWeek = TimeSpec.FirstDayOfWeek): DateTime =
		endTimeOfWeek(current, firstDayOfWeek).minusDays(TimeSpec.DaysPerWeek)


	//
	// startTimeOfDay / endTimeOfDay
	//

	def startTimeOfDay(moment: DateTime): DateTime = moment.withTimeAtStartOfDay()

	def endTimeOfDay(moment: DateTime): DateTime =
		moment.withTimeAtStartOfDay().plusDays(1).minus(TimeSpec.MinPositiveDuration)


	//
	// startTimeOfHour / endTimeofHour
	//

	def startTimeOfHour(moment: DateTime): DateTime = trimToMinute(moment)

	def endTimeOfHour(moment: DateTime): DateTime = startTimeOfHour(moment).plusHours(1).minus(1)

	def startTimeOfMinute(moment: DateTime): DateTime = trimToSecond(moment)

	def endTimeOfMinute(moment: DateTime): DateTime = startTimeOfMinute(moment).plusMinutes(1).minus(1)

	def startTimeOfSecond(moment: DateTime): DateTime = trimToMillis(moment)

	def endTimeOfSecond(moment: DateTime): DateTime = startTimeOfSecond(moment).plusSeconds(1).minus(1)

	//
	// HalfyearKind
	//

	def halfyearOf(month: Int): HalfYearKind =
		if (TimeSpec.FirstHalfyearMonths contains month) HalfYearKind.First
		else HalfYearKind.Second

	def halfyearOf(moment: DateTime): HalfYearKind = halfyearOf(moment.getMonthOfYear)

	def startMonthOfHalfyear(halfyear: HalfYearKind): Int =
		(halfyear.toInt - 1) * TimeSpec.MonthsPerHalfyear + 1

	def endMonthOfHalfyear(halfyear: HalfYearKind): Int =
		startMonthOfHalfyear(halfyear) + TimeSpec.MonthsPerHalfyear - 1

	//
	// QuarterKind
	//
	def startMonthOfQuarter(quarter: QuarterKind): Int =
		(quarter.toInt - 1) * TimeSpec.MonthsPerQuarter + 1

	def endMonthOfQuarter(quarter: QuarterKind): Int =
		quarter.toInt * TimeSpec.MonthsPerQuarter

	def quarterOf(month: Int): QuarterKind = {
		val quarter = (month - 1) / TimeSpec.MonthsPerQuarter + 1
		QuarterKind.valueOf(quarter)
	}

	def quarterOf(moment: DateTime): QuarterKind = quarterOf(moment.getMonthOfYear)

	/**
	 * 지정한 일자의 직전 분기를 구한다.
	 */
	def lastQuarterOf(moment: DateTime): QuarterKind =
		quarterOf(moment.minusMonths(TimeSpec.MonthsPerQuarter).getMonthOfYear)


	def nextDayOfWeek(current: DateTime): DateTime = nextDayOfWeek(current, current.getDayOfWeek)

	def nextDayOfWeek(current: DateTime, dayOfWeek: Int): DateTime = {
		Guard.shouldBeInRange(dayOfWeek, 1, 7, "dayOfWeek")
		var next = current
		do {
			next = next.plusDays(1)
		} while (next.dayOfWeek() != dayOfWeek)

		next
	}

	def prevDayOfWeek(current: DateTime, dayOfWeek: Int): DateTime = {
		Guard.shouldBeInRange(dayOfWeek, 1, 7, "dayOfWeek")
		var prev = current
		do {
			prev = prev.minusDays(1)
		} while (prev.dayOfWeek() != dayOfWeek)
		prev
	}


	def getDatePart(moment: DateTime): DateTime = moment.withTimeAtStartOfDay()

	def hasDatePart(moment: DateTime): Boolean = getDatePart(moment).getMillis > 0

	def setDatePart(moment: DateTime, datePart: DateTime): DateTime =
		setTimePart(datePart, moment.getMillisOfDay)

	def setDate(moment: DateTime, year: Int, month: Int = 1, day: Int = 1): DateTime =
		setTimePart(new DateTime().withDate(year, month, day), moment.getMillisOfDay)

	def setYear(moment: DateTime, year: Int): DateTime = moment.withYear(year)

	def setMonth(moment: DateTime, monthOfYear: Int): DateTime =
		moment.withMonthOfYear(monthOfYear)

	def setDay(moment: DateTime, dayOfMonth: Int): DateTime =
		moment.withDayOfMonth(dayOfMonth)

	def combineDate(datePart: DateTime, timePart: DateTime): DateTime =
		setTimePart(datePart, timePart.getMillisOfDay)

	def getTimePart(moment: DateTime): DateTime =
		new DateTime().withMillisOfDay(moment.getMillisOfDay)

	def hasTimePart(moment: DateTime): Boolean = getTimePart(moment).getMillis > 0

	def setTimePart(moment: DateTime, timePart: Int): DateTime =
		getDatePart(moment).plusMillis(timePart)

	def setTime(moment: DateTime, hour: Int = 0, minute: Int = 0, second: Int = 0, millis: Int = 0): DateTime =
		getDatePart(moment).withTime(hour, minute, second, millis)

	def setHour(moment: DateTime, hourOfDay: Int): DateTime = moment.withHourOfDay(hourOfDay)

	def setMinute(moment: DateTime, minuteOfHour: Int): DateTime = moment.withMinuteOfHour(minuteOfHour)

	def setSecond(moment: DateTime, secondOfMinute: Int): DateTime = moment.withSecondOfMinute(secondOfMinute)

	def setMillis(moment: DateTime, millisOfSecond: Int): DateTime = moment.withMillisOfSecond(millisOfSecond)

	//
	// Fluent Methods
	//

	/**
	 * 지정한 일자의 정오
	 */
	def noon(moment: DateTime): DateTime = getDatePart(moment).withHourOfDay(12)

	/**
	 * 지정한 시각에서 duration 이전의 시각 (과거)
	 */
	def ago(moment: DateTime, duration: Duration): DateTime = moment.minus(duration)

	/**
	 * 지정한 시각에서 duration 이후의 시각 (미래)
	 */
	def from(moment: DateTime, duration: Duration): DateTime = moment.plus(duration)

	def fromNow(duration: Duration): DateTime = from(getNow, duration)

	/**
	 * from 메소드와 같다 (지정한 시각에서 duration 이후의 시각 (미래))
	 */
	def since(moment: DateTime, duration: Duration): DateTime = from(moment, duration)

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
				result += getTimeRange(startTimeOfYear(currentTime), period.getEnd)
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

	def adjustPeriod(start: Date, end: Date): (Date, Date) = {
		if (start != null && end != null) {
			if (start.compareTo(end) > 0) {
				(end, start)
			}
		}
		(start, end)
	}

	def adjustPeriod(start: Date, duration: Long): (Date, Long) = {
		if (start == null || duration == 0)
			(start, duration)

		if (duration < 0L) (new Date(start.getTime + duration), -duration) else (start, duration)
	}

	def adjustPeriod(start: DateTime, end: DateTime): (DateTime, DateTime) = {
		if (start == null || end == null)
			(start, end)

		if (start.compareTo(end) > 0) (end, start) else (start, end)
	}

	def adjustPeriod(start: DateTime, duration: Long): (DateTime, Long) = {
		if (start == null || duration == 0)
			(start, duration)

		if (duration < 0) (start.plus(duration), -duration) else (start, duration)
	}

	//
	// << Period >>
	//


	def getTimeBlock(start: DateTime, duration: Long): TimeBlock = {
		new TimeBlock(start, duration, false)
	}

	def getTimeBlock(start: DateTime, duration: Duration): TimeBlock =
		new TimeBlock(start, duration.getMillis, false)

	def getTimeBlock(start: DateTime, end: DateTime): TimeBlock =
		new TimeBlock(start, end, false)


	def getTimeRange(start: DateTime, duration: Long): TimeRange = {
		new TimeRange(start, duration, false)
	}

	def getTimeRange(start: DateTime, duration: Duration): TimeRange =
		new TimeRange(start, duration.getMillis, false)

	def getTimeRange(start: DateTime, end: DateTime): TimeRange =
		new TimeRange(start, end, false)

	def getRelativeYearPeriod(start: DateTime, years: Int = 0): TimeRange =
		new TimeRange(start, start.plusYears(years))

	def getRelativeMonthPeriod(start: DateTime, months: Int = 0): TimeRange =
		new TimeRange(start, start.plusMonths(months))

	def getRelativeWeekPeriod(start: DateTime, weeks: Int = 0): TimeRange =
		new TimeRange(start, start.plusDays(weeks * TimeSpec.DaysPerWeek))

	def getRelativeDayPeriod(start: DateTime, days: Int = 0): TimeRange =
		new TimeRange(start, start.plusDays(days))

	def getRelativeHourPeriod(start: DateTime, hours: Int = 0): TimeRange =
		new TimeRange(start, start.plusHours(hours))

	def getRelativeMinutePeriod(start: DateTime, minutes: Int = 0): TimeRange =
		new TimeRange(start, start.plusMinutes(minutes))

	def getRelativeSecondPeriod(start: DateTime, seconds: Int = 0): TimeRange =
		new TimeRange(start, start.plusSeconds(seconds))

	def getPeriodOf(moment: DateTime, periodKind: PeriodKind, timeCalendar: ITimeCalendar = TimeCalendar.Default): ITimePeriod = {
		if (log.isDebugEnabled)
			log.debug("일자[{}]가 속한 기간종류[{}]의 기간을 구합니다. timeCalendar=[{}]", moment, periodKind, timeCalendar)

		periodKind match {
			case PeriodKind.Year => getYearRange(moment, timeCalendar)
		}
	}


	def getYearRange(moment: DateTime, timeCalendar: ITimeCalendar = TimeCalendar.Default): YearRange =
		new YearRange(moment, timeCalendar)

	def getYearRanges(moment: DateTime, yearCount: Int, timeCalendar: ITimeCalendar = TimeCalendar.Default): YearRangeCollection =
		new YearRangeCollection(moment, yearCount, timeCalendar)


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
				relation = if ((period.getStart == target.getStart)) PeriodRelation.InsideStartTouching
				           else if (((period.getEnd == target.getEnd))) PeriodRelation.InsideEndTouching
				           else PeriodRelation.Inside
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
		if (log.isDebugEnabled)
			log.debug("period=[{}]와 target=[{}]의 교집합 TimeRange=[{}]을 구했습니다.",
			          asString(period), asString(target), asString(intersectionRange))

		intersectionRange
	}

	/**
	 * 두 기간의 합집합을 구하여 TimeRange 인스턴스를 빌드하여 반환합니다.
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
			log.debug("period=[{}]와 target=[{}]의 합집합 TimeRange=[{}]을 구했습니다.",
			          asString(period), asString(target), asString(unionRange))

		unionRange
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
			                 "시작 시각이 완료 시각보다 이전 시각이어야 합니다. Start=[%s], End=[%s]", start, end)
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
