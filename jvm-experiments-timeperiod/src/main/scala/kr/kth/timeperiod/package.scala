package kr.kth

import commons.slf4j.Logger
import org.joda.time.DateTime


/**
 * kr.kth.commons.timeperiod package object
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 9.
 */
package object timeperiod {

  lazy val log = Logger("kr.kth.timeperiod")

  /**
   * 기본 주차 규칙 (ISO 8601)
   */
  val DefaultWeekOfYearRule = WeekOfYearRuleKind.Iso8601

  implicit def dateTime2Millis(x: DateTime): Long = x.getMillis()

  lazy val DateTimeOrdering: Ordering[DateTime] = {
    new Ordering[DateTime] {
      override def compare(x: DateTime, y: DateTime): Int = x.getMillis.compareTo(y.getMillis)
    }
  }

  implicit def dateTimeCompare(x: DateTime) =
    new Ordered[DateTime] {
      def compare(that: DateTime): Int = x compareTo that
    }

  implicit def timePeriodCompare(x: ITimePeriod) =
    new Ordered[ITimePeriod] {
      def compare(that: ITimePeriod): Int = x.getStart compareTo that.getStart
    }


  lazy val StartOfTimePeriodOrdering: Ordering[ITimePeriod] = {
    new Ordering[ITimePeriod] {
      override def compare(x: ITimePeriod, y: ITimePeriod): Int = x.getStart.compareTo(y.getStart)
    }
  }

  lazy val EndOfTimePeriodOrdering: Ordering[ITimePeriod] = {
    new Ordering[ITimePeriod] {
      override def compare(x: ITimePeriod, y: ITimePeriod): Int = x.getStart.compareTo(y.getStart)
    }
  }


  implicit def rangeToTimeBlock(range: ITimeRange): TimeBlock = {
    log.debug("TimeRange[{}] 를 TimeBlock으로 변환합니다.", range)
    if (range == null) null
    else {
      if (range.isAnytime) TimeBlock.Anytime
      else TimeBlock(range.getStart, range.getEnd, range.isReadonly)
    }
  }

  implicit def blockToTimeRange(block: ITimeBlock): TimeRange = {
    log.debug("TimeBlock[{}] 을 TimeRange로 변환합니다.", block)
    if (block == null) null
    else if (block.isAnytime) TimeRange.Anytime
    else TimeRange(block.getStart, block.getEnd, block.isReadonly)
  }

  implicit def rangeToTimeInterval(range: ITimeRange): TimeInterval = {
    log.debug("TimeRange[{}]를 TimeInterval로 변환합니다.", range)
    if (range == null) null
    else if (range.isAnytime) TimeInterval.Anytime
    else TimeInterval(range.getStart, range.getEnd, readonly = range.isReadonly)
  }
}
