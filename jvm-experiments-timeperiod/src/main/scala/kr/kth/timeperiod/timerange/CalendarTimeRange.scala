package kr.kth.timeperiod.timerange

import com.google.common.base.Objects
import java.lang.String
import kr.kth.commons.Guard._
import kr.kth.commons.tools.ScalaHash
import kr.kth.timeperiod._
import org.joda.time.DateTime

/**
 * kr.kth.timeperiod.timerange.ICalendarTimeRange
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 15
 */
class CalendarTimeRange(val timeCalendar: ITimeCalendar) extends ICalendarTimeRange {
  override def getTimeCalendar: ITimeCalendar = timeCalendar
}

object CalendarTimeRange {

  def toCalendarTimeRange(period: ITimePeriod, mapper: ITimePeriodMapper = TimeCalendar.Default): TimeRange = {
    shouldNotBeNull(period, "period")
    val mappedStart: DateTime = mapper.mapStart(period.getStart)
    val mappedEnd: DateTime = mapper.mapEnd(period.getEnd)

    Times.assertValidPeriod(mappedStart, mappedEnd)
    val mapped: TimeRange = new TimeRange(mappedStart, mappedEnd)
    log.debug("TimeCalendar 매퍼를 이용하여 기간을 매핑했습니다. period=[{}], mapped=[{}]", period, mapped)

    mapped
  }
}

trait ICalendarTimeRange extends ITimeRange {

  def getTimeCalendar: ITimeCalendar

  def getStartYear: Int = getStart.getYear

  def getStartMonth: Int = getStart.getMonthOfYear

  def getStartDay: Int = getStart.getDayOfMonth

  def getStartHour: Int = getStart.getHourOfDay

  def getStartMinute: Int = getStart.getMinuteOfHour

  def getEndYear: Int = getEnd.getYear

  def getEndMonth: Int = getEnd.getMonthOfYear

  def getEndDay: Int = getStart.getDayOfMonth

  def getEndHour: Int = getStart.getHourOfDay

  def getEndMinute: Int = getStart.getMinuteOfHour

  def getMappedStart: DateTime = getTimeCalendar.mapStart(getStart)

  def getMappedEnd: DateTime = getTimeCalendar.mapEnd(getEnd)

  def getUnmappedStart: DateTime = getTimeCalendar.unmapStart(getStart)

  def getUnmappedEnd: DateTime = getTimeCalendar.unmapEnd(getEnd)

  def getStartMonthStart: DateTime = Times.trimToDay(getStart)

  def getEndMonthStart: DateTime = Times.trimToDay(getEnd)

  def getStartDayStart: DateTime = Times.trimToHour(getStart)

  def getEndDayEnd: DateTime = Times.trimToHour(getEnd)

  def getStartHourStart: DateTime = Times.trimToMinute(getStart)

  def getEndHourEnd: DateTime = Times.trimToMinute(getEnd)

  def getStartMinuteStart: DateTime = Times.trimToSecond(getStart)

  def getEndMinuteEnd: DateTime = Times.trimToSecond(getEnd)

  def getStartSecondStart: DateTime = Times.trimToMillis(getStart)

  def getEndSecondEnd: DateTime = Times.trimToMillis(getEnd)

  override def copy(offset: Long): ITimePeriod = {
    CalendarTimeRange.toCalendarTimeRange(super.copy(offset), getTimeCalendar)
  }

  override def format(formatter: Option[ITimeFormatter]): String = {
    val fmt = formatter.getOrElse(TimeFormatter.instance)
    fmt.getCalendarPeriod(fmt.getDate(getStart), fmt.getDate(getEnd), getDuration)
  }

  def compareTo(other: ICalendarTimeRange): Int =
    getStart.compareTo(other.getStart)


  override def hashCode: Int = {
    ScalaHash.compute(super.hashCode, getTimeCalendar)
  }

  protected override def buildStringHelper(): Objects.ToStringHelper = {
    super.buildStringHelper().add("timeCalendar", getTimeCalendar)
  }
}

