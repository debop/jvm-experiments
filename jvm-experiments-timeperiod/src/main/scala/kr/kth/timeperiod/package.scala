package kr.kth

import kr.kth.commons.slf4j.Logger
import org.joda.time.{Duration, DateTime}


/**
 * kr.kth.commons.timeperiod package object
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 9.
 */
package object timeperiod {

    private[timeperiod] lazy val log = Logger("kr.kth.timeperiod")

    /**
     * 기본 주차 규칙 (ISO 8601)
     */
    val DefaultWeekOfYearRule = WeekOfYearRuleKind.Iso8601

    private[timeperiod] implicit def dateTime2Millis(x: DateTime): Long = x.getMillis

    private[timeperiod] implicit def millis2DateTime(millis: Long): DateTime = new DateTime().withMillis(millis)

    lazy val DateTimeOrdering: Ordering[DateTime] = {
        new Ordering[DateTime] {
            override def compare(x: DateTime, y: DateTime): Int = x.compareTo(y)
        }
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

    lazy val TimeLimeMomentOrdering: Ordering[ITimeLineMoment] = {
        new Ordering[ITimeLineMoment] {
            def compare(x: ITimeLineMoment, y: ITimeLineMoment): Int =
                x.getMoment.compareTo(y.getMoment)
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

    implicit class OrderedDateVal(val dateVal: DateVal) extends Ordered[DateVal] {
        def compare(that: DateVal) = dateVal.date compareTo that.date

        def +(that: DateVal): DateVal = new DateVal(this.dateVal.date.plus(that.dateVal.date.getMillis))

        def -(that: DateVal): DateVal = new DateVal(this.dateVal.date.minus(that.dateVal.date.getMillis))

        def +(days: Int): DateVal = new DateVal(this.dateVal.date.plusDays(days))

        def -(days: Int): DateVal = new DateVal(this.dateVal.date.minusDays(days))
    }

    implicit class OrderedTimeVal(val timeVal: TimeVal) extends Ordered[TimeVal] {
        def compare(that: TimeVal) = timeVal.getTime compareTo that.getTime

        def +(that: TimeVal): DateVal = new DateVal(this.timeVal.getTime.plus(that.timeVal.getTime.getMillis))

        def -(that: TimeVal): DateVal = new DateVal(this.timeVal.getTime.minus(that.timeVal.getTime.getMillis))

        def +(days: Int): DateVal = new DateVal(this.timeVal.getTime.plusDays(days))

        def -(days: Int): DateVal = new DateVal(this.timeVal.getTime.minusDays(days))
    }

    implicit class OrderedDateTime(val date: DateTime) extends Ordered[DateTime] {

        def compare(that: DateTime): Int = date.compareTo(that)

        def +(that: DateTime): DateTime = this.date.plus(that.getMillis)

        def -(that: DateTime): DateTime = this.date.minus(that.getMillis)

        def +(millis: Long): DateTime = this.date.plus(millis)

        def -(millis: Long): DateTime = this.date.minus(millis)
    }

    implicit class OrderedDuration(val duration: org.joda.time.Duration) extends Ordered[Duration] {

        def compare(that: Duration) = duration.compareTo(that)

        def +(that: Duration) = this.duration.plus(that.getMillis)

        def -(that: Duration) = this.duration.minus(that.getMillis)
    }

}
