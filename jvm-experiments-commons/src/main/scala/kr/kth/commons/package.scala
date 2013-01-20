package kr.kth

import org.joda.time.{Duration, DateTime}


/**
 * kr.kth.commons package object
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 9.
 */
package object commons {

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
