package kr.kth.timeperiod

import com.google.common.base.Objects.ToStringHelper
import java.util.Locale
import kr.kth.commons.slf4j.Logging
import kr.kth.commons.tools.ScalaHash
import lombok.extern.slf4j.Slf4j
import org.joda.time.{Duration, DateTime}
import kr.kth.commons.{ValueObjectBase, Guard}

/**
 * 문화권에 따른 날짜 표현, 날짜 계산 등을 제공하는 Calendar 입니다. (ISO 8601, Korean 등)
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 29.
 */
@Slf4j
@SerialVersionUID(4848502849274L)
class TimeCalendar(config: TimeCalendarConfig) extends ValueObjectBase with ITimeCalendar with Logging {
  {
    // 기본 생성자는 클래스 내의 모든 문장을 실행시킵니다.
    //

    val cfg = if (config != null) config else TimeCalendarConfig.Default

    Guard.shouldNotBeNegativeNumber(cfg.getStartOffset.getMillis, "startOffSet")
    Guard.shouldNotBeNegativeNumber(cfg.getEndOffset.getMillis, "endOffset")

    this.locale = cfg.getLocale
    this.yearKind = cfg.getYearKind
    this.startOffset = cfg.getStartOffset
    this.endOffset = cfg.getEndOffset
    this.yearBaseMonth = cfg.getYearBaseMonth
    this.weekOfYearRule = cfg.getWeekOfYearRule
    this.calendarWeekRule = cfg.getCalendarWeekRule
    this.firstDayOfWeek = cfg.getFirstDayOfWeek
  }

  private var locale: Locale = _
  private var yearKind: YearKind = _
  private var startOffset: Duration = _
  private var endOffset: Duration = _
  private var yearBaseMonth: Int = TimeSpec.CalendarYearStartMonth
  private var weekOfYearRule: WeekOfYearRuleKind = _
  private var calendarWeekRule: CalendarWeekRule = _
  private var firstDayOfWeek: DayOfWeek = _


  def getLocale: Locale = locale

  def getYearKind: YearKind = yearKind

  def getStartOffset: Duration = startOffset

  def getEndOffset: Duration = endOffset

  def getBaseMonthOfYear: Int = yearBaseMonth

  def getWeekOfYearRule: WeekOfYearRuleKind = weekOfYearRule

  def getCalendarWeekRule: CalendarWeekRule = calendarWeekRule

  def getFirstDayOfWeek: DayOfWeek = firstDayOfWeek

  def getYear(time: DateTime) = time.getYear

  def getMonth(time: DateTime) = time.getMonthOfYear

  def getDayOfMonth(time: DateTime) = time.getDayOfMonth

  def getDayOfWeek(time: DateTime) = time.getDayOfWeek

  def getHour(time: DateTime) = time.getHourOfDay

  def getMinute(time: DateTime) = time.getMinuteOfHour

  def getDaysInMonth(year: Int, month: Int) = Times.getDaysInMonth(year, month)

  def getYearName(year: Int) =
    yearKind match {
      case YearKind.CalendarYear => TimeStrings.CalendarYearName(year)
      case YearKind.FiscalYear => TimeStrings.FiscalYearName(year)
      case YearKind.SchoolYear => TimeStrings.SchoolYearName(year)
      case _ => TimeStrings.SystemYearName(year)
    }

  def getHalfYearName(halfyear: HalfYearKind) =
    yearKind match {
      case YearKind.CalendarYear => TimeStrings.CalendarHalfyearName(halfyear)
      case YearKind.FiscalYear => TimeStrings.FiscalHalfyearName(halfyear)
      case YearKind.SchoolYear => TimeStrings.SchoolHalfyearName(halfyear)
      case _ => TimeStrings.SystemHalfyearName(halfyear)
    }

  def getHalfYearOfYearName(year: Int, halfyear: HalfYearKind) =
    yearKind match {
      case YearKind.CalendarYear => TimeStrings.CalendarHalfyearOfYearName(halfyear, year)
      case YearKind.FiscalYear => TimeStrings.FiscalHalfyearOfYearName(halfyear, year)
      case YearKind.SchoolYear => TimeStrings.SchoolHalfyearOfYearName(halfyear, year)
      case _ => TimeStrings.SystemHalfyearOfYearName(halfyear, year)
    }

  def getQuarterName(quarter: QuarterKind) =
    yearKind match {
      case YearKind.CalendarYear => TimeStrings.CalendarQuarterName(quarter)
      case YearKind.FiscalYear => TimeStrings.FiscalQuarterName(quarter)
      case YearKind.SchoolYear => TimeStrings.SchoolQuarterName(quarter)
      case _ => TimeStrings.SystemQuarterName(quarter)
    }

  def getQuarterOfYearName(year: Int, quarter: QuarterKind) =
    yearKind match {
      case YearKind.CalendarYear => TimeStrings.CalendarQuarterOfYearName(quarter, year)
      case YearKind.FiscalYear => TimeStrings.FiscalQuarterOfYearName(quarter, year)
      case YearKind.SchoolYear => TimeStrings.SchoolQuarterOfYearName(quarter, year)
      case _ => TimeStrings.SystemQuarterOfYearName(quarter, year)
    }

  def getMonthName(month: Int) =
    new DateTime().withMonthOfYear(month).monthOfYear().getAsText(locale)

  def getMonthOfYearName(year: Int, month: Int) =
    TimeStrings.MonthOfYearName(getMonthName(month), getYearName(year))

  def getWeekOfYearName(year: Int, weekOfYear: Int) = TimeStrings.WeekOfYearName(weekOfYear, getYearName(year))

  def getDayName(dayOfWeek: Int) = DayOfWeek.valueOf(dayOfWeek).toString

  def getWeekOfYear(time: DateTime) = time.getWeekOfWeekyear

  def getStartOfYearWeek(year: Int, weekOfYear: Int) = Times.getStartOfYearWeek(year, weekOfYear)

  def mapStart(moment: DateTime): DateTime =
    if (moment.compareTo(TimeSpec.MinPeriodTime) > 0) moment.plus(getStartOffset) else moment

  def mapEnd(moment: DateTime) =
    if (moment.compareTo(TimeSpec.MinPeriodTime) < 0) moment.minus(getEndOffset) else moment

  def unmapStart(moment: DateTime) =
    if (moment.compareTo(TimeSpec.MinPeriodTime) > 0) moment.minus(getStartOffset) else moment

  def unmapEnd(moment: DateTime) =
    if (moment.compareTo(TimeSpec.MinPeriodTime) < 0) moment.plus(getEndOffset) else moment

  override def hashCode(): Int = ScalaHash.compute(locale, startOffset, endOffset, yearBaseMonth, weekOfYearRule)

  protected override def buildStringHelper(): ToStringHelper =
    super.buildStringHelper()
      .add("locale", locale)
      .add("startOffset", startOffset)
      .add("endOffset", endOffset)
      .add("yearBaseMonth", yearBaseMonth)
      .add("weekOfYearRule", weekOfYearRule)
      .add("calendarWeekRule", calendarWeekRule)
      .add("firstDayOfWeek", firstDayOfWeek)
}


object TimeCalendar extends Logging {

  lazy val DefaultStartOffset = new Duration(TimeSpec.NoDuration)
  lazy val DufaultEndOffset = new Duration(TimeSpec.MinPositiveDuration)

  lazy val Default = apply

  def apply = new TimeCalendar(TimeCalendarConfig.Default)

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

  def apply(startOffset: Duration, endOffset: Duration, yearBaseMonth: Int) = {
    val cfg = TimeCalendarConfig.Default

    cfg.setStartOffset(startOffset)
    cfg.setEndOffset(endOffset)
    cfg.setYearBaseMonth(yearBaseMonth)

    new TimeCalendar(cfg)
  }

  def apply(locale: Locale, startOffset: Duration, endOffset: Duration, yearBaseMonth: Int) = {
    val cfg = TimeCalendarConfig.Default

    cfg.setLocale(locale)
    if (startOffset != null)
      cfg.setStartOffset(startOffset)
    if (endOffset != null)
      cfg.setEndOffset(endOffset)
    cfg.setYearBaseMonth(yearBaseMonth)

    new TimeCalendar(cfg)
  }

  def apply(locale: Locale, yearBaseMonth: Int, weekOfYearRule: WeekOfYearRuleKind) = {
    val cfg = new TimeCalendarConfig(locale, weekOfYearRule)
    cfg.setYearBaseMonth(yearBaseMonth)

    new TimeCalendar(cfg)
  }

  def apply(locale: Locale, yearBaseMonth: Int, weekOfYearRule: WeekOfYearRuleKind, startOffset: Duration, endOffset: Duration) = {
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
