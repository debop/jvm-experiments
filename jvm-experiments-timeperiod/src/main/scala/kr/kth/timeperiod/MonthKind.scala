package kr.kth.timeperiod

/**
 * kr.kth.timeperiod.MonthKind
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 13.
 */
object MonthKind extends Enumeration {
    type MonthKind = Value

    /**
     * 1월
     */
    val January = Value(1, "January")

    /**
     * 2월
     */
    val Feburary = Value(2, "Feburary")

    /**
     * 3월
     */
    val March = Value(3, "March")

    /**
     * 4월
     */
    val April = Value(4, "April")

    /**
     * 5월
     */
    val May = Value(5, "May")

    /**
     * 6월
     */
    val June = Value(6, "June")

    /**
     * 7월
     */
    val July = Value(7, "July")

    /**
     * 8월
     */
    val August = Value(8, "August")

    /**
     * 9월
     */
    val September = Value(9, "September")

    /**
     * 10월
     */
    val October = Value(10, "October")

    /**
     * 11월
     */
    val November = Value(11, "November")

    /**
     * 12월
     */
    val December = Value(12, "December")
}
