package kr.kth.timeperiod

/**
 * 기간 종류 {@link java.util.concurrent.TimeUnit} 과 유사
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 13.
 */
object PeriodKind extends Enumeration {
  type PeriodKind = Value
  /**
   * 알 수 없음
   */
  val Unknown = Value("Unknown")
  /**
   * 년 (Year)
   */
  val Year = Value("Year")
  /**
   * 반기 (Half Year)
   */
  val Halfyear = Value("Halfyear")
  /**
   * 분기 (Quarter)
   */
  val Quarter = Value("Quarter")
  /**
   * 월 (Month)
   */
  val Month = Value("Month")
  /**
   * 주 (Week)
   */
  val Week = Value("Week")
  /**
   * 일 (Day)
   */
  val Day = Value("Day")
  /**
   * 시간 (Hour)
   */
  val Hour = Value("Hour")
  /**
   * 분 (Minute)
   */
  val Minute = Value("Minute")
  /**
   * 초 (Second)
   */
  val Second = Value("Second")
  /**
   * 밀리초 (Millisecond)
   */
  val Millisecond = Value("Millisecond")
  /**
   * 나노초 (Nanosecond)
   */
  val Nanosecond = Value("Nanosecond")
}
