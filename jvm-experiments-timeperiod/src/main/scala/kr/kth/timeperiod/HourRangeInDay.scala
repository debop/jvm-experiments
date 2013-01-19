package kr.kth.timeperiod

import kr.kth.commons.tools.ScalaHash
import kr.kth.commons.{ValueObjectBase, TimeVal}

/**
 * 1일 (Day) 안의 한시간 단위를 나타내는 HourRange 를 표현합니다. ( 9시 ~ 6시)
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 26
 */
class HourRangeInDay(start: TimeVal, end: TimeVal)
    extends ValueObjectBase with Ordered[HourRangeInDay] {

    private val _start: TimeVal = start
    private val _end: TimeVal = end

    def getStart: TimeVal = _start

    def getEnd: TimeVal = _end

    def isMoment: Boolean = (_start == _end)

    def hasInside(hour: Int): Boolean = hasInside(TimeVal(hour))

    def hasInside(target: TimeVal): Boolean = {
        target >= _start && target <= _end
    }

    def compare(that: HourRangeInDay) = getStart compareTo that.getStart

    override def hashCode: Int = ScalaHash.compute(_start, _end)

    protected override def buildStringHelper = {
        super.buildStringHelper
        .add("start", _start)
        .add("end", _end)
    }
}

object HourRangeInDay {

    def apply(start: TimeVal, end: TimeVal): HourRangeInDay = {
        if (start <= end) new HourRangeInDay(start, end)
        else new HourRangeInDay(end, start)
    }

    def apply(startHour: Int, endHour: Int): HourRangeInDay = {
        if (startHour <= endHour) apply(TimeVal(startHour), TimeVal(endHour))
        else apply(TimeVal(endHour), TimeVal(startHour))
    }

    def apply(hour: Int): HourRangeInDay = apply(hour, hour)


    def unapply(hourRange: HourRangeInDay) = (hourRange.getStart, hourRange.getEnd)
}
