package kr.kth.commons.timeperiod.timerange;

import com.google.common.collect.Lists;
import kr.kth.commons.timeperiod.ITimeCalendar;
import kr.kth.commons.timeperiod.ITimeFormatter;
import kr.kth.commons.timeperiod.TimeCalendar;
import kr.kth.commons.timeperiod.Times;
import org.joda.time.DateTime;

import java.util.List;

/**
 * 년 단위의 기간의 컬렉션
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 29.
 */
public class YearRangeCollection extends YearTimeRange {

	public YearRangeCollection(DateTime moment, int yearCount) {
		this(moment, yearCount, TimeCalendar.Default());
	}

	public YearRangeCollection(DateTime moment, int yearCount, ITimeCalendar timeCalendar) {
		this(Times.getYearOfCalendar(timeCalendar, moment), yearCount, timeCalendar);
	}

	public YearRangeCollection(int year, int yearCount) {
		this(year, yearCount, TimeCalendar.Default());
	}

	public YearRangeCollection(int year, int yearCount, ITimeCalendar timeCalendar) {
		super(year, yearCount, timeCalendar);
	}

	public Iterable<YearRange> getYears() {

		int startYear = getStartYear();
		int yearCount = getYearCount();
		ITimeCalendar timeCalendar = getTimeCalendar();
		List<YearRange> years = Lists.newArrayListWithCapacity(yearCount);

		for (int i = 0; i < yearCount; i++) {
			years.add(new YearRange(startYear + i, timeCalendar));
		}
		return years;
	}

	@Override
	protected String format(ITimeFormatter formatter) {
		return formatter.getCalendarPeriod(getStartYearName(),
		                                   getEndYearName(),
		                                   formatter.getShortDate(getStart()),
		                                   formatter.getShortDate(getEnd()),
		                                   getDuration());
	}
}
