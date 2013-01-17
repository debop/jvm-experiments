package kr.kth.timeperiod

import com.google.common.base.Objects.ToStringHelper
import java.util.Locale
import kr.kth.commons.slf4j.Logging
import kr.kth.commons.tools.ScalaHash
import org.joda.time.{Duration, DateTime}
import kr.kth.commons.{ValueObjectBase, Guard}
import WeekOfYearRuleKind._, CalendarWeekRule._


/**
 * 문화권에 따른 날짜 표현, 날짜 계산 등을 제공하는 Calendar 입니다. (ISO 8601, Korean 등)
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 29.
 */
@SerialVersionUID(4848502849274L)
class TimeCalendar(config: TimeCalendarConfig) extends ITimeCalendar {
	{
		// 기본 생성자는 클래스 내의 모든 문장을 실행시킵니다.
		//

		val cfg = if (config != null) config else TimeCalendarConfig.Default

		Guard.shouldNotBeNegativeNumber(cfg.getStartOffset.getMillis, "startOffSet")
		Guard.shouldNotBeNegativeNumber(cfg.getEndOffset.getMillis, "endOffset")

		_locale = cfg.getLocale
		_yearKind = cfg.getYearKind
		_startOffset = cfg.getStartOffset
		_endOffset = cfg.getEndOffset
		_yearBaseMonth = cfg.getYearBaseMonth
		_weekOfYearRule = cfg.getWeekOfYearRule
		_calendarWeekRule = cfg.getCalendarWeekRule
		_firstDayOfWeek = cfg.getFirstDayOfWeek
	}
}

/**
 * TimeCalendar Object
 */
object TimeCalendar extends Logging {

	lazy val DefaultStartOffset = new Duration(TimeSpec.NoDuration)
	lazy val DufaultEndOffset = new Duration(TimeSpec.MinPositiveDuration)

	lazy val Default: TimeCalendar = apply()

	def apply(): TimeCalendar = new TimeCalendar(TimeCalendarConfig.Default)

	def apply(locale: Locale): TimeCalendar = {
		val cfg = TimeCalendarConfig.Default
		cfg.setLocale(locale)

		new TimeCalendar(cfg)
	}

	def apply(yearBaseMonth: Int): TimeCalendar = {
		val cfg = TimeCalendarConfig.Default
		cfg.setYearBaseMonth(yearBaseMonth)

		new TimeCalendar(cfg)
	}

	def apply(startOffset: Duration, endOffset: Duration, yearBaseMonth: Int): TimeCalendar = {
		val cfg = TimeCalendarConfig.Default

		cfg.setStartOffset(startOffset)
		cfg.setEndOffset(endOffset)
		cfg.setYearBaseMonth(yearBaseMonth)

		new TimeCalendar(cfg)
	}

	def apply(locale: Locale, startOffset: Duration, endOffset: Duration, yearBaseMonth: Int): TimeCalendar = {
		val cfg = TimeCalendarConfig.Default

		cfg.setLocale(locale)
		if (startOffset != null)
			cfg.setStartOffset(startOffset)
		if (endOffset != null)
			cfg.setEndOffset(endOffset)
		cfg.setYearBaseMonth(yearBaseMonth)

		new TimeCalendar(cfg)
	}

	def apply(locale: Locale, yearBaseMonth: Int, weekOfYearRule: WeekOfYearRuleKind): TimeCalendar = {
		val cfg = new TimeCalendarConfig(locale, weekOfYearRule)
		cfg.setYearBaseMonth(yearBaseMonth)

		new TimeCalendar(cfg)
	}

	def apply(locale: Locale,
	          yearBaseMonth: Int,
	          weekOfYearRule: WeekOfYearRuleKind,
	          startOffset: Duration,
	          endOffset: Duration): TimeCalendar = {
		val cfg = new TimeCalendarConfig(locale, weekOfYearRule)
		cfg.setYearBaseMonth(yearBaseMonth)
		if (startOffset != null)
			cfg.setStartOffset(startOffset)
		if (endOffset != null)
			cfg.setEndOffset(endOffset)

		new TimeCalendar(cfg)
	}

	def apply(locale: Locale, yearBaseMonth: Int): TimeCalendar = {
		val cfg = new TimeCalendarConfig(locale)
		cfg.setYearBaseMonth(yearBaseMonth)

		new TimeCalendar(cfg)
	}

}

/**
 * kr.kth.timeperiod.ITimeCalendar
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 13.
 */
trait ITimeCalendar extends ValueObjectBase with ITimePeriodMapper {

	import YearKind._, QuarterKind._, DayOfWeek._, WeekOfYearRuleKind._, HalfYearKind._

	protected var _locale = Locale.getDefault
	protected var _yearKind = YearKind.Calendar
	protected var _startOffset: Duration = Duration.ZERO
	protected var _endOffset: Duration = Duration.ZERO
	protected var _yearBaseMonth: Int = TimeSpec.CalendarYearStartMonth
	protected var _weekOfYearRule: WeekOfYearRuleKind = WeekOfYearRuleKind.Iso8601
	protected var _calendarWeekRule: CalendarWeekRule = CalendarWeekRule.FirstFourDayWeek
	protected var _firstDayOfWeek: DayOfWeek = DayOfWeek.Monday


	def getLocale: Locale = _locale

	def getYearKind: YearKind = _yearKind

	/**
	 * 시작일 오프셋
	 */
	def getStartOffset: Duration = _startOffset

	/**
	 * 종료일 오프셋
	 */
	def getEndOffset: Duration = _endOffset

	/**
	 * 년의 기준 월
	 */
	def getBaseMonthOfYear: Int = _yearBaseMonth

	def getWeekOfYearRule: WeekOfYearRuleKind = _weekOfYearRule

	def getCalendarWeekRule: CalendarWeekRule = _calendarWeekRule

	/**
	 * 한주의 시작 요일 (1: 월요일 ... 6:토요일, 7:일요일)
	 */
	def getFirstDayOfWeek: DayOfWeek = _firstDayOfWeek

	/**
	 * 일자의 년도를 구합니다.
	 */
	def getYear(moment: DateTime): Int = moment.getYear

	/**
	 * 월을 구합니다.
	 */
	// TODO: getMonthOfYear 로 변경
	def getMonth(moment: DateTime): Int = moment.getMonthOfYear

	/**
	 * 해당 월의 일를 구합니다.
	 */
	def getDayOfMonth(moment: DateTime): Int = moment.getDayOfMonth

	/**
	 * 요일을 구합니다.
	 */
	def getDayOfWeek(moment: DateTime): DayOfWeek = DayOfWeek(moment.getDayOfWeek)

	/**
	 * 시간을 구합니다.
	 */
	// TODO: getHourOfDay 로 변경
	def getHour(moment: DateTime): Int = moment.getHourOfDay

	/**
	 * 분을 구합니다.
	 */
	// TODO: getMinuteOfHour 로 변경
	def getMinute(moment: DateTime): Int = moment.getMinuteOfHour

	/**
	 * 해당 년/월의 일자 수 (28~31)
	 */
	def getDaysInMonth(year: Int, monthOfYear: Int): Int = Times.getDaysInMonth(year, monthOfYear)

	/**
	 * 년도 이름
	 */
	def getYearName(year: Int): String =
		_yearKind match {
			case YearKind.Calendar => TimeStrings.CalendarYearName(year)
			case YearKind.Fiscal => TimeStrings.FiscalYearName(year)
			case YearKind.School => TimeStrings.SchoolYearName(year)
			case YearKind.System => TimeStrings.SystemYearName(year)
			case _ => TimeStrings.CalendarYearName(year)
		}

	/**
	 * 반기를 표현하는 문자열을 반환합니다.
	 */
	def getHalfYearName(halfyear: HalfYearKind): String =
		_yearKind match {
			case YearKind.Calendar => TimeStrings.CalendarHalfyearName(halfyear)
			case YearKind.Fiscal => TimeStrings.FiscalHalfyearName(halfyear)
			case YearKind.School => TimeStrings.SchoolHalfyearName(halfyear)
			case YearKind.System => TimeStrings.SystemHalfyearName(halfyear)
			case _ => TimeStrings.CalendarHalfyearName(halfyear)
		}


	/**
	 * 지정한 년도와 반기를 표현하는 문자열을 반환합니다.
	 */
	def getHalfYearOfYearName(year: Int, halfyear: HalfYearKind): String =
		_yearKind match {
			case YearKind.Calendar => TimeStrings.CalendarHalfyearOfYearName(halfyear, year)
			case YearKind.Fiscal => TimeStrings.FiscalHalfyearOfYearName(halfyear, year)
			case YearKind.School => TimeStrings.SchoolHalfyearOfYearName(halfyear, year)
			case YearKind.System => TimeStrings.SystemHalfyearOfYearName(halfyear, year)
			case _ => TimeStrings.CalendarHalfyearOfYearName(halfyear, year)
		}

	/**
	 * 분기를 표현하는 문자열을 반환합니다.
	 */
	def getQuarterName(quarter: QuarterKind): String =
		_yearKind match {
			case YearKind.Calendar => TimeStrings.CalendarQuarterName(quarter)
			case YearKind.Fiscal => TimeStrings.FiscalQuarterName(quarter)
			case YearKind.School => TimeStrings.SchoolQuarterName(quarter)
			case YearKind.System => TimeStrings.SystemQuarterName(quarter)
			case _ => TimeStrings.CalendarQuarterName(quarter)
		}

	/**
	 * 특정년도의 분기를 표현하는 문자열을 반환합니다. (2013년 1사분기)
	 */
	def getQuarterOfYearName(year: Int, quarter: QuarterKind): String =
		_yearKind match {
			case YearKind.Calendar => TimeStrings.CalendarQuarterOfYearName(quarter, year)
			case YearKind.Fiscal => TimeStrings.FiscalQuarterOfYearName(quarter, year)
			case YearKind.School => TimeStrings.SchoolQuarterOfYearName(quarter, year)
			case YearKind.System => TimeStrings.SystemQuarterOfYearName(quarter, year)
			case _ => TimeStrings.CalendarQuarterOfYearName(quarter, year)
		}

	/**
	 * 월을 문자열로
	 */
	def getMonthName(monthOfYear: Int): String = MonthKind(monthOfYear).toString

	/**
	 * 년/월을 문자열로 표현합니다.
	 */
	def getMonthOfYearName(year: Int, monthOfYear: Int): String =
		TimeStrings.MonthOfYearName(getMonthName(monthOfYear), getYearName(year))

	/**
	 * 년도와 주차를 문자열로 표현합니다.
	 */
	def getWeekOfYearName(year: Int, weekOfYear: Int): String =
		TimeStrings.WeekOfYearName(weekOfYear, getYearName(year))

	/**
	 * 요일을 문자열로 표현합니다.
	 */
	def getDayName(dayOfWeek: DayOfWeek): String = dayOfWeek.toString


	/**
	 * 해당 일자의 주차를 구합니다. (주차계산 규칙에 따라 달라집니다)
	 */
	def getWeekOfYear(moment: DateTime): Int = {
		Times.getWeekOfYear(moment).weekOfYear
	}

	/**
	 * 해당 년도와 주차의 첫번째 일자를 반환합니다. (주차계산 규칙에 따라 달라집니다. 일요일|월요일 등)
	 */
	def getStartOfYearWeek(year: Int, weekOfYear: Int): DateTime =
		Times.getStartOfYearWeek(year, weekOfYear)


	def mapStart(moment: DateTime): DateTime =
		if (moment.compareTo(TimeSpec.MinPeriodTime) > 0) moment.plus(getStartOffset) else moment

	def mapEnd(moment: DateTime) =
		if (moment.compareTo(TimeSpec.MinPeriodTime) < 0) moment.minus(getEndOffset) else moment

	def unmapStart(moment: DateTime) =
		if (moment.compareTo(TimeSpec.MinPeriodTime) > 0) moment.minus(getStartOffset) else moment

	def unmapEnd(moment: DateTime) =
		if (moment.compareTo(TimeSpec.MinPeriodTime) < 0) moment.plus(getEndOffset) else moment

	override def hashCode(): Int =
		ScalaHash.compute(_locale, _startOffset, _endOffset, _yearBaseMonth, _weekOfYearRule)

	protected override def buildStringHelper(): ToStringHelper =
		super.buildStringHelper()
		.add("locale", _locale)
		.add("startOffset", _startOffset)
		.add("endOffset", _endOffset)
		.add("yearBaseMonth", _yearBaseMonth)
		.add("weekOfYearRule", _weekOfYearRule)
		.add("calendarWeekRule", _calendarWeekRule)
		.add("firstDayOfWeek", _firstDayOfWeek)
}

