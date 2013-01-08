package kr.kth.commons.timeperiod.timerange;

import kr.kth.commons.timeperiod.ITimeCalendar;
import kr.kth.commons.timeperiod.ITimePeriod;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

/**
 * kr.kth.commons.timeperiod.timerange.WeekRange
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 30.
 */
@Slf4j
public class WeekRange extends WeekTimeRange {

    private static final long serialVersionUID = 5762664028391472463L;

    public WeekRange(ITimePeriod period, ITimeCalendar calendar) {
        super(period, calendar);
    }

    public WeekRange(DateTime moment, int weekCount, ITimeCalendar timeCalendar) {
        super(moment, weekCount, timeCalendar);
    }

    public WeekRange(int year, int week, int weekCount, ITimeCalendar timeCalendar) {
        super(year, week, weekCount, timeCalendar);
    }
}
