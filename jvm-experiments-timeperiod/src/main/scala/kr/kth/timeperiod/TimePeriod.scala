package kr.kth.timeperiod

import kr.kth.commons.ValueObjectBase
import org.joda.time.DateTime

/**
 * kr.kth.timeperiod.TimePeriod
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 14
 */
@SerialVersionUID(7093665366996191218L)
abstract class TimePeriod(start: DateTime, end: DateTime, readonly: Boolean)
    extends ValueObjectBase with ITimePeriod {

    _start = if (start != null) start else TimeSpec.MinPeriodTime
    _end = if (end != null) end else TimeSpec.MaxPeriodTime
    _readonly = readonly

    def this(start: DateTime, duration: Long, readonly: Boolean) {
        this(start, null, readonly)
        super.setDuration(duration)
    }

    def this(source: ITimePeriod, readonly: Option[Boolean] = None) {
        this(source.getStart, source.getEnd, readonly.getOrElse(source.isReadonly))
    }

    def this(moment: DateTime, readonly: Boolean) {
        this(moment, moment, readonly)
    }

    def this(readonly: Boolean) {
        this(null, null, readonly)
    }

    def this() {
        this(null, null, false)
    }
}
