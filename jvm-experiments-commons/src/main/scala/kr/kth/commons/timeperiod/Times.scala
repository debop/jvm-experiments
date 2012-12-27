package kr.kth.commons.timeperiod

import org.joda.time.DateTime
import grizzled.slf4j.Logger
import java.util.Locale
import kr.kth.commons.base.{Guard, NotImplementedException}
import collection.JavaConversions

/**
 * DateTime 관련 Object
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 27
 */
object Times {

	val log = Logger[this.type]

	val NullStr = "null"
	val UnixEpoch = new DateTime() withMillis 0

	def now: DateTime = DateTime.now

	def nowTime: Long = now.getMillis

	def asString(period: ITimePeriod) = if (period != null) period.getDurationDescription else NullStr

	def toDateTime(str: String, defaultVal: DateTime = UnixEpoch) = {
		try {
			DateTime.parse(str)
		} catch {
			case e: Exception => defaultVal
		}
	}

	def datePart(moment: DateTime): DateTime = moment.withTimeAtStartOfDay

	def timePart(moment: DateTime): DateTime = new DateTime() withMillis moment.getMillisOfDay


	// region << Calendar >>

	def getYearOf(year: Int, monthOfYear: Int = 1, startMonthOfYear: Int = TimeSpec.CalendarYearStartMonth): Int = {
		if (monthOfYear >= startMonthOfYear) year else year - 1
	}

	def getYearOfDate(moment: DateTime, startMonthOfYear: Int = TimeSpec.CalendarYearStartMonth): Int =
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
		val startQuarterCount = ((startYear + offsetYear) * TimeSpec.QuartersPerYear) + (startQuarter.intValue() - 1)
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
	 * 년, 주차에 해당하는 첫뻔재 날짜를 반환한다.
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


	// endregion

	// region << Collections >>

	def toTimePeriodCollection[T <: ITimePeriod](sequence: Iterable[T]): TimePeriodCollection = {
		new TimePeriodCollection(JavaConversions.asJavaIterable(sequence))
	}

	// endregion


	// region << startTimeOfMonth / endTimeOfMonth >>

	// 지정한 일자의 해당월의 시작일
	def startTimeOfMonth(moment: DateTime): DateTime =
		moment.withTimeAtStartOfDay().withDayOfMonth(1)

	//	def startTimeOfMonth(year: Int, month: MonthKind = MonthKind.January): DateTime =
	//		new DateTime(year, month.toInt, 1, 0, 0)

	/**
	 * 지정한 년/월의 시작일자
	 */
	def startTimeOfMonth(year: Int, month: Int = 1): DateTime = new DateTime(year, month, 1, 0, 0)

	def endTimeOfMonth(moment: DateTime): DateTime =
		startTimeOfMonth(moment).plusMonths(1).minus(TimeSpec.MinPeriodDuration)

	//	def endTimeOfMonth(year: Int, month: MonthKind = MonthKind.January): DateTime =
	//		startTimeOfMonth(year, month).plusMonths(1).minus(TimeSpec.MinPeriodDuration)

	def endTimeOfMonth(year: Int, month: Int = 1): DateTime =
		startTimeOfMonth(year, month).plusMonths(1).minus(TimeSpec.MinPeriodDuration)

	def startTimeOfLastMonth(current: DateTime): DateTime = startTimeOfMonth(current.minusMonths(1))

	def endTimeOfLastMonth(current: DateTime): DateTime = endTimeOfMonth(current.minusMonths(1))

	// endregion
}
