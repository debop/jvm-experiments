package kr.kth.commons.timeperiod.timerange;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import kr.kth.commons.base.Guard;
import kr.kth.commons.timeperiod.*;
import kr.kth.commons.timeperiod.tools.WeekTools;
import kr.kth.commons.tools.HashTool;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.List;
import java.util.Locale;

/**
 * kr.kth.commons.timeperiod.timerange.WeekTimeRange
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 25.
 */
@Slf4j
public class WeekTimeRange extends CalendarTimeRange {

    private static final long serialVersionUID = 7184474814959645157L;

    @Getter
    private final int year;
    @Getter
    private final int startWeek;
    @Getter
    private final int weekCount;

    protected WeekTimeRange(ITimePeriod period, ITimeCalendar timeCalendar) {
        super(period, timeCalendar);
        year = period.getStart().getYear();
        startWeek = WeekTools.getYearAndWeek(period.getStart(), timeCalendar).getWeekOfYear();
        weekCount = 1;
    }

    protected WeekTimeRange(DateTime moment, int weekCount, ITimeCalendar timeCalendar) {
        super(getPeriodOf(moment, weekCount, timeCalendar), timeCalendar);
        YearAndWeek yearAndWeek = WeekTools.getYearAndWeek(moment, timeCalendar);
        this.year = yearAndWeek.getYear();
        this.startWeek = yearAndWeek.getWeekOfYear();
        this.weekCount = weekCount;
    }

    protected WeekTimeRange(int year, int week, int weekCount, ITimeCalendar timeCalendar) {
        super(getPeriodOf(year, week, weekCount, timeCalendar));
        this.year = year;
        this.startWeek = week;
        this.weekCount = weekCount;
    }

    public int getEndWeek() {
        return startWeek + weekCount - 1;
    }

    public String getStartWeekOfYearName() {
        return getTimeCalendar().getWeekOfYearName(getYear(), getStartWeek());
    }

    public String getEndWeekOfYearName() {
        return getTimeCalendar().getWeekOfYearName(getYear(), getEndWeek());
    }

    public Iterable<DayRange> getDays() {
        DateTime startDay = Times.getStartOfYearWeek(year, startWeek, Locale.getDefault(), getTimeCalendar().getWeekOfYearRule());
        int dayCount = weekCount * TimeSpec.DaysPerWeek;
        List<DayRange> days = Lists.newArrayListWithCapacity(dayCount);

        for (int i = 0; i < dayCount; i++) {
            days.add(new DayRange(startDay.plusDays(i), getTimeCalendar()));
        }
        return days;
    }

    @Override
    public boolean isSamePeriod(ITimePeriod other) {
        return this.equals(other);
    }

    @Override
    public int hashCode() {
        return HashTool.compute(super.hashCode(), year, startWeek, weekCount);
    }

    @Override
    protected Objects.ToStringHelper buildStringHelper() {
        return super.buildStringHelper()
                .add("year", year)
                .add("startWeek", startWeek)
                .add("weekCount", weekCount);
    }

    private static TimeRange getPeriodOf(DateTime moment, int weekCount, ITimeCalendar timeCalendar) {
        DateTime startOfWeek = Times.startTimeOfWeek(moment, timeCalendar.getFirstDayOfWeek());
        return new TimeRange(startOfWeek, Duration.standardDays(weekCount * TimeSpec.DaysPerWeek).getMillis());
    }

    private static TimeRange getPeriodOf(int year, int weekOfYear, int weekCount, ITimeCalendar timeCalendar) {
        Guard.shouldBePositiveNumber(weekCount, "weekCount");

        WeekRange weekRange = WeekTools.getWeekRange(new YearAndWeek(year, weekOfYear), timeCalendar);
        DateTime startDay = weekRange.getStart();
        DateTime endDay = startDay.plusWeeks(weekCount);

        return new TimeRange(startDay, endDay);
    }
}
