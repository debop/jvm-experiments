package kr.kth.timeperiod

import kr.kth.commons.Guard
import kr.kth.commons.slf4j.Logging
import org.joda.time.DateTime

/**
 * 시각 구간 ( 시작 ~ 완료 시각 )을 나타냅니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 14
 */
class TimeRange(start: DateTime, end: DateTime, readonly: Boolean)
    extends TimePeriod(start, end, readonly) with ITimeRange {

    def this(source: ITimePeriod, readonly: Boolean) {
        this(source.getStart, source.getEnd, readonly)
    }

    def this(source: ITimePeriod) {
        this(source, false)
    }

    def this(moment: DateTime, readonly: Boolean) {
        this(moment, moment, readonly)
    }

    def this(start: DateTime, end: DateTime) {
        this(start, end, false)
    }

    def this() {
        this(TimeSpec.MinPeriodTime, TimeSpec.MaxPeriodTime, false)
    }

}

/**
 * TimeRange Object
 */
object TimeRange extends Logging {

    lazy val Anytime: TimeRange = apply(readonly = true)

    def apply(start: DateTime = TimeSpec.MinPeriodTime,
              end: DateTime = TimeSpec.MaxPeriodTime,
              readonly: Boolean = false): TimeRange =
        new TimeRange(start, end, readonly)

    def apply(source: ITimePeriod): TimeRange = {
        Guard.shouldNotBeNull(source, "source")
        if (source == Anytime) Anytime
        else apply(source.getStart, source.getEnd, source.isReadonly)
    }

    def apply(source: ITimePeriod, readonly: Boolean): TimeRange = {
        Guard.shouldNotBeNull(source, "source")
        apply(source.getStart, source.getEnd, readonly)
    }

    def unapply(range: ITimeRange) = (range.getStart, range.getEnd, range.isReadonly)
}

/**
 * ITimeRange trait
 */
trait ITimeRange extends ITimePeriod {

    /**
     * 시작시각을 설정합니다.
     */
    def setStart(newStart: DateTime) {
        assertMutable()
        _start = newStart
    }

    /**
     * 완료시각을 설정합니다.
     */
    def setEnd(newEnd: DateTime) {
        assertMutable()
        _end = newEnd
    }

    /**
     * 시작시각을 지정된 시각으로 변경합니다. 기존 시작시각보다 작은 값 (과거) 이여야 합니다.
     */
    def expandStartTo(moment: DateTime) {
        assertMutable()

        if (_start.compareTo(moment) > 0) {
            log.debug("expand start[{}] to [{}]", _start, moment)
            _start = moment
        }
    }

    /**
     * 완료시각을 지정된 시각으로 변경합니다. 기존 완료시각보다 큰 값 (미래) 이어야 합니다.
     */
    def expandEndTo(moment: DateTime) {
        assertMutable()

        if (_end.compareTo(moment) < 0) {
            log.debug("expand end[{}] to [{}]", _end, moment)
            _end = moment
        }
    }

    /**
     * 시작시각과 완료시각을 지정된 시각으로 설정합니다.
     */
    def expandTo(moment: DateTime) {
        assertMutable()
        expandStartTo(moment)
        expandEndTo(moment)
    }

    /**
     * 시작시각과 완료시각을 지정된 기간 정보를 기준으로 변경합니다.
     */
    def expandTo(period: ITimePeriod) {
        assertMutable()
        Guard.shouldNotBeNull(period, "period")
        if (period.hasStart) expandStartTo(period.getStart)
        if (period.hasEnd) expandEndTo(period.getEnd)
    }

    /**
     * 시작시각을 지정된 시각으로 변경합합니다. 기존 시작시각보다 큰 값(미래) 이어야 합니다.
     */
    def shrinkStartTo(moment: DateTime) {
        assertMutable()
        if (_start.compareTo(moment) < 0) {
            log.debug("shrink start=[{}] to [{}]", _start, moment)
            _start = moment
        }
    }

    /**
     * 완료시각을 지정된 시각으로 변경합니다. 기존 완료시각보다 작은 값(과거) 이어야 하고, 시작시각보다는 같거나 커야 합니다.
     */
    def shrinkEndTo(moment: DateTime) {
        assertMutable()
        if (_start.compareTo(moment) < 0) {
            log.debug("shrink end=[{}] to [{}]", _end, moment)
            _end = moment
        }
    }

    /**
     * 기간을 지정된 기간으로 축소시킵니다.
     */
    def shrinkTo(period: ITimePeriod) {
        assertMutable()
        Guard.shouldNotBeNull(period, "period")

        if (period.hasStart) shrinkStartTo(period.getStart)
        if (period.hasEnd) shrinkEndTo(period.getEnd)
    }
}
