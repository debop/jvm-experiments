package kr.kth.timeperiod.timerange

import kr.kth.commons.tools.ScalaHash
import kr.kth.timeperiod._
import org.joda.time.DateTime


abstract class MinuteTimeRange(start: DateTime, minuteCount: Int, timeCalendar: ITimeCalendar)
    extends CalendarTimeRange(start, start.plusMinutes(minuteCount), timeCalendar) {

    private val _minuteCount = minuteCount
    private val _endMinute = start.plusMinutes(minuteCount).getMinuteOfHour

    def this(year: Int, month: Int, day: Int, hour: Int, minute: Int, minuteCount: Int, calendar: ITimeCalendar) {
        this(new DateTime(year, month, day, hour, minute, 0), minuteCount, calendar)
    }

    def this(year: Int, month: Int, day: Int, hour: Int, minute: Int, minuteCount: Int) {
        this(year, month, day, hour, minute, minuteCount, TimeCalendar.Default)
    }

    def getMinuteCount: Int = _minuteCount

    override def getEndMinute: Int = _endMinute

    override def hashCode = ScalaHash.compute(super.hashCode, _minuteCount)

    protected override def buildStringHelper() =
        super.buildStringHelper()
        .add("minuteCount", _minuteCount)
        .add("endMinute", _endMinute)
}

/**
 * 분단위의 기간을 나타내는 클래스
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 17.
 */
class MinuteRange(moment: DateTime, calendar: ITimeCalendar)
    extends MinuteTimeRange(moment, 1, calendar) {

    def this(calendar: ITimeCalendar) {
        this(Clock.getClock.now, calendar)
    }

    def this() {
        this(TimeCalendar.Default)
    }

    def this(year: Int, month: Int, day: Int, hour: Int, minute: Int, calendar: ITimeCalendar) {
        this(new DateTime(year, month, day, hour, minute, 0, 0), calendar)
    }

    def this(year: Int, month: Int, day: Int, hour: Int, minute: Int) {
        this(year, month, day, hour, minute, TimeCalendar.Default)
    }

    def getYear: Int = getStartYear

    def getMonth: Int = getStartMonth

    def getDay: Int = getStartDay

    def getHour: Int = getStartHour

    def getMinute: Int = getStartMinute

    def getPreviousMinute: MinuteRange = addMinutes(-1)

    def getNextMinute: MinuteRange = addMinutes(1)

    def addMinutes(minutes: Int): MinuteRange = {
        val startMinute = getStart
                          .withTimeAtStartOfDay()
                          .withHourOfDay(getStartHour)
                          .withMinuteOfHour(getStartMinute)
        new MinuteRange(startMinute.plusMinutes(minutes), getTimeCalendar)
    }

    override protected def format(formatter: Option[ITimeFormatter]): String = {
        val fmt = formatter.getOrElse(TimeFormatter.instance)
        fmt.getCalendarPeriod(fmt.getShortDate(getStart),
            fmt.getShortTime(getStart),
            fmt.getShortTime(getEnd),
            getDuration)
    }
}

/**
 * 분단위의 기간 정보 ({@link MinuteRange}의 컬렉션
 */
class MinuteRangeCollection(moment: DateTime, minuteCount: Int, calendar: ITimeCalendar)
    extends MinuteTimeRange(moment, minuteCount, calendar) {

    def this(moment: DateTime, minuteCount: Int) {
        this(moment, minuteCount, TimeCalendar.Default)
    }

    def this(year: Int, month: Int, day: Int, hour: Int, minute: Int, minuteCount: Int, calendar: ITimeCalendar) {
        this(new DateTime(year, month, day, hour, minute, 0, 0), minuteCount, calendar)
    }

    def this(year: Int, month: Int, day: Int, hour: Int, minute: Int, minuteCount: Int) {
        this(year, month, day, hour, minute, minuteCount, TimeCalendar.Default)
    }

    def getMinutes: Iterable[MinuteRange] = {
        var startMinute = getStart
                          .withTimeAtStartOfDay()
                          .withHourOfDay(getStartHour)
                          .withMinuteOfHour(getStartMinute)

        (0 until getMinuteCount)
        .map(x => new MinuteRange(startMinute.plusMinutes(x), getTimeCalendar))
    }

}