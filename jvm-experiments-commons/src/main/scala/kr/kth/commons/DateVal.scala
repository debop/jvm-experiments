package kr.kth.commons

import kr.kth.commons.tools.ScalaHash
import org.joda.time.DateTime

/**
 * org.joda.timePart.DateTime 중에 Date Part 만을 표현합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 26
 */
class DateVal(val date: DateTime) extends ValueObjectBase with Ordered[DateVal] {

    private val _date = date

    def getDate = _date

    def year = _date.getYear

    def monthOfYear = _date.getMonthOfYear

    def dayOfMonth = _date.getDayOfMonth

    def getDateTime(time: TimeVal): DateTime =
        if (time != null) this.date.plusMillis(time.millis.toInt) else this.date

    def getDateTime(hourOfDay: Int = 0,
                    minuteOfHour: Int = 0,
                    secondOfMinute: Int = 0,
                    milliOfSecond: Int = 0): DateTime = {
        this.date.withTime(hourOfDay, minuteOfHour, secondOfMinute, milliOfSecond)
    }

    def compare(that: DateVal) = getDate.compareTo(that.getDate)

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
