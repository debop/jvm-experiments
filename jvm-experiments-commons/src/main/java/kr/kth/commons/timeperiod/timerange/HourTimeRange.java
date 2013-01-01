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
 * 시간 단위로 기간을 표현하는 클래스입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 25.
 */
@Slf4j
public abstract class HourTimeRange extends CalendarTimeRange {

	private static final long serialVersionUID = 8784288998590133240L;

	@Getter private final int hourCount;
	@Getter private final int endHour;

	protected HourTimeRange(DateTime moment, int hourCount, ITimeCalendar calendar) {
		this(calendar.getYear(moment), calendar.getMonth(moment), calendar.getDayOfMonth(moment),
		     calendar.getHour(moment), hourCount, calendar);
	}

	protected HourTimeRange(int year, int month, int day, int hour, int hourCount) {
		this(year, month, day, hour, hourCount, TimeCalendar.Default());
	}

	protected HourTimeRange(int year, int month, int day, int hour, int hourCount, ITimeCalendar calendar) {
		super(getPeriodOf(year, month, day, hour, hourCount), calendar);

		this.hourCount = hourCount;
		this.endHour = getStart().plusHours(hourCount).getHourOfDay();
	}

	public List<MinuteRange> getMinutes() {

		List<MinuteRange> minutes = Lists.newArrayListWithCapacity(hourCount * TimeSpec.MinutesPerHour);

		DateTime start = getStart();
		for (int h = 0; h < hourCount; h++) {
			for (int m = 0; m < TimeSpec.MinutesPerHour; m++) {
				minutes.add(new MinuteRange(start.plusHours(h).plusMinutes(m), getTimeCalendar()));
			}
		}
		return minutes;
	}

	@Override
	public int hashCode() {
		return HashTool.compute(super.hashCode(), this.hourCount);
	}

	@Override
	protected Objects.ToStringHelper buildStringHelper() {
		return super.buildStringHelper()
		            .add("hourCount", hourCount)
		            .add("endHour", endHour);
	}

	private static ITimePeriod getPeriodOf(int year, int month, int day, int hour, int hourCount) {
		DateTime start = new DateTime().withDate(year, month, day).withHourOfDay(hour);
		return new TimeRange(start, start.plusHours(hourCount));
	}
}
