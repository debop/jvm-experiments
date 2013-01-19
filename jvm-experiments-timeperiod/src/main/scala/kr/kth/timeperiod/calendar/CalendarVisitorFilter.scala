package kr.kth.timeperiod.calendar

import com.google.common.base.Objects
import java.util.concurrent.locks.ReentrantLock
import kr.kth.commons.tools.StringTool
import kr.kth.timeperiod.DayOfWeek._
import kr.kth.timeperiod.{DayOfWeek, TimePeriodCollection}

/**
 * kr.kth.timeperiod.calendar.CalendarVisitorFilter
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 19.
 */
class CalendarVisitorFilter extends ICalendarVisitorFilter {

    private val _excludePeriods = new TimePeriodCollection()
    private val _years = new collection.mutable.HashSet[Int]()
    private val _months = new collection.mutable.HashSet[Int]()
    private val _days = new collection.mutable.HashSet[Int]()
    private val _weekDays = new collection.mutable.HashSet[DayOfWeek]()
    private val _hours = new collection.mutable.HashSet[Int]()
    private val _minutes = new collection.mutable.HashSet[Int]()

    private val _lock = new ReentrantLock()

    def getExcludePeriods = _excludePeriods

    def getYears = _years

    def getMonths = _months

    def getDays = _days

    def getWeekDays = _weekDays

    def getHours = _hours

    def getMinutes = _minutes

    def addWorkingWeekDays() {
        addWeekDays(DayOfWeek.Monday,
                    DayOfWeek.Tuesday,
                    DayOfWeek.Wednesday,
                    DayOfWeek.Thursday,
                    DayOfWeek.Friday)
    }

    def addWeekendWeekDays() {
        addWeekDays(DayOfWeek.Saturday,
                    DayOfWeek.Sunday)
    }

    def addWeekDays(dayOfWeeks: DayOfWeek*) {
        if (dayOfWeeks != null)
            dayOfWeeks.foreach(dow => _weekDays.add(dow))
    }

    def clear() {
        _lock.lock()
        try {
            _years.clear()
            _months.clear()
            _days.clear()
            _weekDays.clear()
            _hours.clear()
            _minutes.clear()
        } finally {
            _lock.unlock()
        }
    }

    override def toString: String = {
        Objects.toStringHelper(this)
        .add("years", StringTool.listToString(_years))
        .add("months", StringTool.listToString(_months))
        .add("days", StringTool.listToString(_days))
        .add("weekDays", StringTool.listToString(_weekDays))
        .add("hours", StringTool.listToString(_hours))
        .add("minutes", StringTool.listToString(_minutes))
        .add("exclude Periods", StringTool.listToString(_excludePeriods))
        .toString()
    }
}
