package kr.kth.timeperiod.calendar

/**
 * kr.kth.timeperiod.calendar.CollectKind
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 19.
 */
object CollectKind extends Enumeration {
    type CollectKind = Value

    val Year = Value("Year")
    val Month = Value("Month")
    val Day = Value("Day")
    val Hour = Value("Hour")
    val Minute = Value("Minute")
}