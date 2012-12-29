package kr.kth.commons.timeperiod

import org.joda.time.DateTime

/**
 * Date 관련 Object
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 30.
 */
object DateTool {

	val UnixEpoch = new DateTime().withDate(1970, 1, 1)


	def getStartOfYear(moment: DateTime): DateTime = new DateTime(moment.getYear, 1, 1, 0, 0)

	def getEndOfYear(moment: DateTime): DateTime = getStartOfYear(moment).plusYears(1).minus(1)


	def getStartOfCurrentYear: DateTime = getStartOfYear(DateTime.now())

	def getEndOfCurrentYear: DateTime = getEndOfYear(DateTime.now())

	def getStartOfLastYear: DateTime = getStartOfCurrentYear.minusYears(1)

	def getEndOfLastYear: DateTime = getEndOfCurrentYear.minusYears(1)

	def getHalfyear(moment: DateTime): HalfYearKind = {
		val halfyear = ((moment.getMonthOfYear - 1) / TimeSpec.MonthsPerHalfyear) + 1
		HalfYearKind.valueOf(halfyear)
	}

	def getStartOfHalfyear(moment: DateTime): DateTime =
		if (getHalfyear(moment) == HalfYearKind.First) getStartOfYear(moment) else getStartOfYear(moment).plusMonths(6)

	def getEndOfHalfyear(moment: DateTime): DateTime =
		getStartOfHalfyear(moment).plusMonths(TimeSpec.MonthsPerHalfyear).minus(1)

	/**
	 * 지정한 분기의 시작 월
	 */
	def getStartMonthOfQuarter(quarter: QuarterKind): Int = (quarter.toInt - 1) * TimeSpec.MonthsPerQuarter + 1

	/**
	 * 지정한 분기의 마직막 월
	 */
	def getEndMonthOfQuarter(quarter: QuarterKind): Int = quarter.toInt * TimeSpec.MonthsPerQuarter

	def getStartOfQuarter(quarter: QuarterKind, year: Int = DateTime.now().getYear): DateTime =
		new DateTime().withDate(year, getStartMonthOfQuarter(quarter), 1)


	def getEndOfQuarter(quarter: QuarterKind, year: Int = DateTime.now().getYear): DateTime = {
		val endMonth = getEndMonthOfQuarter(quarter)

		new DateTime()
		.withDate(year, endMonth, Times.getDaysInMonth(year, endMonth))
		.plusDays(1)
		.minus(TimeSpec.MinPositiveDuration)
	}

	/**
	 * 지정된 월이 속한 분기
	 */
	def getQuarterByMonth(monthOfYear: Int): QuarterKind = {
		val quarter = (monthOfYear - 1) / TimeSpec.MonthsPerQuarter + 1
		QuarterKind.valueOf(quarter)
	}

	def getQuarterByDate(date: DateTime) = getQuarterByMonth(date.getMonthOfYear)

	/**
	 * 지정한 날짜의 전분기를 구한다.
	 */
	def getLastQuarter(date: DateTime): QuarterKind =
		getQuarterByMonth(date.minusMonths(TimeSpec.MonthsPerQuarter).getMonthOfYear)

	/**
	 * 현재 날짜가 속한 분기
	 */
	def getCurrentQuarter: QuarterKind = getQuarterByMonth(DateTime.now().getMonthOfYear)

	def getStartOfCurrentQuarter: DateTime = getStartOfQuarter(getCurrentQuarter)

	def getEndOfCurrentQuarter: DateTime = getEndOfQuarter(getCurrentQuarter)

	def getStartOfLastQuarter: DateTime = getStartOfQuarter(getLastQuarter(DateTime.now()))

	def getEndOfLastQuarter: DateTime = getEndOfQuarter(getLastQuarter(DateTime.now()))

	def getStartOfMonth(year: Int, month: Int) = new DateTime().withDate(year, month, 1)

	def getEndOfMonth(year: Int, month: Int) =
		getStartOfMonth(year, month).dayOfMonth().withMaximumValue().millisOfDay().withMaximumValue()

	def getStartOfCurrentMonth = DateTime.now().dayOfMonth().withMinimumValue().withTimeAtStartOfDay()

	def getEndOfCurrentMonth = DateTime.now().dayOfMonth().withMaximumValue().millisOfDay().withMaximumValue()

	def getStartOfLastMonth = getStartOfCurrentMonth.minusMonths(1)

	def getEndOfLastMonth = getEndOfCurrentMonth.minusMonths(1)


	/**
	 * 지정된 날짜가 속한 주(week)의 첫번째 요일의 날짜 (한국:일요일, ISO8601:월요일)
	 */
	def getStartOfWeek(moment: DateTime, firstDayOfWeek: DayOfWeek = DayOfWeek.Monday): DateTime = {
		var add = firstDayOfWeek.toInt - moment.getDayOfWeek
		if (add > 0)
			add -= TimeSpec.DaysPerWeek

		moment.withTimeAtStartOfDay().plusDays(add)
	}

	/**
	 * 지정된 날짜가 속한 주(week)의 마지막 날짜 (한국:토요일, ISO8601:일요일)
	 */
	def getEndOfWeek(moment: DateTime, firstDayOfWeek: DayOfWeek = DayOfWeek.Monday): DateTime = {
		getStartOfWeek(moment).plusDays(TimeSpec.DaysPerWeek).minus(1)
	}

	/**
	 * 현재 주의 시작 시각
	 */
	def getStartOfCurrentWeek: DateTime = {
		var currentDay = DateTime.now().withTimeAtStartOfDay()
		currentDay.minusDays(currentDay.getDayOfWeek - 1)
	}

	/**
	 * 현재 주의 마지막 시각
	 */
	def getEndOfCurrentWeek: DateTime = getStartOfCurrentWeek.plusDays(TimeSpec.DaysPerWeek).minus(1)

	/**
	 * 전주의 시작 시각
	 * @return
	 */
	def getStartOfLastWeek: DateTime = getStartOfCurrentWeek.minusDays(TimeSpec.DaysPerWeek)

	/**
	 * 전주의 마지막 시각
	 * @return
	 */
	def getEndOfLastWeek: DateTime = getEndOfCurrentWeek.minusDays(TimeSpec.DaysPerWeek)


	def getStartOfDay(moment: DateTime): DateTime = moment.withTimeAtStartOfDay()

	def getEndOfDay(moment: DateTime): DateTime = moment.millisOfDay().withMaximumValue()


	/**
	 * 지정한 시각으로부터 가장 가까운 요일의 날짜를 가져온다. (현재 시각은 제외된다)
	 * @param current 기준 일자 (이 일자 이후로의 시각)
	 * @param dayOfWeek 원하는 요일
	 */
	def nextDayOfWeek(current: DateTime, dayOfWeek: DayOfWeek): DateTime = {
		var next = current.plusDays(1)
		while (next.getDayOfWeek != dayOfWeek.toInt) {
			next = next.plusDays(1)
		}
		next
	}

	/**
	 * 지정한 시각 이전에 가장 가까운 요일의 날짜를 가져온다. (현재 시각은 제외된다)
	 * @param current 기준 일자 (이 일자 이후로의 시각)
	 * @param dayOfWeek 원하는 요일
	 */
	def prevDayOfWeek(current: DateTime, dayOfWeek: DayOfWeek): DateTime = {
		var prev = current.minusDays(1)
		while (prev.getDayOfWeek != dayOfWeek.toInt) {
			prev = prev.minusDays(1)
		}
		prev
	}
}

