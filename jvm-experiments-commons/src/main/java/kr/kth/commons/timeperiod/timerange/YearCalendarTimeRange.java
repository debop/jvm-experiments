package kr.kth.commons.timeperiod.timerange;

import kr.kth.commons.timeperiod.ITimeCalendar;
import kr.kth.commons.timeperiod.ITimePeriod;
import lombok.extern.slf4j.Slf4j;

/**
 * kr.kth.commons.timeperiod.timerange.YearCalendarTimeRange
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 23.
 */
@Slf4j
public abstract class YearCalendarTimeRange extends CalendarTimeRange {

    private static final long serialVersionUID = -4211622541447295691L;

    protected YearCalendarTimeRange(ITimePeriod period, ITimeCalendar calendar) {
        super(period, calendar);
    }

    public int getBaseMonthOfYear() {
        return getTimeCalendar().getBaseMonthOfYear();
    }

    public int getBaseYear() {
        return getStartYear();
    }
}
