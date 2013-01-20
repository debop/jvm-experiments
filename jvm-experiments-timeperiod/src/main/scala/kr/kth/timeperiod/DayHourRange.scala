package kr.kth.timeperiod

import DayOfWeek._
import kr.kth.commons.tools.ScalaHash
import kr.kth.commons.{Guard, TimeVal}

/**
 * 특정요일의 한시간단위의 기간을 표현한다. (예: 금요일 1시~ 5시)
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 27
 */
class DayHourRange(val dayOfWeek: DayOfWeek,
                   start: TimeVal,
                   end: TimeVal) extends HourRangeInDay(start, end) {

    override def hashCode = ScalaHash.compute(super.hashCode(), dayOfWeek)

    protected override def buildStringHelper =
        super.buildStringHelper.add("dayOfWeek", dayOfWeek)
}

object DayHourRange {

    def apply(dayOfWeek: Int, hour: Int): DayHourRange = {
        Guard.shouldBeBetween(dayOfWeek, 1, TimeSpec.DaysPerWeek, "dayOfWeek")
        apply(DayOfWeek(dayOfWeek), hour, hour)
    }

    def apply(dayOfWeek: DayOfWeek, hour: Int): DayHourRange = {
        this.apply(dayOfWeek, hour, hour)
    }

    def apply(dayOfWeek: DayOfWeek, startHour: Int, endHour: Int): DayHourRange = {

        Guard.shouldBeInRange(startHour, 0, TimeSpec.HoursPerDay, "startHour")
        Guard.shouldBeInRange(endHour, 0, TimeSpec.HoursPerDay, "endHour")

        if (startHour <= endHour)
            new DayHourRange(dayOfWeek, TimeVal(startHour), TimeVal(endHour))
        else
            new DayHourRange(dayOfWeek, TimeVal(endHour), TimeVal(startHour))
    }

}
