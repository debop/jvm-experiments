package kr.kth.timeperiod.calendar

import kr.kth.timeperiod.DayOfWeek._
import kr.kth.timeperiod.ITimePeriodCollection

/**
 * kr.kth.timeperiod.calendar.ICalendarVisitorFilter
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 19.
 */
trait ICalendarVisitorFilter {

    def getExcludePeriods: ITimePeriodCollection

    def getYears: collection.Set[Int]

    def getMonths: collection.Set[Int]

    def getDays: collection.Set[Int]

    def getWeekDays: collection.Set[DayOfWeek]

    def getHours: collection.Set[Int]

    def getMinutes: collection.Set[Int]

    def addWorkingWeekDays()

    def addWeekendWeekDays()

    def addWeekDays(dayOfWeeks: DayOfWeek*)

    def clear()
}
