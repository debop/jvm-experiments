package kr.kth.commons.timeperiod.timerange;

import kr.kth.commons.timeperiod.ITimeCalendar;
import kr.kth.commons.timeperiod.TimeCalendar;
import kr.kth.commons.timeperiod.TimeSpec;
import kr.kth.commons.timeperiod.clock.ClockProxy;
import kr.kth.commons.timeperiod.tools.TimeTool;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

/**
 * 1년(Year) 단위의 기간을 표현
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 23.
 */
@Slf4j
public class YearRange extends YearTimeRange {

    private static final long serialVersionUID = -7756718452396550503L;

    public YearRange() {
        this(TimeCalendar.Default());
    }

    public YearRange(ITimeCalendar calendar) {
        this(ClockProxy.getClock().now(), calendar);
    }

    public YearRange(DateTime moment) {
        this(moment, TimeCalendar.Default());
    }

    public YearRange(DateTime moment, ITimeCalendar calendar) {
        this(TimeTool.getYearOf(calendar, moment), calendar);
    }

    public YearRange(int year) {
        this(year, TimeCalendar.Default());
    }

    public YearRange(int year, ITimeCalendar calendar) {
        super(year, 1, calendar);
    }

    public int getYearValue() {
        return getStartYear();
    }

    public String getYearName() {
        return getStartYearName();
    }

    public boolean isCalendarYear() {
        return getBaseMonthOfYear() == TimeSpec.CalendarYearStartMonth;
    }

    public YearRange getPreviousYear() {
        return addYears(-1);
    }

    public YearRange getNextYear() {
        return addYears(1);
    }

    public YearRange addYears(int years) {
        DateTime startTime = new DateTime(getStartYear(), getBaseMonthOfYear(), 1, 0, 0);
        return new YearRange(startTime.plusYears(years), getTimeCalendar());
    }
}
