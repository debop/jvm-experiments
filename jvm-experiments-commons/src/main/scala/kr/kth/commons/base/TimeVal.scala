package kr.kth.commons.base

import kr.kth.commons.tools.HashTool
import org.joda.time.{Duration, DateTime}

/**
 * org.joda.time.DateTime 중에 Time Part 만을 표현합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 26
 */
class TimeVal extends ValueObjectBase with Comparable[TimeVal] {

	var time: DateTime = _

	def this(duration: Duration) {
		this()
		this.time = new DateTime().withMillisOfDay(duration.getMillis.toInt)
	}

	def this(moment: DateTime) {
		this()
		this.time = new DateTime().withMillis(moment.getMillisOfDay.toLong)
	}

	def this(hourOfDay: Int, minuteOfHour: Int = 0, secondOfMinute: Int = 0, millisOfSecond: Int = 0) {
		this()
		time = new DateTime().withTime(hourOfDay, minuteOfHour, secondOfMinute, millisOfSecond)
	}

	def datetime: DateTime = this.time

	def hourOfDay: Int = time.getHourOfDay

	def minuteOfHour: Int = time.getMinuteOfHour

	def secondOfMinute: Int = time.getSecondOfMinute

	def millisOfSecond: Int = time.getMillisOfSecond

	def millis: Long = time.getMillis

	def getDateTime(moment: DateTime): DateTime = moment.withTimeAtStartOfDay().plus(millis)

	def getDateTime(date: DateVal): DateTime = date.getDateTime(this)

	override def hashCode(): Int = HashTool.compute(time)

	protected override def buildStringHelper() =
		super.buildStringHelper()
			.add("time", time)

	def compareTo(other: TimeVal) = {
		Guard.shouldNotBeNull(other, "other")
		time.compareTo(other.time)
	}
}

object TimeVal {

	def now: TimeVal = new TimeVal(DateTime.now())
}
