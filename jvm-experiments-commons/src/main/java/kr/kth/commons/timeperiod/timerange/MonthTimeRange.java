package kr.kth.commons.timeperiod.timerange;

import com.google.common.base.Objects;
import kr.kth.commons.base.NotImplementedException;
import kr.kth.commons.timeperiod.ITimeCalendar;
import kr.kth.commons.timeperiod.ITimePeriod;
import kr.kth.commons.timeperiod.TimeCalendar;
import kr.kth.commons.tools.HashTool;
import lombok.Getter;
import org.joda.time.DateTime;

import java.util.List;

/**
 * kr.kth.commons.timeperiod.timerange.MonthTimeRange
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 25.
 */
public class MonthTimeRange extends CalendarTimeRange {

	private static final long serialVersionUID = 3094586442615608448L;

	@Getter private final int monthCount;

	protected MonthTimeRange(DateTime moment, int monthCount, ITimeCalendar calendar) {
		this(calendar.getYear(moment), calendar.getMonth(moment), monthCount, calendar);
	}

	protected MonthTimeRange(int year, int month, int monthCount) {
		this(year, month, monthCount, TimeCalendar.Default);
	}

	protected MonthTimeRange(int year, int month, int monthCount, ITimeCalendar calendar) {
		super(getPeriodOf(year, month, monthCount), calendar);
		this.monthCount = monthCount;
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

	public List<DayRange> getDays() {
		if (log.isDebugEnabled())
			log.debug("DayRange 컬렉션을 빌드합니다. monthCount=[{}]", monthCount);

		throw new NotImplementedException();

//		List<DayRange> days = Lists.newArrayListWithCapacity(monthCount * TimeSpec.MaxDaysPerMonth);
//		DateTime startMonth = TimeTool.startTimeOfMonth(getStart());
//
//		for (int m = 0; m < monthCount; m++) {
//			DateTime monthStart = startMonth.plusMonths(m);
//			int daysOfMonth = TimeTool.getDaysInMonth(monthStart.getYear(), monthStart.getMonthOfYear());
//
//			for (int d = 0; d < daysOfMonth; d++) {
//				days.add(new DayRange(monthStart.plusDays(d), getTimeCalendar()));
//			}
//		}
//		return days;
	}

	@Override
	public int hashCode() {
		return HashTool.compute(super.hashCode(), this.monthCount);
	}

	@Override
	protected Objects.ToStringHelper buildStringHelper() {
		return super.buildStringHelper()
		            .add("monthCount", monthCount);
	}

	private static ITimePeriod getPeriodOf(int year, int month, int monthCount) {
		DateTime start = new DateTime().withDate(year, month, 1);
		return new TimeRange(start, start.plusMonths(monthCount));
	}
}
