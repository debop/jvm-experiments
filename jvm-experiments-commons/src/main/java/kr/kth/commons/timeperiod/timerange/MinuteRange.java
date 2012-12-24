package kr.kth.commons.timeperiod.timerange;

import kr.kth.commons.timeperiod.ITimeCalendar;
import kr.kth.commons.timeperiod.ITimeFormatter;
import kr.kth.commons.timeperiod.TimeCalendar;
import kr.kth.commons.timeperiod.TimeFormatter;
import kr.kth.commons.timeperiod.clock.ClockProxy;
import org.joda.time.DateTime;

import static kr.kth.commons.base.Guard.firstNotNull;

/**
 * 1분 단위 간격을 나타냅니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 25.
 */
public class MinuteRange extends MinuteTimeRange {

	private static final long serialVersionUID = 9163926958263476312L;

	// region << Constructors >>

	public MinuteRange() {
		this(new TimeCalendar());
	}

	public MinuteRange(ITimeCalendar calendar) {
		this(ClockProxy.getClock().now(), calendar);
	}

	public MinuteRange(DateTime moment) {
		this(moment, new TimeCalendar());
	}

	public MinuteRange(DateTime moment, ITimeCalendar calendar) {
		super(moment, 1, calendar);
	}

	public MinuteRange(int year, int month, int day, int hour, int minute) {
		this(year, month, day, hour, minute, new TimeCalendar());
	}

	public MinuteRange(int year, int month, int day, int hour, int minute, ITimeCalendar calendar) {
		super(year, month, day, hour, minute, 1, calendar);
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

	public int getMinute() {
		return getStartMinute();
	}

	public MinuteRange getPreviousMinute() {
		return addMinutes(-1);
	}

	public MinuteRange getNextMinute() {
		return addMinutes(1);
	}

	public MinuteRange addMinutes(int minutes) {
		DateTime start = getStart().withTime(getStartHour(), getStartMinute(), 0, 0);
		return new MinuteRange(start.plusMinutes(minutes), getTimeCalendar());
	}

	@Override
	protected String format(ITimeFormatter formatter) {
		ITimeFormatter fmt = firstNotNull(formatter, TimeFormatter.getInstance());

		return fmt.getCalendarPeriod(fmt.getShortDate(getStart()),
		                             fmt.getShortTime(getStart()),
		                             fmt.getShortTime(getEnd()),
		                             getDuration());
	}
}
