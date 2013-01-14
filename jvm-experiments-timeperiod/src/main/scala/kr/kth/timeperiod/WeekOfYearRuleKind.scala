package kr.kth.timeperiod

/**
 * kr.kth.timeperiod.WeekOfYearRuleKind
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 14
 */
object WeekOfYearRuleKind extends Enumeration {

  type WeekOfYearRuleKind = Value

  val Calendar = Value(0, "Calendar")

  val Iso8601 = Value(1, "Iso8601")

}
