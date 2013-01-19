package kr.kth.timeperiod.calendar

import kr.kth.timeperiod.{DayRangeInMonth, MonthRangeInYear}

/**
 * kr.kth.timeperiod.calendar.ICalendarPeriodCollectionFilter
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 19.
 */
trait ICalendarPeriodCollectionFilter extends ICalendarVisitorFilter {

    def getCollectingMonths: List[MonthRangeInYear]

    def getCollectingDays: List[DayRangeInMonth]
}
