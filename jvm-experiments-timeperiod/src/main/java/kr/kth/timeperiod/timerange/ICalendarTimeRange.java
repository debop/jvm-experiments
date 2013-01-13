package kr.kth.timeperiod.timerange;

import kr.kth.timeperiod.ITimeCalendar;
import kr.kth.timeperiod.ITimeRange;

/**
 * kr.kth.timeperiod.timerange.ICalendarTimeRange
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 23.
 */
public interface ICalendarTimeRange extends ITimeRange {

    ITimeCalendar getTimeCalendar();
}
