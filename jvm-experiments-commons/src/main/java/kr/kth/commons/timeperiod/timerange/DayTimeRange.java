package kr.kth.commons.timeperiod.timerange;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import kr.kth.commons.timeperiod.ITimeCalendar;
import kr.kth.commons.timeperiod.ITimePeriod;
import kr.kth.commons.timeperiod.TimeCalendar;
import kr.kth.commons.timeperiod.TimeSpec;
import kr.kth.commons.tools.HashTool;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.util.List;

/**
 * kr.kth.commons.timeperiod.timerange.DayTimeRange
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 25.
 */
@Slf4j
public abstract class DayTimeRange extends CalendarTimeRange {

	private static final long serialVersionUID = 1000583170028762454L;

	@Getter private final int dayCount;

	protected DayTimeRange(DateTime moment, int dayCount, ITimeCalendar calendar) {
		this(calendar.getYear(moment), calendar.getMonth(moment), calendar.getDayOfMonth(moment),
		     dayCount, calendar);
	}

	protected DayTimeRange(int year, int month, int day, int dayCount) {
		this(year, month, day, dayCount, new TimeCalendar());
	}

	protected DayTimeRange(int year, int month, int day, int dayCount, ITimeCalendar calendar) {
		super(getPeriodOf(year, month, day, dayCount), calendar);
		this.dayCount = dayCount;
	}

	public int getStartDayOfWeek() {
		return getTimeCalendar().getDayOfWeek(getStart());
	}

	public String getStartDayName() {
		return getTimeCalendar().getDayName(getStartDayOfWeek());
	}

	public int getEndDayOfWeek() {
		return getTimeCalendar().getDayOfWeek(getEnd());
	}

	public String getEndDayName() {
		return getTimeCalendar().getDayName(getEndDayOfWeek());
	}

	public List<HourRange> getHours() {
		if (log.isDebugEnabled())
			log.debug("HourRange 컬렉션을 빌드합니다. dayCount=[{}]", dayCount);

		List<HourRange> hours = Lists.newArrayListWithCapacity(dayCount * TimeSpec.HoursPerDay);
		DateTime startDay = getStartDayStart();

		for (int d = 0; d < dayCount; d++) {
			for (int h = 0; h < TimeSpec.HoursPerDay; h++) {
				hours.add(new HourRange(startDay.plusDays(d).plusHours(h), getTimeCalendar()));
			}
		}
		return hours;
	}

	@Override
	public int hashCode() {
		return HashTool.compute(super.hashCode(), this.dayCount);
	}

	@Override
	protected Objects.ToStringHelper buildStringHelper() {
		return super.buildStringHelper()
		            .add("dayCount", dayCount);
	}

	private static ITimePeriod getPeriodOf(int year, int month, int day, int dayCount) {
		DateTime start = new DateTime().withDate(year, month, day);
		return new TimeRange(start, start.plusDays(dayCount));
	}
}
