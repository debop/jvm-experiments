package kr.kth.timeperiod.calendar

import kr.kth.timeperiod.SeekDirection._
import kr.kth.timeperiod.timerange.YearRangeCollection
import kr.kth.timeperiod.{TimePeriodCollection, TimeCalendar, ITimeCalendar, ITimePeriod}

/**
 * 기간에 대한 컬렉터입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 19.
 */
class CalendarPeriodCollector(filter: CalendarPeriodCollectorFilter,
                              limits: ITimePeriod,
                              seekDirection: Option[SeekDirection],
                              calendar: ITimeCalendar)
    extends CalendarVisitor[CalendarPeriodCollectorFilter, CalendarPeriodCollectorContext](filter, limits, seekDirection, calendar) {

    private val _periods = new TimePeriodCollection()

    def this(filter: CalendarPeriodCollectorFilter, limits: ITimePeriod, calendar: ITimeCalendar) {
        this(filter, limits, None, calendar)
    }

    def this(filter: CalendarPeriodCollectorFilter, limits: ITimePeriod, seekDir: Option[SeekDirection]) {
        this(filter, limits, seekDir, TimeCalendar.Default)
    }

    def this(filter: CalendarPeriodCollectorFilter, limits: ITimePeriod) {
        this(filter, limits, TimeCalendar.Default)
    }

    def getPeriods = _periods

    def collectYears() {
        startPeriodVisit(new CalendarPeriodCollectorContext(CollectKind.Year))
    }

    def collectMonths() {
        startPeriodVisit(new CalendarPeriodCollectorContext(CollectKind.Month))
    }

    def collectDays() {
        startPeriodVisit(new CalendarPeriodCollectorContext(CollectKind.Day))
    }

    def collectHOurs() {
        startPeriodVisit(new CalendarPeriodCollectorContext(CollectKind.Hour))
    }

    protected override def enterYears(yearRangeCollection: YearRangeCollection, context: CalendarPeriodCollectorContext) = {
        true
    }
}
