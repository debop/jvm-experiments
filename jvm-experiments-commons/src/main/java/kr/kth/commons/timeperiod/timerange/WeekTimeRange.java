package kr.kth.commons.timeperiod.timerange;

import kr.kth.commons.timeperiod.ITimeCalendar;
import kr.kth.commons.timeperiod.ITimePeriod;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * kr.kth.commons.timeperiod.timerange.WeekTimeRange
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 25.
 */
@Slf4j
public class WeekTimeRange extends CalendarTimeRange {

	private static final long serialVersionUID = 7184474814959645157L;

	@Getter private final int year;
	@Getter private final int startWeek;

	protected WeekTimeRange(ITimePeriod period, ITimeCalendar calendar) {
		super(period, calendar);
		year = period.getStart().getYear();
		startWeek = 1;
	}

	// TODO: 구현 중
}
