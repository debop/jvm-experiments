package kr.kth.timeperiod.calendar

import kr.kth.commons.slf4j.Logging
import kr.kth.commons.{SortDirection, Guard}
import kr.kth.timeperiod.SeekDirection._
import kr.kth.timeperiod._
import kr.kth.timeperiod.timerange._
import scala.reflect.ClassTag


/**
 * kr.kth.timeperiod.calendar.CalendarVisitor
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 19.
 */
class CalendarVisitor[TFilter <: ICalendarVisitorFilter : ClassTag, TContext <: ICalendarVisitorContext : ClassTag]
(filter: TFilter, limits: ITimePeriod, seekDir: Option[SeekDirection], calendar: ITimeCalendar)
    extends Logging {

    Guard.shouldNotBeNull(filter, "filter")
    Guard.shouldNotBeNull(limits, "limits")
    private val _filter = filter
    private val _limits = limits
    private val _seekDir = seekDir.getOrElse(SeekDirection.Forward)
    private val _calendar = calendar


    def this(filter: TFilter, limits: ITimePeriod, calendar: ITimeCalendar) {
        this(filter, limits, None, calendar)
    }

    def this(filter: TFilter, limits: ITimePeriod, seekDir: Option[SeekDirection]) {
        this(filter, limits, seekDir, TimeCalendar.Default)
    }

    def this(filter: TFilter, limits: ITimePeriod) {
        this(filter, limits, TimeCalendar.Default)
    }

    def getFilter = _filter

    def getLimits = _limits

    def getSeekDir = _seekDir

    def getCalendar = _calendar


    protected def startPeriodVisit(context: TContext) {
        startPeriodVisit(getLimits, context)
    }

    protected def startPeriodVisit(period: ITimePeriod, context: TContext) {
        log.debug(s"기간에 대한 탐색을 시작합니다... period=[$period], context=[$context]")
        Guard.shouldNotBeNull(period, "peroid")

        if (period.isMoment)
            return

        onVisitStart()

        val calendar = if (getCalendar != null) getCalendar else TimeCalendar.Default
        var years = new YearRangeCollection(period.getStart.getYear,
            period.getEnd.getYear - period.getStart.getYear + 1,
            calendar)

        if (onVisitYears(years, context) && enterYears(years, context)) {
            val yearsToVisit: TimePeriodCollection = Times.toTimePeriodCollection(years.getYears)

            if (getSeekDir == SeekDirection.Backward)
                yearsToVisit.sortByEnd(SortDirection.ASC)

            @inline def filterYear(y: YearRange): Boolean =
                !(y.overlapsWith(period) == false ||
                    onVisitYear(y, context) == false ||
                    enterMonths(y, context) == false)

            for (year <- yearsToVisit.map(_.asInstanceOf[YearRange]).filter(y => filterYear(y))) {

                val monthsToVisit = Times.toTimePeriodCollection(year.getMonths)
                if (getSeekDir == SeekDirection.Backward)
                    monthsToVisit.sortByEnd(SortDirection.ASC)

                for (month <- monthsToVisit.map(_.asInstanceOf[MonthRange]) if {
                    !(month.overlapsWith(period) == false ||
                        onVisitMonth(month, context) == false ||
                        enterDays(month, context) == false)
                }) {

                    val daysToVisit = Times.toTimePeriodCollection(month.getDays)
                    if (getSeekDir == SeekDirection.Backward)
                        daysToVisit.sortByEnd(SortDirection.ASC)

                    for (day <- daysToVisit.map(_.asInstanceOf[DayRange]) if {
                        !(day.overlapsWith(period) == false ||
                            onVisitDay(day, context) == false ||
                            enterHours(day, context) == false)
                    }) {

                        val hoursToVisit = Times.toTimePeriodCollection(day.getHours)
                        if (getSeekDir == SeekDirection.Backward)
                            hoursToVisit.sortByEnd(SortDirection.ASC)

                        for (hour <- hoursToVisit.map(_.asInstanceOf[HourRange]) if {
                            !(hour.overlapsWith(period) == false ||
                                onVisitHour(hour, context) == false)
                        }) {
                            // 탐색은 if 필터링에서 합니다.
                        }
                    }
                }
            }
        }


        onVisitEnd()
        log.debug(s"기간에 대한 탐색을 완료했습니다!!! period=[$period], context=[$context]")
    }

    protected def startYearVisit(year: YearRange,
                                 context: TContext,
                                 visitDirection: Option[SeekDirection] = None): YearRange = {
        Guard.shouldNotBeNull(year, "year")
        val direction = visitDirection.getOrElse(getSeekDir)

        log.debug("년(Year) 단위로 탐색합니다. year=[{}], context=[{}], direction=[{}]",
            year, context, direction)

        var lastVisited: YearRange = null

        onVisitStart()

        val minStart = TimeSpec.MinPeriodTime
        val maxEnd = TimeSpec.MaxPeriodTime.plusYears(-1)
        val offset = if (direction == SeekDirection.Forward) 1 else -1
        var current = year

        while (current.getStart > minStart && current.getEnd < maxEnd) {
            if (onVisitYear(year, context) == false) {
                lastVisited = current
            } else {
                current = current.addYears(offset)
            }
        }

        onVisitEnd()
        log.debug(s"마지막 탐색 Year. lastVisited=$lastVisited")

        lastVisited
    }

    protected def startMonthVisit(month: MonthRange,
                                  context: TContext,
                                  visitDirection: Option[SeekDirection] = None): MonthRange = {
        Guard.shouldNotBeNull(month, "month")
        val direction = visitDirection.getOrElse(getSeekDir)
        log.debug(s"월(Month) 단위로 탐색합니다... month=$month, context=$context, direction=$direction")

        var lastVisited: MonthRange = null

        onVisitStart()

        val minStart = TimeSpec.MinPeriodTime
        val maxEnd = TimeSpec.MaxPeriodTime.plusYears(-1)
        val offset = if (direction == SeekDirection.Forward) 1 else -1
        var current = month

        while (current.getStart > minStart && current.getEnd < maxEnd) {
            if (onVisitMonth(current, context) == false) {
                lastVisited = current
            } else {
                current = current.addMonths(offset)
            }
        }

        onVisitEnd()
        log.debug(s"마지막 탐색 Month. lastVisited=$lastVisited")

        lastVisited
    }

    protected def startDayVisit(day: DayRange,
                                context: TContext,
                                visitDirection: Option[SeekDirection] = None): DayRange = {
        Guard.shouldNotBeNull(day, "day")
        val direction = visitDirection.getOrElse(getSeekDir)
        log.debug(s"일(day) 단위로 탐색합니다... day=$day, context=$context, direction=$direction")

        var lastVisited: DayRange = null

        onVisitStart()

        val minStart = TimeSpec.MinPeriodTime
        val maxEnd = TimeSpec.MaxPeriodTime.plusYears(-1)
        val offset = if (direction == SeekDirection.Forward) 1 else -1
        var current = day

        while (current.getStart > minStart && current.getEnd < maxEnd) {
            if (onVisitDay(current, context) == false) {
                lastVisited = current
            } else {
                current = current.addDays(offset)
            }
        }

        onVisitEnd()
        log.debug(s"마지막 탐색 Day. lastVisited=$lastVisited")

        lastVisited
    }

    protected def startHourVisit(hour: HourRange,
                                 context: TContext,
                                 visitDirection: Option[SeekDirection] = None): HourRange = {
        Guard.shouldNotBeNull(hour, "hour")
        val direction = visitDirection.getOrElse(getSeekDir)
        log.debug(s"시(hour) 단위로 탐색합니다... hour=$hour, context=$context, direction=$direction")

        var lastVisited: HourRange = null

        onVisitStart()

        val minStart = TimeSpec.MinPeriodTime
        val maxEnd = TimeSpec.MaxPeriodTime.plusYears(-1)
        val offset = if (direction == SeekDirection.Forward) 1 else -1
        var current = hour

        while (current.getStart > minStart && current.getEnd < maxEnd) {
            if (onVisitHour(current, context) == false) {
                lastVisited = current
            } else {
                current = current.addHours(offset)
            }
        }

        onVisitEnd()
        log.debug(s"마지막 탐색 Hour. lastVisited=$lastVisited")

        lastVisited
    }

    protected def onVisitStart() {
        log.debug("Calendar Visit를 시작합니다...")
    }

    protected def checkLimits(target: ITimePeriod): Boolean = getLimits.hasInside(target)

    protected def checkExcludePeriods(target: ITimePeriod): Boolean = {
        if (getFilter.getExcludePeriods.exists(x => true) == false) true
        else getFilter.getExcludePeriods.overlapPeriods(target).exists(x => true) == false
    }

    protected def enterYears(yearRanges: YearRangeCollection, context: TContext): Boolean = true

    protected def enterMonths(yearRange: YearRange, context: TContext): Boolean = true

    protected def enterDays(monthRange: MonthRange, context: TContext): Boolean = true

    protected def enterHours(dayRange: DayRange, context: TContext): Boolean = true

    protected def onVisitYears(years: YearRangeCollection, context: TContext): Boolean = true

    protected def onVisitYear(year: YearRange, context: TContext): Boolean = true

    protected def onVisitMonth(month: MonthRange, context: TContext): Boolean = true

    protected def onVisitDay(day: DayRange, context: TContext): Boolean = true

    protected def onVisitHour(hour: HourRange, context: TContext): Boolean = true

    protected def isMatchingYear(year: YearRange, context: TContext): Boolean =
        if (getFilter.getYears.size > 0 && getFilter.getYears.contains(year.getYearValue) == false) false
        else checkExcludePeriods(year)

    protected def isMatchingMonth(month: MonthRange, context: TContext): Boolean = {
        if (getFilter.getYears.size > 0 && getFilter.getYears.contains(month.getYear) == false) false
        else if (getFilter.getMonths.size > 0 && getFilter.getMonths.contains(month.getMonth) == false) false
        else checkExcludePeriods(month)
    }

    protected def isMatchingDay(day: DayRange, context: TContext): Boolean = {
        if (getFilter.getYears.size > 0 && getFilter.getYears.contains(day.getYear) == false) false
        else if (getFilter.getMonths.size > 0 && getFilter.getMonths.contains(day.getMonth) == false) false
        else if (getFilter.getDays.size > 0 && getFilter.getDays.contains(day.getDay) == false) false
        else checkExcludePeriods(day)
    }

    protected def isMatchingHour(hour: HourRange, context: TContext): Boolean = {
        if (getFilter.getYears.size > 0 && getFilter.getYears.contains(hour.getYear) == false) false
        else if (getFilter.getMonths.size > 0 && getFilter.getMonths.contains(hour.getMonth) == false) false
        else if (getFilter.getDays.size > 0 && getFilter.getDays.contains(hour.getDay) == false) false
        else if (getFilter.getHours.size > 0 && getFilter.getHours.contains(hour.getHour) == false) false
        else checkExcludePeriods(hour)
    }

    protected def onVisitEnd() {
        log.debug("Calendar Visit를 종료합니다.")
    }
}
