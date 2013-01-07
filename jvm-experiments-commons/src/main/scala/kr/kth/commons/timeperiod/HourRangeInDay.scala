package kr.kth.commons.timeperiod

import kr.kth.commons.base.{TimeVal, ValueObjectBase}
import kr.kth.commons.tools.HashTool
import java.util.Objects

/**
 * 1일 (Day) 안의 한시간 단위를 나타내는 HourRange 를 표현합니다. ( 9시 ~ 6시)
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 26
 */
class HourRangeInDay(val start: TimeVal,
                     val end: TimeVal) extends ValueObjectBase with Comparable[HourRangeInDay] {

  def isMoment = Objects.equals(start, end)

  def hasInside(hour: Int): Boolean = hasInside(new TimeVal(hour))

  def hasInside(target: TimeVal): Boolean = {
    (target.compareTo(this.start) >= 0) && (target.compareTo(this.end) <= 0)
  }

  def compareTo(other: HourRangeInDay): Int = start compareTo other.start

  override def hashCode: Int = HashTool.compute(start, end)

  protected override def buildStringHelper = {
    super.buildStringHelper
      .add("start", start)
      .add("end", end)
  }
}

object HourRangeInDay {

  def apply(start: TimeVal, end: TimeVal): HourRangeInDay = {
    if (start <= end) new HourRangeInDay(start, end)
    else new HourRangeInDay(end, start)
  }

  def apply(startHour: Int, endHour: Int): HourRangeInDay = {
    if (startHour <= endHour) apply(new TimeVal(startHour), new TimeVal(endHour))
    else apply(new TimeVal(endHour), new TimeVal(endHour))
  }

  def apply(hour: Int): HourRangeInDay = apply(hour, hour)
}
