package kr.kth.timeperiod

import java.util.Locale
import QuarterKind._


/**
 * 날짜를 형식을 정의합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 29.
 */
object TimeStrings {

  def instance = TimeStrings.this

  val SystemYearNameFormat: String = "%d"
  val CalendarYearNameFormat: String = "CY%d"
  val FiscalYearNameFormat: String = "FY%d"
  val SchoolYearNameFormat: String = "SY%d"

  def SystemYearName(year: Int) = format(SystemYearNameFormat, year)

  def CalendarYearName(year: Int) = format(CalendarYearNameFormat, year)

  def FiscalYearName(year: Int) = format(FiscalYearNameFormat, year)

  def SchoolYearName(year: Int) = format(SchoolYearNameFormat, year)


  val SystemHalfyearNameFormat = "HY%d"
  val CalendarHalfyearNameFormat = "CHY%d"
  val FiscalHalfyearNameFormat: String = "FHY%d"
  val SchoolHalfyearNameFormat: String = "SHY%d"
  val SystemHalfyearOfYearNameFormat = "HY%d %d"
  val CalendarHalfyearOfYearNameFormat = "CHY%d %d"
  val FiscalHalfyearOfYearNameFormat: String = "FHY%d %d"
  val SchoolHalfyearOfYearNameFormat: String = "SHY%d %d"

  def SystemHalfyearName(halfyear: HalfYearKind) = format(SystemHalfyearNameFormat, halfyear.toInt)

  def CalendarHalfyearName(halfyear: HalfYearKind) = format(CalendarHalfyearNameFormat, halfyear.toInt)

  def FiscalHalfyearName(halfyear: HalfYearKind) = format(FiscalHalfyearNameFormat, halfyear.toInt)

  def SchoolHalfyearName(halfyear: HalfYearKind) = format(SchoolHalfyearNameFormat, halfyear.toInt)

  def SystemHalfyearOfYearName(halfyear: HalfYearKind, year: Int) =
    format(SystemHalfyearOfYearNameFormat, halfyear.toInt, year)

  def CalendarHalfyearOfYearName(halfyear: HalfYearKind, year: Int) =
    format(CalendarHalfyearOfYearNameFormat, halfyear.toInt, year)

  def FiscalHalfyearOfYearName(halfyear: HalfYearKind, year: Int) =
    format(FiscalHalfyearOfYearNameFormat, halfyear.toInt, year)

  def SchoolHalfyearOfYearName(halfyear: HalfYearKind, year: Int) =
    format(SchoolHalfyearOfYearNameFormat, halfyear.toInt, year)

  val SystemQuarterNameFormat = "Q%d"
  val CalendarQuarterNameFormat = "CQ%d"
  val FiscalQuarterNameFormat: String = "FQ%d"
  val SchoolQuarterNameFormat: String = "SQ%d"
  val SystemQuarterOfYearNameFormat = "Q%d %d"
  val CalendarQuarterOfYearNameFormat = "CQ%d %d"
  val FiscalQuarterOfYearNameFormat: String = "FQ%d %d"
  val SchoolQuarterOfYearNameFormat: String = "SQ%d %d"

  def SystemQuarterName(quarter: QuarterKind) =
    format(SystemQuarterNameFormat, quarter.id)

  def CalendarQuarterName(quater: QuarterKind) =
    format(CalendarQuarterNameFormat, quater.id)

  def FiscalQuarterName(quater: QuarterKind) = format(FiscalQuarterNameFormat, quater.id)

  def SchoolQuarterName(quater: QuarterKind) = format(SchoolQuarterNameFormat, quater.id)

  def SystemQuarterOfYearName(quater: QuarterKind, year: Int) =
    format(SystemQuarterOfYearNameFormat, quater.id, year)

  def CalendarQuarterOfYearName(quater: QuarterKind, year: Int) =
    format(CalendarQuarterOfYearNameFormat, quater.id, year)

  def FiscalQuarterOfYearName(quater: QuarterKind, year: Int) =
    format(FiscalQuarterOfYearNameFormat, quater.id, year)

  def SchoolQuarterOfYearName(quater: QuarterKind, year: Int) =
    format(SchoolQuarterOfYearNameFormat, quater.id, year)

  val MonthOfYearNameFormat = "%s %s"

  def MonthOfYearName(monthName: String, yearName: String) =
    format(MonthOfYearNameFormat, monthName, yearName)

  val WeekOfYearNameFormat = "w/c %d %s"

  def WeekOfYearName(weekOfYear: Int, yearName: String) =
    format(WeekOfYearNameFormat, weekOfYear, yearName)

  val TimeSpanYears = "Years"
  val TimeSpanYear = "Year"
  val TimeSpanMonths = "Months"
  val TimeSpanMonth = "Month"
  val TimeSpanWeeks = "Weeks"
  val TimeSpanWeek = "Week"
  val TimeSpanDays = "Days"
  val TimeSpanDay = "Day"
  val TimeSpanHours = "Hours"
  val TimeSpanHour = "Hour"
  val TimeSpanMinutes = "Minutes"
  val TimeSpanMinute = "Minute"
  val TimeSpanSeconds = "Seconds"
  val TimeSpanSecond = "Second"

  private def format(fmt: String, args: Any*): String = fmt.formatLocal(Locale.ROOT, args)
}
