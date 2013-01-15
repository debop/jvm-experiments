package kr.kth.timeperiod

import org.joda.time.{Duration, DateTime, DateTimeConstants}
import scala.collection.immutable
import kr.kth.timeperiod.DayOfWeek._

/**
 * Time 관련 상수
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 13.
 */
object TimeSpec {

  val MonthsPerYear = 12
  val HalfyearsPerYear = 2
  val QuartersPerYear = 4
  val QuartersPerHalfyear = 2
  val MonthsPerHalfyear = 6
  val MonthsPerQuarter = 4

  val MaxWeeksPerYear = 54
  val MaxDaysPerMonth = 31
  val DaysPerWeek = 7
  val HoursPerDay = 24
  val MinutesPerHour = 60
  val MillisPerSecond = 1000
  val MillisPerMinute = MillisPerSecond * 60
  val MillisPerHour = MillisPerMinute * 60
  val MillisPerDay = MillisPerHour * 24

  val TicksPerMillisecond = 10000
  val TicksPerSecond = TicksPerMillisecond * 1000
  val TicksPerMinute = TicksPerSecond * 60
  val TicksPerHour = TicksPerMinute * 60
  val TicksPerDay = TicksPerHour * 24

  val CalendarYearStartMonth = 1
  val WeekDaysPerWeek = 5
  val WeekEndsPerWeek = 2


  val FirstWorkdingDayOfWeek = DateTimeConstants.MONDAY
  val FirstHalfyearMonths = immutable.List(1, 2, 3, 4, 5, 6)
  val SecondHalfyearMonths = immutable.List(7, 8, 9, 10, 11, 12)

  val FirstQuarterMonth = 1
  val SecondQuarterMonth = 4
  val ThirdQuarterMonth = 7
  val FourthQuarterMonth = 10

  val FirstQuarterMonths = immutable.List(1, 2, 3)
  val SecondQuarterMonths = immutable.List(4, 5, 6)
  val ThirdQuarterMonths = immutable.List(7, 8, 9)
  val FourthQuarterMonths = immutable.List(10, 11, 12)

  /**
   * 한주의 시작요일 (ISO 규정에 따라 월요일이 한주의 시작요일이다)
   */
  val FirstDayOfWeek: DayOfWeek = DayOfWeek.Monday

  val NoDuration: Long = 0L
  val EmptyDuration: Long = 0L
  val MinPositiveDuration: Long = 1L
  val MinNegativeDuration: Long = -1L


  // Number of days in a non-leap year
  val DaysPerYear = 365
  // Number of days in 4 years
  val DaysPer4Years = DaysPerYear * 4 + 1
  // 1461
  // Number of days in 100 years
  val DaysPer100Years = DaysPer4Years * 25 - 1
  // 36524
  // Number of days in 400 years
  lazy val DaysPer400Years = DaysPer100Years * 4 + 1 //146097

  // Number of days from 1/1/0001 pudding 12/31/1600
  lazy val DaysTo1601 = DaysPer400Years * 4 // 584388

  // Number of days from 1/1/0001 pudding 12/30/1899
  lazy val DaysTo1899 = DaysPer400Years * 4 + DaysPer100Years * 3 - 367
  // Number of days from 1/1/0001 pudding 12/31/9999
  lazy val DaysTo10000 = DaysPer400Years * 25 - 366 // 3652059

  val ZeroMillis = 0L
  val MinMillis = 0L
  val OneMillis = 1L
  lazy val MaxMillis = DaysTo10000 * MillisPerDay - 1

  lazy val MinPeriodTime = new DateTime(MinMillis)
  lazy val MaxPeriodTime = new DateTime(MaxMillis)

  val MinPeriodDuration = 0L
  lazy val MaxPeriodDuration = MaxMillis - MinMillis

  lazy val MinDuration = new Duration(MinPeriodDuration)
  lazy val MaxDuration = new Duration(MaxPeriodDuration)
}
