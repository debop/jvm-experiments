package kr.kth.commons.timeperiod.timerange;

import com.google.common.collect.Lists;
import kr.kth.commons.timeperiod.ITimeCalendar;
import kr.kth.commons.timeperiod.TimeCalendar;
import org.joda.time.DateTime;

import java.util.List;

/**
 * 일분 단위의 {@link MinuteRange}의 연속된 컬렉션
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 25.
 */
public class MinuteRangeCollection extends MinuteTimeRange {

	private static final long serialVersionUID = 3798232473967314158L;

	public MinuteRangeCollection(DateTime moment, int minuteCount) {
		this(moment, minuteCount, TimeCalendar.Default);
	}

	public MinuteRangeCollection(DateTime moment, int minuteCount, ITimeCalendar calendar) {
		super(moment, minuteCount, calendar);
	}

	public MinuteRangeCollection(int year, int month, int day, int hour, int minute, int minuteCount) {
		this(year, month, day, hour, minute, minuteCount, TimeCalendar.Default);
	}

	public MinuteRangeCollection(int year, int month, int day, int hour, int minute, int minuteCount, ITimeCalendar calendar) {
		super(year, month, day, hour, minute, minuteCount, calendar);
	}

	public List<MinuteRange> getMinutes() {

		DateTime startMinute = getStart().withTime(getStartHour(), getStartMinute(), 0, 0);
		List<MinuteRange> minuteRanges = Lists.newArrayListWithCapacity(getMinuteCount());

		for (int i = 0; i < getMinuteCount(); i++) {
			minuteRanges.add(new MinuteRange(startMinute.plusMinutes(i), getTimeCalendar()));
		}

		return minuteRanges;
	}
}
