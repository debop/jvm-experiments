package kr.kth.commons.timeperiod.timerange;

import kr.kth.commons.timeperiod.ITimeCalendar;
import kr.kth.commons.timeperiod.ITimeRange;

/**
 * kr.kth.commons.timeperiod.timerange.ICalendarTimeRange
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 23.
 */
public interface ICalendarTimeRange extends ITimeRange {

	ITimeCalendar getTimeCalendar();
}
