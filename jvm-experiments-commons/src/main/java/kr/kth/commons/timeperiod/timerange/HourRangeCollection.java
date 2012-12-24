package kr.kth.commons.timeperiod.timerange;

import com.google.common.collect.Lists;
import kr.kth.commons.timeperiod.ITimeCalendar;
import kr.kth.commons.timeperiod.TimeCalendar;
import org.joda.time.DateTime;

import java.util.List;

/**
 * 한시간 단위의 {@link HourRange}의 연속된 컬렉션
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 25.
 */
public class HourRangeCollection extends HourTimeRange {

	private static final long serialVersionUID = -7443693337436110826L;

	public HourRangeCollection(DateTime moment, int hourCount) {
		this(moment, hourCount, TimeCalendar.Default);
	}

	public HourRangeCollection(DateTime moment, int hourCount, ITimeCalendar calendar) {
		super(moment, hourCount, calendar);
	}

	public HourRangeCollection(int year, int month, int day, int hour, int hourCount) {
		this(year, month, day, hour, hourCount, TimeCalendar.Default);
	}

	public HourRangeCollection(int year, int month, int day, int hour, int hourCount, ITimeCalendar calendar) {
		super(year, month, day, hour, hourCount, calendar);
	}

	public List<HourRange> getHours() {

		DateTime startHour = getStart().withTime(getStartHour(), 0, 0, 0);
		List<HourRange> hourRanges = Lists.newArrayListWithCapacity(getHourCount());

		for (int i = 0; i < getHourCount(); i++) {
			hourRanges.add(new HourRange(startHour.plusHours(i), getTimeCalendar()));
		}

		return hourRanges;
	}
}
