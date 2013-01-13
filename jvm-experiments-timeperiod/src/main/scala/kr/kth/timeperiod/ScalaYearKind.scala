package kr.kth.timeperiod

/**
 * kr.kth.timeperiod.ScalaYearKind
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 13.
 */
object ScalaYearKind extends Enumeration {
  type ScalaYearKind = Value

  /**
   * 설치된 OS의 문화권에 해당하는 Year
   */
  val System = Value("System")
  /**
   * 현 ThreadContext에 지정된 {@link java.util.Locale}의 Calendar의 Year
   */
  val Calendar = Value("Calendar")
  /**
   * 회계 년 (2월 시작)
   */
  val Fiscal = Value("Fiscal")
  /**
   * 교육 년 (3월 시작, 9월 시작)
   */
  val School = Value("School")
  /**
   * 사용자 정의 Year
   */
  val Custom = Value("Custom")
}
