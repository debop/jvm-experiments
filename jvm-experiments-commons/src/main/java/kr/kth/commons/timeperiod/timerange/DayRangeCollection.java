package kr.kth.commons.timeperiod.timerange;

import com.google.common.collect.Lists;
import kr.kth.commons.base.Guard;
import kr.kth.commons.timeperiod.ITimeCalendar;
import kr.kth.commons.timeperiod.ITimeFormatter;
import kr.kth.commons.timeperiod.TimeCalendar;
import kr.kth.commons.timeperiod.TimeFormatter;
import org.joda.time.DateTime;

import java.util.List;

/**
 * 1일 단위의 기간을 나타내는 {@link DayRange}의 컬렉션입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 25.
 */
public class DayRangeCollection extends DayTimeRange {

	private static final long serialVersionUID = 1881676088684386069L;

	// region << Constructors >>

	public DayRangeCollection(DateTime moment, int hourCount) {
		this(moment, hourCount, TimeCalendar.Default);
	}

	public DayRangeCollection(DateTime moment, int hourCount, ITimeCalendar calendar) {
		super(moment, hourCount, calendar);
	}

	public DayRangeCollection(int year, int month, int day, int dayCount) {
		this(year, month, day, dayCount, TimeCalendar.Default);
	}

	public DayRangeCollection(int year, int month, int day, int dayCount, ITimeCalendar calendar) {
		super(year, month, day, dayCount, calendar);
	}

	// endregion

	public List<DayRange> getDays() {

		if (log.isDebugEnabled())
			log.debug("일단위 컬렉션을 반환합니다. 시작일=[{}], 일자수=[{}]", getStart(), getDayCount());

		DateTime startDay = getStart();
		List<DayRange> dayRanges = Lists.newArrayListWithCapacity(getDayCount());

		for (int d = 0; d < getDayCount(); d++) {
			dayRanges.add(new DayRange(startDay.plusDays(d), getTimeCalendar()));
		}

		return dayRanges;
	}

	@Override
	protected String format(ITimeFormatter formatter) {
		ITimeFormatter fmt = Guard.firstNotNull(formatter, TimeFormatter.getInstance());
		return fmt.getCalendarPeriod(getStartDayName(),
		                             getEndDayName(),
		                             fmt.getShortDate(getStart()),
		                             fmt.getShortDate(getEnd()),
		                             getDuration());
	}
}
