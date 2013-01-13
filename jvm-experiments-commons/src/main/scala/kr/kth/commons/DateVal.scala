package kr.kth.commons

import kr.kth.commons.tools.ScalaHash
import org.joda.time.DateTime

/**
 * org.joda.timePart.DateTime 중에 Date Part 만을 표현합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 26
 */
class DateVal(val date: DateTime) extends ValueObjectBase with Comparable[DateVal] {

  def year = date.getYear

  def monthOfYear = date.getMonthOfYear

  def dayOfMonth = date.getDayOfMonth

  def getDateTime(time: TimeVal): DateTime = if (time != null) this.date.plusMillis(time.millis.toInt) else this.date

  def getDateTime(hourOfDay: Int = 0, minuteOfHour: Int = 0, secondOfMinute: Int = 0, milliOfSecond: Int = 0): DateTime = {
    this.date.withTime(hourOfDay, minuteOfHour, secondOfMinute, milliOfSecond)
  }

  def ==(other: DateVal) = this.equals(other)

  def <(other: DateVal) = this.compareTo(other) < 0

  def <=(other: DateVal): Boolean = this.compareTo(other) <= 0

  def >(other: DateVal) = this.compareTo(other) > 0

  def >=(other: DateVal) = this.compareTo(other) >= 0

  def compareTo(other: DateVal): Int = {
    Guard.shouldNotBeNull(other, "other")
    this.date.compareTo(other.date)
  }

  override def hashCode(): Int = ScalaHash.compute(date)

  protected override def buildStringHelper() =
    super.buildStringHelper()
      .add("date", date)
}

object DateVal {
  def today: DateVal = new DateVal(DateTime.now())

  def apply(date: DateTime) = {
    new DateVal(date)
  }

  def apply(year: Int, monthOfYear: Int = 1, dayOfMonth: Int = 1) = {
    new DateVal(new DateTime().withDate(year, monthOfYear, dayOfMonth))
  }
}
