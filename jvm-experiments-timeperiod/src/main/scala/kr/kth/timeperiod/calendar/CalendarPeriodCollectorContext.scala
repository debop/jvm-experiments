package kr.kth.timeperiod.calendar

import CollectKind._

/**
 * kr.kth.timeperiod.calendar.CalendarPeriodCollectorContext
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 19.
 */
class CalendarPeriodCollectorContext(collectKind: CollectKind)
    extends ICalendarVisitorContext with Serializable {

    def this() {
        this(CollectKind.Year)
    }

    private var _collectKind: CollectKind = collectKind

    def getScope: CollectKind = _collectKind

    def setScope(nv: CollectKind) { _collectKind = nv }
}
