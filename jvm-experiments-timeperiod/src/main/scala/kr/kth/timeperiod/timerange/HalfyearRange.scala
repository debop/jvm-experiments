package kr.kth.timeperiod.timerange

import kr.kth.commons.tools.ScalaHash
import kr.kth.timeperiod.HalfYearKind._
import kr.kth.timeperiod._
import org.joda.time.DateTime


abstract class HalfyearTimeRange(startYear: Int,
                                 startHalfyear: HalfYearKind,
                                 halfyearCount: Int,
                                 calendar: ITimeCalendar)
    extends YearCalendarTimeRange(HalfyearTimeRange.
                                  getPeriodOf(calendar.getBaseMonthOfYear,
                                              startYear,
                                              startHalfyear,
                                              halfyearCount),
                                  calendar) {

    private val _startYear = startYear
    private val _startHalfyear = startHalfyear
    private val _halfyearCount = halfyearCount

    private val endYearAndHalfyear = Times.addHalfyear(startHalfyear, startYear, halfyearCount - 1)
    private val _endYear = endYearAndHalfyear.getYear
    private val _endHalfyear = endYearAndHalfyear.getHalfyear


    def geetBaseYear = _startYear

    override def getStartYear = _startYear

    def getStartHalfyear = _startHalfyear

    override def getEndYear = _endYear

    def getEndHalfyear = _endHalfyear

    def getHalfyearCount = _halfyearCount

    def getStartHalfyearName =
        getTimeCalendar.getHalfYearName(getStartHalfyear)

    def getStartHalfyearOfYearName =
        getTimeCalendar.getHalfYearOfYearName(getStartYear, getStartHalfyear)

    def getEndHalfyearName =
        getTimeCalendar.getHalfYearName(getEndHalfyear)

    def getEndHalfyearOfYearName =
        getTimeCalendar.getHalfYearOfYearName(getEndYear, getEndHalfyear)

    def isCalendarHalfyear: Boolean =
        ((getYearBaseMonth - 1) % TimeSpec.MonthsPerHalfyear) == 0

    def isMultipleCalnedarYears: Boolean = {
        if (isCalendarHalfyear) return false

        val monthCount = (getStartHalfyear.id - 1) * TimeSpec.MonthsPerHalfyear
        val startYM = Times.addMonth(getStartYear, getYearBaseMonth, monthCount)

        val monthCount2 = monthCount + getHalfyearCount * TimeSpec.MonthsPerHalfyear
        val endYM = Times.addMonth(getStartYear, getYearBaseMonth, monthCount2)

        startYM.getYear != endYM.getYear
    }

    def getQuarters: IndexedSeq[QuarterRange] = {
        val quarterCount = getHalfyearCount * TimeSpec.QuartersPerHalfyear
        var startQuarter = (getStartHalfyear.id - 1) * TimeSpec.QuartersPerHalfyear

        (0 until quarterCount).map(q => {
            val targetQuarter = (startQuarter + q) % TimeSpec.QuartersPerYear
            val year = getBaseYear + (targetQuarter / TimeSpec.QuartersPerYear)

            new QuarterRange(year, QuarterKind(targetQuarter + 1), getTimeCalendar)
        })
    }

    def getMonths: IndexedSeq[MonthRange] = {
        val startMonth = new DateTime().withDate(getStartYear, getYearBaseMonth, 1)
        val monthCount = getHalfyearCount * TimeSpec.MonthsPerHalfyear

        (0 until monthCount).map(m => new MonthRange(startMonth.plusMonths(m), getTimeCalendar))
    }

    override def hashCode: Int =
        ScalaHash.compute(super.hashCode, getStartHalfyear, getEndHalfyear, getHalfyearCount)

    protected override def buildStringHelper() =
        super.buildStringHelper()
        .add("startYear", _startYear)
        .add("startHalfyear", _startHalfyear)
        .add("endYear", _endYear)
        .add("endHalfyear", _endHalfyear)
        .add("halfyearCount", _halfyearCount)
}

object HalfyearTimeRange {
    def getPeriodOf(baseMonth: Int, year: Int, halfyear: HalfYearKind, halfyearCount: Int): TimeRange = ???
}

/**
 * kr.kth.timeperiod.timerange.HalfyearRange
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 18.
 */
class HalfyearRange(startYear: Int, halfyear: HalfYearKind, calendar: ITimeCalendar)
    extends HalfyearTimeRange(startYear, halfyear, 1, calendar) {

    def this(startYear: Int, halfyear: HalfYearKind) {
        this(startYear, halfyear, TimeCalendar.Default)
    }

    def this(moment: DateTime, calendar: ITimeCalendar) {
        this(Times.getYearOfCalendar(calendar, moment),
             Times.getHalfyearOfMonth(moment.getMonthOfYear, calendar.getBaseMonthOfYear),
             calendar)
    }

    def this(moment: DateTime) {
        this(moment, TimeCalendar.Default)
    }

    def this() {
        this(Clock.getClock.today, TimeCalendar.Default)
    }

    def getYear = getStartYear

    def getHalfyear = getStartHalfyear

    def getHalfyearName = getTimeCalendar.getHalfYearName(getHalfyear)

    def getHalfyearOfYearName = getTimeCalendar.getHalfYearOfYearName(getYear, getHalfyear)

    def getPreviousHalfyear = addHalfyears(-1)

    def getNextHalfyear = addHalfyears(1)

    def addHalfyears(halfyears: Int): HalfyearRange = {
        val halfyear = Times.addHalfyear(getHalfyear, getYear, halfyears)
        new HalfyearRange(halfyear.getYear, halfyear.getHalfyear, getTimeCalendar)
    }

    protected override def format(formatter: Option[ITimeFormatter]) = {
        val fmt = formatter.getOrElse(TimeFormatter.instance)

        fmt.getCalendarPeriod(getHalfyearOfYearName,
                              fmt.getShortDate(getStart),
                              fmt.getShortDate(getEnd),
                              getDuration)
    }
}

class HalfyearRangeCollection(year: Int, halfyear: HalfYearKind, halfyearCount: Int, calendar: ITimeCalendar)
    extends HalfyearTimeRange(year, halfyear, halfyearCount, calendar) {

    def this(year: Int, halfyear: HalfYearKind, halfyearCount: Int) {
        this(year, halfyear, halfyearCount, TimeCalendar.Default)
    }

    def this(moment: DateTime, halfyearCount: Int, calendar: ITimeCalendar) {
        this(Times.getYearOfCalendar(calendar, moment),
             Times.getHalfyearOfMonth(moment.getMonthOfYear, calendar.getBaseMonthOfYear),
             halfyearCount,
             calendar)
    }

    def this(moment: DateTime, halfyearCount: Int) {
        this(moment, halfyearCount, TimeCalendar.Default)
    }

    def getHalfyears: IndexedSeq[HalfyearRange] = {
        (0 until getHalfyearCount).map(hy => {
            val yhy = Times.addHalfyear(getStartHalfyear, getStartYear, hy)
            new HalfyearRange(yhy.getYear, yhy.getHalfyear, getTimeCalendar)
        })
    }

    protected override def format(formatter: Option[ITimeFormatter]) = {
        val fmt = formatter.getOrElse(TimeFormatter.instance)

        fmt.getCalendarPeriod(getStartHalfyearOfYearName,
                              getEndHalfyearOfYearName,
                              fmt.getShortDate(getStart),
                              fmt.getShortDate(getEnd),
                              getDuration)
    }
}
