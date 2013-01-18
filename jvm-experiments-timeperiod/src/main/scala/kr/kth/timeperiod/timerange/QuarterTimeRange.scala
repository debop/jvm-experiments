package kr.kth.timeperiod.timerange

import com.google.common.base.Objects.ToStringHelper
import kr.kth.commons.Guard
import kr.kth.commons.tools.ScalaHash
import kr.kth.timeperiod.QuarterKind._
import kr.kth.timeperiod._
import org.joda.time.DateTime

/**
 * 분기(Quarter) 단위의 기간을 표현합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 18
 */
abstract class QuarterTimeRange(startYear: Int, startQuarter: QuarterKind, quarterCount: Int, calendar: ITimeCalendar)
    extends YearCalendarTimeRange(QuarterTimeRange.getPeriodOf(calendar, startYear, startQuarter.id, quarterCount), calendar) {

    private val _startYear: Int = startYear
    private val _startQuarter: QuarterKind = startQuarter
    private val _quarterCount: Int = quarterCount
    private val endYearQuarter = Times.addQuarter(startQuarter, startYear, quarterCount - 1)
    private val _endYear = endYearQuarter.getYear
    private val _endQuarter = endYearQuarter.getQuarter

    def this(startYear: Int, startQuarter: QuarterKind, quarterCount: Int) {
        this(startYear, startQuarter, quarterCount, TimeCalendar.Default)
    }

    override def getBaseYear: Int = _startYear

    override def getStartYear: Int = _startYear

    def getStartQuarter: QuarterKind = _startQuarter

    override def getEndYear: Int = _endYear

    def getEndQuarter: QuarterKind = _endQuarter

    def getQuarterCount: Int = _quarterCount

    def getStartQuarterOfYearName: String = getTimeCalendar.getQuarterOfYearName(getStartYear, getStartQuarter)

    def getStartQuarterName: String = getTimeCalendar.getQuarterName(getStartQuarter)

    def getEndQuarterOfYearName: String = getTimeCalendar.getQuarterOfYearName(getEndYear, getEndQuarter)

    def getEndQuarterName: String = getTimeCalendar.getQuarterName(getEndQuarter)

    override def getStartMonth: Int = {
        val monthCount = (getStartQuarter.id - 1) * TimeSpec.MonthsPerQuarter
        val startYearMonth = Times.addMonth(getStartYear, getYearBaseMonth, monthCount)
        startYearMonth.getMonth
    }

    override def getEndMonth: Int = {
        val monthCount = (getStartQuarter.id - 1 + getQuarterCount) * TimeSpec.MonthsPerQuarter
        val endYearMonth = Times.addMonth(getStartYear, getYearBaseMonth, monthCount)
        endYearMonth.getMonth
    }

    def isCalendarQuarter: Boolean = ((getYearBaseMonth - 1) % TimeSpec.MonthsPerQuarter) == 0

    /**
    * 시작 분기와 완료 분기가 다른 년도인지 판단합니다.
    */
    def isMultipleCalendarYears: Boolean = {
        if (isCalendarQuarter) return false

        val startMonthCount = (getStartQuarter.id - 1) * TimeSpec.MonthsPerQuarter
        val startYearAndMonth = Times.addMonth(getStartYear, getYearBaseMonth, startMonthCount)

        val endMonthCount = startMonthCount + _quarterCount * TimeSpec.MonthsPerQuarter
        val endYearAndMonth = Times.addMonth(getStartYear, getYearBaseMonth, endMonthCount)

        (startYearAndMonth.getYear == endYearAndMonth.getYear)
    }

    /**
    * 해당 기간의 모든 Month를 열거합니다.
    */
    def getMonths: IndexedSeq[MonthRange] = {
        val startMonth = new DateTime(getStartYear, getYearBaseMonth, 1)
        val monthCount = getQuarterCount * TimeSpec.MonthsPerQuarter

        (0 until monthCount).map(m => new MonthRange(startMonth.plusMonths(m), getTimeCalendar))
    }

    override def hashCode: Int =
        ScalaHash.compute(super.hashCode, _startQuarter, _quarterCount, _endQuarter)

    protected override def buildStringHelper(): ToStringHelper =
        super.buildStringHelper()
        .add("startQuarter", _startQuarter)
        .add("quarterCount", _quarterCount)
        .add("endQuarter", _endQuarter)
}

object QuarterTimeRange {

    private[timerange] def getPeriodOf(calendar: ITimeCalendar, year: Int, quarter: Int, quarterCount: Int): ITimePeriod = {
        Guard.shouldBePositiveNumber(quarterCount, "quarterCount")
        val yearStart = new DateTime(year, calendar.getBaseMonthOfYear, 1)
        val start = yearStart.plusMonths((quarter - 1) * TimeSpec.MonthsPerQuarter)
        val end = start.plusMonths(quarterCount * TimeSpec.MonthsPerQuarter)
        TimeRange(start, end)
    }
}


class QuarterRange {

}

object QuarteRange {


}

class QuarterRangeCollection {

}

