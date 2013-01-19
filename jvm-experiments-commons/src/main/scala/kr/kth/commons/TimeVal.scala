package kr.kth.commons

import kr.kth.commons.tools.ScalaHash
import org.joda.time.{Duration, DateTime}

/**
 * org.joda.timePart.DateTime 중에 Time Part 만을 표현합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 26
 */
class TimeVal(time: DateTime) extends ValueObjectBase with Ordered[TimeVal] {

    private val _time: DateTime = time

    def getTime = _time

    def datetime: DateTime = _time

    def hourOfDay: Int = _time.getHourOfDay

    def minuteOfHour: Int = _time.getMinuteOfHour

    def secondOfMinute: Int = _time.getSecondOfMinute

    def millisOfSecond: Int = _time.getMillisOfSecond

    def millis: Long = _time.getMillis

    def getDateTime(moment: DateTime): DateTime = moment.withTimeAtStartOfDay().plus(millis)

    def getDateTime(date: DateVal): DateTime = date.getDateTime(this)


    def compare(that: TimeVal) = getTime.compareTo(that.getTime)

    override def hashCode(): Int = ScalaHash.compute(_time)

    protected override def buildStringHelper() =
        super.buildStringHelper()
        .add("time", _time)


}

object TimeVal {

    def now: TimeVal = new TimeVal(DateTime.now())

    def apply(duration: Duration) = new TimeVal(new DateTime().withMillisOfDay(duration.getMillis.toInt))

    def apply(moment: DateTime) = new TimeVal(new DateTime().withMillis(moment.getMillisOfDay))

    def apply(hourOfDay: Int, minuteOfHour: Int = 0, secondOfMinute: Int = 0, millisOfSecond: Int = 0) = {
        new TimeVal(new DateTime().withTime(hourOfDay, minuteOfHour, secondOfMinute, millisOfSecond))
    }
}
