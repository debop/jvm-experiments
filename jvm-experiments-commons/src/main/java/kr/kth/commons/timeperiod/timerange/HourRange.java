package kr.kth.commons.timeperiod.timerange;

import kr.kth.commons.base.Guard;
import kr.kth.commons.timeperiod.ITimeCalendar;
import kr.kth.commons.timeperiod.ITimeFormatter;
import kr.kth.commons.timeperiod.TimeCalendar;
import kr.kth.commons.timeperiod.TimeFormatter;
import kr.kth.commons.timeperiod.clock.ClockProxy;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

/**
 * 1시간 단위를 표현합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 25.
 */
@Slf4j
public class HourRange extends HourTimeRange {

	private static final long serialVersionUID = 28929008453229484L;

	// region << Constructors >>

	public HourRange() {
		this(new TimeCalendar());
	}

	public HourRange(ITimeCalendar calendar) {
		this(ClockProxy.getClock().now(), calendar);
	}

	public HourRange(DateTime moment) {
		this(moment, new TimeCalendar());
	}

	public HourRange(DateTime moment, ITimeCalendar calendar) {
		super(moment, 1, calendar);
	}

	public HourRange(int year, int month, int day, int hour) {
		this(year, month, day, hour, new TimeCalendar());
	}

	public HourRange(int year, int month, int day, int hour, ITimeCalendar calendar) {
		super(year, month, day, hour, 1, calendar);
	}

	// endregion

	public int getYear() {
		return getStartYear();
	}

	public int getMonth() {
		return getStartMonth();
	}

	public int getDay() {
		return getStartDay();
	}

	public int getHour() {
		return getStartHour();
	}

	public HourRange getPreviousHour() {
		return addHours(-1);
	}

	public HourRange getNextHour() {
		return addHours(1);
	}

	public HourRange addHours(int hours) {
		DateTime start = getStart().withTime(getStartHour(), 0, 0, 0);
		return new HourRange(start.plusHours(hours), getTimeCalendar());
	}

	@Override
	protected String format(ITimeFormatter formatter) {
		ITimeFormatter fmt = Guard.firstNotNull(formatter, TimeFormatter.getInstance());

		return fmt.getCalendarPeriod(fmt.getShortDate(getStart()),
		                             fmt.getShortTime(getStart()),
		                             fmt.getShortTime(getEnd()),
		                             getDuration());
	}
}
