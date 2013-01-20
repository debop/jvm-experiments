package kr.kth.timeperiod.calendar

import kr.kth.timeperiod._
import scala.collection.mutable.ArrayBuffer
import scala.util.Try

/**
 * kr.kth.timeperiod.calendar.CalendarPeriodCollectorFilter
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 19.
 */
class CalendarPeriodCollectorFilter extends CalendarVisitorFilter with ICalendarPeriodCollectorFilter {

    private val _collectingMonths = new ArrayBuffer[MonthRangeInYear]()
    private val _collectingDays = new ArrayBuffer[DayRangeInMonth]()
    private val _collectingHours = new ArrayBuffer[HourRangeInDay]()
    private val _collectingDayHours = new ArrayBuffer[DayHourRange]()

    override def getCollectingMonths = _collectingMonths

    override def getCollectingDays = _collectingDays

    def getCollectingHours = _collectingHours

    def getCollectingDayHours = _collectingDayHours

    override def clear {
        this.synchronized {
            Try {
                super.clear()
                _collectingMonths.clear()
                _collectingDays.clear()
                _collectingHours.clear()
                _collectingDayHours.clear()
            }
        }
    }
}
