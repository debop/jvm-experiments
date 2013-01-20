package kr.kth.commons

import kr.kth.commons.tools.ScalaHash
import org.joda.time.{Duration, DateTime}

/**
 * org.joda.timePart.DateTime 중에 Time Part 만을 표현합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 26
 */
class TimeVal(val time: DateTime) extends ValueObjectBase with Ordered[TimeVal] {

    def getTime = time

    def datetime: DateTime = time

    def hourOfDay: Int = time.getHourOfDay

    def minuteOfHour: Int = time.getMinuteOfHour

    def secondOfMinute: Int = time.getSecondOfMinute

    def millisOfSecond: Int = time.getMillisOfSecond

    def millis: Long = time.getMillis

    def getDateTime(moment: DateTime): DateTime = moment.withTimeAtStartOfDay() + millis

    def getDateTime(date: DateVal): DateTime = date.getDateTime(this)

    def compare(that: TimeVal) = time compareTo that.time

    override def hashCode: Int = ScalaHash.compute(time)

    protected override def buildStringHelper =
        super.buildStringHelper()
        .add("time", time)
}

object TimeVal {

    def now: TimeVal = new TimeVal(DateTime.now())

    def apply(duration: Duration) = new TimeVal(new DateTime().withMillisOfDay(duration.getMillis.toInt))

    def apply(moment: DateTime) = new TimeVal(new DateTime().withMillis(moment.getMillisOfDay))

    def apply(hourOfDay: Int, minuteOfHour: Int = 0, secondOfMinute: Int = 0, millisOfSecond: Int = 0) = {
        new TimeVal(new DateTime().withTime(hourOfDay, minuteOfHour, secondOfMinute, millisOfSecond))
    }
}
