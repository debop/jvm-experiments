package kr.kth.timeperiod

import java.util.Locale
import org.joda.time.{DateTime, Duration}

/**
 * kr.kth.timeperiod.ITimeCalendar
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 13.
 */
trait ITimeCalendar extends ITimePeriodMapper {

  import YearKind._, QuarterKind._, DayOfWeek._

  def getLocale(): Locale

  def getYearKind(): YearKind

  /**
   * 시작일 오프셋
   */
  def getStartOffset(): Duration

  /**
   * 종료일 오프셋
   */
  def getEndOffset(): Duration

  /**
   * 년의 기준 월
   */
  def getBaseMonthOfYear: Int

  def getWeekOfYearRule: WeekOfYearRuleKind

  /**
   * 한주의 시작 요일 (1: 월요일 ... 6:토요일, 7:일요일)
   */
  def getFirstDayOfWeek: DayOfWeek

  /**
   * 일자의 년도를 구합니다.
   */
  def getYear(moment: DateTime): Int

  /**
   * 월을 구합니다.
   */
  // TODO: getMonthOfYear 로 변경
  def getMonth(moment: DateTime): Int

  /**
   * 해당 월의 일를 구합니다.
   */
  def getDayOfMonth(moment: DateTime): Int

  /**
   * 요일을 구합니다.
   */
  def getDayOfWeek(moment: DateTime): Int

  /**
   * 시간을 구합니다.
   */
  // TODO: getHourOfDay 로 변경
  def getHour(moment: DateTime): Int

  /**
   * 분을 구합니다.
   */
  // TODO: getMinuteOfHour 로 변경
  def getMinute(moment: DateTime): Int

  /**
   * 해당 년/월의 일자 수 (28~31)
   */
  def getDaysInMonth(year: Int, monthOfYear: Int): Int

  /**
   * 년도 이름
   */
  def getYearName(year: Int): String

  /**
   * 반기를 표현하는 문자열을 반환합니다.
   */
  def getHalfYearName(halfyear: HalfYearKind): String

  /**
   * 지정한 년도와 반기를 표현하는 문자열을 반환합니다.
   */
  def getHalfYearOfYearName(year: Int, halfyear: HalfYearKind): String

  /**
   * 분기를 표현하는 문자열을 반환합니다.
   */
  def getQuarterName(quarter: QuarterKind): String

  /**
   * 특정년도의 분기를 표현하는 문자열을 반환합니다. (2013년 1사분기)
   */
  def getQuarterOfYearName(year: Int, quarter: QuarterKind): String

  /**
   * 월을 문자열로
   */
  def getMonthName(monthOfYear: Int): String

  /**
   * 년/월을 문자열로 표현합니다.
   */
  def getMonthOfYearName(year: Int, monthOfYear: Int): String

  /**
   * 년도와 주차를 문자열로 표현합니다.
   */
  def getWeekOfYearName(year: Int, weekOfYear: Int): String

  /**
   * 요일을 문자열로 표현합니다.
   */
  def getDayName(dayOfWeek: Int): String

  /**
   * 해당 일자의 주차를 구합니다. (주차계산 규칙에 따라 달라집니다)
   */
  def getWeekOfYear(moment: DateTime): Int

  /**
   * 해당 년도와 주차의 첫번째 일자를 반환합니다. (주차계산 규칙에 따라 달라집니다. 일요일|월요일 등)
   */
  def getStartOfYearWeek(year: Int, weekOfYear: Int): DateTime
}
