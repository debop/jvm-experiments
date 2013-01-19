package kr.kth.timeperiod

import com.google.common.base.Objects.ToStringHelper
import java.util.Objects
import kr.kth.commons.ValueObjectBase
import kr.kth.commons.slf4j.Logging
import kr.kth.commons.tools.ScalaHash
import kr.kth.timeperiod.PeriodRelation._
import org.joda.time.DateTime

/**
 * 시간 간격을 표현합니다. 시작~완료 구간과 기간을 표현하는 인터페이스입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 13.
 */
trait ITimePeriod extends ValueObjectBase with Ordered[ITimePeriod] with Logging {

    protected var _start: DateTime = TimeSpec.MinPeriodTime
    protected var _end: DateTime = TimeSpec.MaxPeriodTime
    protected var _readonly: Boolean = false

    def getStart: DateTime = _start

    def getEnd: DateTime = _end

    def getDuration: Long = {
        if (hasPeriod) getEnd.getMillis - getStart.getMillis
        else TimeSpec.MaxPeriodDuration
    }

    def setDuration(duration: Long) {
        assertMutable()
        assert(duration >= 0)
        if (hasStart)
            _end = _start.plus(duration)
    }

    def getDurationDescription: String = org.joda.time.Duration.millis(getDuration).toString

    def hasStart: Boolean = getStart != TimeSpec.MinPeriodTime

    def hasEnd: Boolean = getEnd != TimeSpec.MaxPeriodTime

    def hasPeriod: Boolean = hasStart && hasEnd

    def isMoment: Boolean = hasPeriod && (getStart == getEnd)

    def isAnytime: Boolean = !hasStart && !hasEnd

    def isReadonly: Boolean = _readonly

    protected def setReadonly(nv: Boolean) { _readonly = nv }

    def setup(newStart: DateTime, newEnd: DateTime) {
        log.debug("기간을 새로 설정합니다. newStart=[{}], newEnd=[{}]", newStart, newEnd)

        assertMutable()

        val (start, end) = Times.adjustPeriod(newStart, newEnd)
        _start = if (start != null) start else TimeSpec.MinPeriodTime
        _end = if (end != null) end else TimeSpec.MaxPeriodTime
    }

    def copy(offset: Long): ITimePeriod = {
        log.debug("[{}]를 offset=[{}]을 주어 복사합니다.", this, offset)

        if (offset == 0) TimeRange(this)
        else {
            val start = if (hasStart) getStart.plus(offset) else getStart
            val end = if (hasEnd) getEnd.plus(offset) else getEnd
            new TimeRange(start, end)
        }
    }

    def move(offset: Long) {
        if (offset == 0) return
        assertMutable()

        log.debug("[{}]을 offset[{}] 만큼 이동시킵니다.", this, offset)

        if (hasStart) _start = _start.plus(offset)
        if (hasEnd) _end = _end.plus(offset)

    }

    def isSamePeriod(other: ITimePeriod): Boolean = {
        (other != null) && Objects.equals(_start, other.getStart) && Objects.equals(_end, other.getEnd)
    }

    def hasInside(moment: DateTime): Boolean = Times.hasInside(this, moment)

    def hasInside(other: ITimePeriod): Boolean = Times.hasInside(this, other)

    def intersectsWith(other: ITimePeriod): Boolean = Times.intersectsWith(this, other)

    def overlapsWith(other: ITimePeriod): Boolean = Times.overlapsWith(this, other)

    def reset() {
        assertMutable()
        _start = TimeSpec.MinPeriodTime
        _end = TimeSpec.MaxPeriodTime
    }

    def getRelation(other: ITimePeriod): PeriodRelation = Times.getRelation(this, other)

    def getDescription(formatter: Option[ITimeFormatter] = None): String = format(formatter)

    def getIntersection(other: ITimePeriod): ITimePeriod = Times.getIntersectionRange(this, other)

    def getUnion(other: ITimePeriod): ITimePeriod = Times.getUnionRange(this, other)

    protected final def assertMutable() {
        if (isReadonly)
            throw new IllegalStateException("Current object is readonly!!! object=" + this)
    }

    protected def format(formatter: Option[ITimeFormatter]) = {
        formatter.getOrElse(TimeFormatter.instance).getPeriod(_start, _end, getDuration)
    }

    override def hashCode: Int = ScalaHash.compute(getStart, getEnd, isReadonly)

    protected override def buildStringHelper(): ToStringHelper =
        super.buildStringHelper()
        .add("_start", getStart)
        .add("_end", getEnd)
        .add("_readonly", isReadonly)

    def compare(that: ITimePeriod): Int = getStart compareTo that.getStart
}

