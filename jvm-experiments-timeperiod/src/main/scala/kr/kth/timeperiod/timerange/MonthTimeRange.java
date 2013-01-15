package kr.kth.timeperiod.timerange;

import com.google.common.base.Objects;
import kr.kth.commons.Guard;
import kr.kth.commons.NotImplementedException;
import kr.kth.commons.tools.HashTool;
import kr.kth.timeperiod.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.util.List;

/**
 * kr.kth.timeperiod.timerange.MonthTimeRange
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 25.
 */
@Slf4j
public class MonthTimeRange extends CalendarTimeRange {

    private static final long serialVersionUID = 3094586442615608448L;

    @Getter
    private final int monthCount;
    @Getter
    private final int endYear;
    @Getter
    private final int endMonth;

    protected MonthTimeRange(DateTime moment, int monthCount, ITimeCalendar calendar) {
        this(calendar.getYear(moment), calendar.getMonth(moment), monthCount, calendar);
    }

    protected MonthTimeRange(int year, int month, int monthCount) {
        this(year, month, monthCount, TimeCalendar.Default());
    }

    protected MonthTimeRange(int year, int month, int monthCount, ITimeCalendar calendar) {
        super(getPeriodOf(year, month, monthCount), calendar);
        Guard.shouldBePositiveNumber(monthCount, "monthCount");

        this.monthCount = monthCount;

        YearAndMonth yearAndMonth = Times.addMonth(year, month, monthCount - 1);
        this.endYear = yearAndMonth.year();
        this.endMonth = yearAndMonth.month();
    }

    public String getStartMonthName() {
        return getTimeCalendar().getMonthOfYearName(getStartYear(), getStartMonth());
    }

    public String getStartMonthOfYearName() {
        return getTimeCalendar().getMonthOfYearName(getStartYear(), getStartMonth());
    }

    public String getEndMonthName() {
        return getTimeCalendar().getMonthOfYearName(getEndYear(), getEndMonth());
    }

    public String getEndMonthOfYearName() {
        return getTimeCalendar().getMonthOfYearName(getEndYear(), getEndMonth());
    }

    public List<DayRange> getDays() {
        if (MonthTimeRange.log.isDebugEnabled())
            MonthTimeRange.log.debug("DayRange 컬렉션을 빌드합니다. monthCount=[{}]", monthCount);

        throw new NotImplementedException();

//		List<DayRange> days = Lists.newArrayListWithCapacity(monthCount * TimeSpec.MaxDaysPerMonth);
//		DateTime startMonth = Times.startTimeOfMonth(getStart());
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
                .add("monthCount", monthCount)
                .add("endYear", endYear)
                .add("endMonth", endMonth);
    }

    private static ITimePeriod getPeriodOf(int year, int month, int monthCount) {
        DateTime start = Times.startTimeOfMonthByYear(year, month);
        return new TimeRange(start, start.plusMonths(monthCount));
    }
}
