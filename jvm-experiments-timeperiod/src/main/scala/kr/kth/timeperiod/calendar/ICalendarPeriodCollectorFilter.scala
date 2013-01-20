package kr.kth.timeperiod.calendar

import kr.kth.timeperiod.{DayRangeInMonth, MonthRangeInYear}
import scala.collection.mutable

/**
 * kr.kth.timeperiod.calendar.ICalendarPeriodCollectorFilter
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 19.
 */
trait ICalendarPeriodCollectorFilter extends ICalendarVisitorFilter {

    def getCollectingMonths: mutable.Buffer[MonthRangeInYear]

    def getCollectingDays: mutable.Buffer[DayRangeInMonth]
}
