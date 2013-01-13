package kr.kth.timeperiod

import org.joda.time.DateTimeConstants

/**
 * 한 주의 요일을 표현합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 13.
 */
object DayOfWeek extends Enumeration {

  type DayOfWeek = Value

  // NOTE: withName, apply 메소드를 사용하세요

  val Monday = Value(DateTimeConstants.MONDAY, "Monday")
  val Tuesday = Value(DateTimeConstants.TUESDAY, "Tuesday")
  val Wednesday = Value(DateTimeConstants.WEDNESDAY, "Wednesday")
  val Thursday = Value(DateTimeConstants.THURSDAY, "Thursday")
  val Friday = Value(DateTimeConstants.FRIDAY, "Friday")
  val Saturday = Value(DateTimeConstants.SATURDAY, "Saturday")
  val Sunday = Value(DateTimeConstants.SUNDAY, "Sunday")
}
