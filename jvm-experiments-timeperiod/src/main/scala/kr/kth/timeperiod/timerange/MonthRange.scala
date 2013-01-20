package kr.kth.timeperiod.timerange

import com.google.common.base.Objects.ToStringHelper
import kr.kth.commons.tools.ScalaHash
import kr.kth.timeperiod._
import org.joda.time.DateTime

/**
 * 월 단위로 기간을 표현합니다.
 */
abstract class MonthTimeRange(moment: DateTime, monthCount: Int, calendar: ITimeCalendar)
    extends CalendarTimeRange(moment, monthCount, calendar) {

    private val _monthCount = monthCount

    val yearMonth = Times.addMonth(moment.getYear, moment.getMonthOfYear, monthCount - 1)
    private val _endYear = yearMonth.year
    private val _endMonth = yearMonth.month

    def this(moment: DateTime, minuteCount: Int) {
        this(moment, minuteCount, TimeCalendar.Default)
    }

    def this(year: Int, month: Int, monthCount: Int, calendar: ITimeCalendar) {
        this(new DateTime().withDate(year, month, 1), monthCount, calendar)
    }

    def this(year: Int, month: Int, minuteCount: Int) {
        this(new DateTime().withDate(year, month, 1), minuteCount, TimeCalendar.Default)
    }


    def getMonthCount = _monthCount

    override def getEndYear = _endYear

    override def getEndMonth = _endMonth

    def getStartMonthName = getTimeCalendar.getMonthName(getStartMonth)

    def getStartMonthOfYearName = getTimeCalendar.getMonthOfYearName(getStartYear, getStartMonth)

    def getEndMonthName = getTimeCalendar.getMonthName(getEndMonth)

    def getEndMonthOfYearName = getTimeCalendar.getMonthOfYearName(getEndYear, getEndMonth)


    def getDays: IndexedSeq[DayRange] = {
        val startMonth = Times.startTimeOfMonth(getStart)

        (0 until getMonthCount)
        .map(m => {
            val monthStart = startMonth.plusMonths(m)
            val daysOfMonth = Times.getDaysInMonth(monthStart.getYear, monthStart.getMonthOfYear)

            (0 until daysOfMonth).map(d => new DayRange(monthStart.plusDays(d), getTimeCalendar))
        })
        .flatten
    }

    override def hashCode: Int = ScalaHash.compute(super.hashCode, _monthCount)

    protected override def buildStringHelper(): ToStringHelper =
        super.buildStringHelper()
        .add("monthCount", _monthCount)
        .add("endYear", _endYear)
        .add("endMonth", _endMonth)
}

/**
 * 월 단위 기간을 나타냅니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 18
 */
class MonthRange(moment: DateTime, calendar: ITimeCalendar)
    extends MonthTimeRange(moment, 1, calendar) {

    def this(year: Int, month: Int, calendar: ITimeCalendar) {
        this(new DateTime().withDate(year, month, 1), calendar)
    }

    def this(year: Int, month: Int) {
        this(year, month, TimeCalendar.Default)
    }

    def this(moment: DateTime) {
        this(moment, TimeCalendar.Default)
    }

    def this(calendar: ITimeCalendar) {
        this(Clock.getClock.today, calendar)
    }

    def this() {
        this(TimeCalendar.Default)
    }

    def getYear = getStartYear

    def getMonth = getStartMonth

    def getMonthName = getStartMonthName

    def getMonthOfYearName = getStartMonthOfYearName

    def getDaysInMonth = Times.getDaysInMonth(getYear, getMonth)

    def getPreviousMonth: MonthRange = addMonths(-1)

    def getNextMonth: MonthRange = addMonths(1)

    def addMonths(months: Int): MonthRange =
        new MonthRange(Times.startTimeOfMonth(getStart).plusMonths(months), getTimeCalendar)

    protected override def format(formatter: Option[ITimeFormatter]): String = {
        val fmt = formatter.getOrElse(TimeFormatter.instance)
        fmt.getCalendarPeriod(getMonthOfYearName,
            fmt.getShortDate(getStart),
            fmt.getShortDate(getEnd),
            getDuration)
    }
}

/**
 * 월 단위 기간을 나타내는 MonthRange 의 컬렉션
 */
class MonthRangeCollection(moment: DateTime, monthCount: Int, calendar: ITimeCalendar)
    extends MonthTimeRange(moment, monthCount, calendar) {

    def this(moment: DateTime, monthCount: Int) {
        this(moment, monthCount, TimeCalendar.Default)
    }

    def this(year: Int, month: Int, monthCount: Int, calendar: ITimeCalendar) {
        this(new DateTime().withDate(year, month, 1), monthCount, calendar)
    }

    def this(year: Int, month: Int, monthCount: Int) {
        this(year, month, monthCount, TimeCalendar.Default)
    }

    def getMonths: IndexedSeq[MonthRange] = {
        val startTime = getStart
        (0 until getMonthCount).map(m => new MonthRange(startTime.plusMonths(m), getTimeCalendar))
    }

    protected override def format(formatter: Option[ITimeFormatter]): String = {
        val fmt = formatter.getOrElse(TimeFormatter.instance)
        fmt.getCalendarPeriod(getStartMonthName,
            getEndMonthName,
            fmt.getShortDate(getStart),
            fmt.getShortDate(getEnd),
            getDuration)
    }
}
