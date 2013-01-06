package kr.kth.commons.timeperiod.tools

import java.util.{Calendar, Locale}
import org.joda.time.{Duration, DateTime}
import kr.kth.commons.timeperiod._
import kr.kth.commons.timeperiod.Times
import kr.kth.commons.slf4j.Logger
import timerange.{TimeRange, WeekRange}

/**
 * kr.kth.commons.timeperiod.tools.WeekTools
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 29.
 */
object WeekTools {

	val log = Logger[WeekTools.type]

	val DefaultCalendarWeekRuleAndFirstDayOfWeek = (CalendarWeekRule.FirstFourDayWeek, DayOfWeek.Monday)

	def getCalendarWeekRuleByLocale(locale: Locale = Locale.getDefault): CalendarWeekRule = {
		val minDays = Calendar.getInstance(locale).getMinimalDaysInFirstWeek
		if (minDays == 1) {
			CalendarWeekRule.FirstDay
		} else if (minDays == 7) {
			CalendarWeekRule.FirstFullWeek
		} else {
			CalendarWeekRule.FirstFourDayWeek
		}
	}

	/**
	 * 주차 결정 종류에 따라 주차결정 및 한주 시작 요일을 반환한다.
	 */
	def getCalendarWeekRuleAndFirstDayOfWeek(locale: Locale,
	                                         weekOfYearRule: WeekOfYearRuleKind): (CalendarWeekRule, DayOfWeek) = {
		if (weekOfYearRule == WeekOfYearRuleKind.Caleandar) {
			val dayOfWeek = DayOfWeek.valueOf(Calendar.getInstance(locale).getFirstDayOfWeek)
			(getCalendarWeekRuleByLocale(locale), dayOfWeek)
		} else {
			DefaultCalendarWeekRuleAndFirstDayOfWeek
		}
	}

	/**
	 * 주차 계산 룰과 문화권에 따른 주차 계산 룰을 구합니다.
	 */
	def getCalendarWeekRule(locale: Locale,
	                        weekOfYearRule: WeekOfYearRuleKind = WeekOfYearRuleKind.Caleandar): CalendarWeekRule =
		if (weekOfYearRule == WeekOfYearRuleKind.Iso8601)
			CalendarWeekRule.FirstFourDayWeek
		else
			getCalendarWeekRuleByLocale(locale)


	/**
	 * 한주의 시작 요일을 반환합니다. ISO 8601 은 월요일을, 한국과 미국은 일요일을 한 주의 시작 요일로 정했다.
	 * @param locale
	 * @param weekOfYearRule
	 * @return
	 */
	def getFirstDayOfWeek(locale: Locale = Locale.getDefault,
	                      weekOfYearRule: WeekOfYearRuleKind = WeekOfYearRuleKind.Caleandar): DayOfWeek = {
		if (weekOfYearRule == WeekOfYearRuleKind.Iso8601)
			DayOfWeek.Monday
		else
			DayOfWeek.valueOf(Calendar.getInstance(locale).getFirstDayOfWeek)
	}

	/**
	 * 한해의 첫번째 주차 결정 규칙과 첫번째 요일 규칙을 이용하여, 주처 결정 룰을 반환합니다.
	 * @param weekRule  한해의 첫번째 주차 결정 규칙
	 * @param firstDayOfWeek  한주의 시작 요일
	 * @return  ISO 8601 이거나 Locale에 따른 것인가?
	 */
	def getWeekOfYearRuleKind(weekRule: CalendarWeekRule = CalendarWeekRule.FirstFourDayWeek,
	                          firstDayOfWeek: DayOfWeek = DayOfWeek.Monday): WeekOfYearRuleKind =
		if (weekRule == CalendarWeekRule.FirstFourDayWeek &&
		    firstDayOfWeek == DayOfWeek.Monday)
			WeekOfYearRuleKind.Iso8601
		else
			WeekOfYearRuleKind.Caleandar


	def getYearAdnWeek(moment: DateTime,
	                   locale: Locale = null,
	                   weekOfYearRule: WeekOfYearRuleKind = WeekOfYearRuleKind.Iso8601,
	                   yearStartMonth: Int = TimeSpec.CalendarYearStartMonth): YearAndWeek = {
		getYearAndWeek(moment, TimeCalendar(locale, yearStartMonth, weekOfYearRule))
	}

	def getYearAndWeek(moment: DateTime, timeCalendar: ITimeCalendar): YearAndWeek = {
		// TODO: ITimeCalendar의 WeekOfYearRuleKind 를 고려해서 계산하는 방식으로 변경해야 한다.
		new YearAndWeek(moment.getYear, moment.getWeekOfWeekyear)
	}

	def getEndYearAndWeek(year: Int, locale: Locale, weekOfYearRule: WeekOfYearRuleKind, yearStartMonth: Int): YearAndWeek = {
		getEndYearAndWeek(year, TimeCalendar(locale, yearStartMonth, weekOfYearRule))
	}

	def getEndYearAndWeek(year: Int, timeCalendar: ITimeCalendar): YearAndWeek = {
		val endOfYear = Times.endTimeOfYear(year)
		new YearAndWeek(year, endOfYear.getWeekOfWeekyear)
	}

	/**
	 * 해당 주차에 해당에 속한 기간
	 */
	def getWeekRange(yearAndWeek: YearAndWeek, timeCalendar: ITimeCalendar): WeekRange = {
		val startTime = Times.getStartOfYearWeek(yearAndWeek.getYear,
		                                         yearAndWeek.getWeekOfYear,
		                                         timeCalendar.getLocale,
		                                         timeCalendar.getWeekOfYearRule)

		val endTime = Times.endTimeOfWeek(startTime, timeCalendar.getFirstDayOfWeek)
		new WeekRange(new TimeRange(startTime, endTime), timeCalendar)
	}

	def getWeekRangeByLocale(yearAndWeek: YearAndWeek,
	                         locale: Locale = Locale.getDefault,
	                         weekOfYearRule: WeekOfYearRuleKind = WeekOfYearRuleKind.Iso8601,
	                         yearStartMonth: Int = TimeSpec.CalendarYearStartMonth): WeekRange = {
		val timeCalendar = TimeCalendar(locale, yearStartMonth, weekOfYearRule, Duration.ZERO, Duration.ZERO)
		getWeekRange(yearAndWeek, timeCalendar)
	}

	def getStartWeekRangeOfYear(year: Int, timeCalendar: ITimeCalendar = TimeCalendar.Default) = {
		getWeekRange(new YearAndWeek(year, 1), timeCalendar)
	}

	def getStartWeekRangeOfYearByLocale(year: Int,
	                                    locale: Locale = Locale.getDefault,
	                                    weekOfYearRule: WeekOfYearRuleKind = WeekOfYearRuleKind.Iso8601,
	                                    yearStartMonth: Int = TimeSpec.CalendarYearStartMonth): WeekRange = {
		val timeCalendar = TimeCalendar(locale, yearStartMonth, weekOfYearRule, Duration.ZERO, Duration.ZERO)
		getStartWeekRangeOfYear(year, timeCalendar)
	}


	def getEndWeekRangeOfYear(year: Int, timeCalendar: ITimeCalendar = TimeCalendar.Default) = {
		var endYearAndWeek = getEndYearAndWeek(year, timeCalendar)
		getWeekRange(endYearAndWeek, timeCalendar)
	}

	def getEndWeekRangeOfYearByLocale(year: Int,
	                                  locale: Locale = Locale.getDefault,
	                                  weekOfYearRule: WeekOfYearRuleKind = WeekOfYearRuleKind.Iso8601,
	                                  yearStartMonth: Int = TimeSpec.CalendarYearStartMonth): WeekRange = {
		val timeCalendar = TimeCalendar(locale, yearStartMonth, weekOfYearRule, Duration.ZERO, Duration.ZERO)
		getEndWeekRangeOfYear(year, timeCalendar)
	}

	def addWeekOfYears(yearAndWeek: YearAndWeek, weeks: Int, timeCalendar: ITimeCalendar): YearAndWeek = {

		val weekRange = getWeekRange(yearAndWeek, timeCalendar)
		val startTime = weekRange.getStart.plusWeeks(weeks)

		new YearAndWeek(startTime.getYear, startTime.getWeekOfWeekyear)
	}

	def addWeekOfYearsByLocale(yearAndWeek: YearAndWeek,
	                           weeks: Int,
	                           locale: Locale = Locale.getDefault,
	                           weekOfYearRule: WeekOfYearRuleKind = WeekOfYearRuleKind.Iso8601,
	                           yearStartMonth: Int = TimeSpec.CalendarYearStartMonth): YearAndWeek = {
		val timeCalendar = TimeCalendar(locale, yearStartMonth, weekOfYearRule, Duration.ZERO, Duration.ZERO)
		addWeekOfYears(yearAndWeek, weeks, timeCalendar)
	}
}
