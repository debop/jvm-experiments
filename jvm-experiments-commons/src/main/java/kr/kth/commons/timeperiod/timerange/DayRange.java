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
 * 1일 기간을 나타내는 클래스입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 25.
 */
@Slf4j
public class DayRange extends DayTimeRange {

	private static final long serialVersionUID = 5495008766913620838L;

	// region << Constructors >>

	public DayRange() {
		this(new TimeCalendar());
	}

	public DayRange(ITimeCalendar calendar) {
		this(ClockProxy.getClock().now(), calendar);
	}

	public DayRange(DateTime moment) {
		this(moment, new TimeCalendar());
	}

	public DayRange(DateTime moment, ITimeCalendar calendar) {
		super(moment, 1, calendar);
	}

	public DayRange(int year, int month, int day) {
		this(year, month, day, new TimeCalendar());
	}

	public DayRange(int year, int month, int day, ITimeCalendar calendar) {
		super(year, month, day, 1, calendar);
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

	public int getDayOfWeek() {
		return getStartDayOfWeek();
	}

	public String getDayName() {
		return getStartDayName();
	}

	public HourRange getPreviousDay() {
		return addDays(-1);
	}

	public HourRange getNextDay() {
		return addDays(1);
	}

	public HourRange addDays(int days) {
		DateTime start = getStart().withTimeAtStartOfDay();
		return new HourRange(start.plusDays(days), getTimeCalendar());
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
