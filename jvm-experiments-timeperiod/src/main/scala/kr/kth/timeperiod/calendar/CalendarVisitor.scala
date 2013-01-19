package kr.kth.timeperiod.calendar

import kr.kth.commons.Guard
import kr.kth.commons.slf4j.Logging
import kr.kth.timeperiod.SeekDirection._
import kr.kth.timeperiod._
import kr.kth.timeperiod.timerange.YearRange
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

        // TODO: 구현 필요
    }

    protected def startYearVisit(yearRange: YearRange,
                                 context: TContext,
                                 visitDirection: Option[SeekDirection] = None) {
        Guard.shouldNotBeNull(yearRange, "yearRange")
        val direction = visitDirection.getOrElse(getSeekDir)

        log.debug("년(Year) 단위로 탐색합니다. year=[{}], context=[{}], direction=[{}]",
                  yearRange, context, direction)

        var lastVisited: YearRange = null
    }
}
