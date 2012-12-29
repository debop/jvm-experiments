package kr.kth.commons.base

import org.joda.time.DateTime
import kr.kth.commons.tools.HashTool

/**
 * org.joda.timePart.DateTime 중에 Date Part 만을 표현합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 26
 */
class DateVal extends ValueObjectBase with Comparable[DateVal] {

	private var date: DateTime = _

	def this(moment: DateTime) {
		this()
		date = moment.withTimeAtStartOfDay()
	}

	def this(year: Int, monthOfYear: Int = 1, dayOfMonth: Int = 1) {
		this()
		date = new DateTime().withDate(year, monthOfYear, dayOfMonth)
	}

	def year = date.getYear

	def monthOfYear = date.getMonthOfYear

	def dayOfMonth = date.getDayOfMonth

	def getDateTime(time: TimeVal): DateTime = this.date.plusMillis(time.millis.toInt)

	def getDateTime(hourOfDay: Int = 0, minuteOfHour: Int = 0, secondOfMinute: Int = 0, milliOfSecond: Int = 0): DateTime = {
		this.date.withTime(hourOfDay, minuteOfHour, secondOfMinute, milliOfSecond)
	}

	def compareTo(other: DateVal): Int = {
		Guard.shouldNotBeNull(other, "other")
		this.date.compareTo(other.date)
	}

	override def hashCode(): Int = HashTool.compute(this.date)

	protected override def buildStringHelper() =
		super.buildStringHelper()
		.add("moment", date)
}

object DateVal {
	def now: DateVal = new DateVal(DateTime.now())
}
