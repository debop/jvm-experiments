package kr.kth.timeperiod.timerange;

import kr.kth.commons.Guard;
import kr.kth.commons.tools.HashTool;
import kr.kth.timeperiod.ITimeCalendar;
import kr.kth.timeperiod.tools.TimeTool;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

/**
 * kr.kth.timeperiod.timerange.YearTimeRange
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 23.
 */
@Slf4j
public abstract class YearTimeRange extends YearCalendarTimeRange {

    private static final long serialVersionUID = 498519758680442121L;

    @Getter
    private final int yearCount;

    protected YearTimeRange(DateTime moment, int yearCount, ITimeCalendar calendar) {
        this(TimeTool.getYearOf(calendar, moment), yearCount, calendar);
    }

    protected YearTimeRange(int startYear, int yearCount, ITimeCalendar calendar) {
        super(getPeriodOf(calendar.getBaseMonthOfYear(), startYear, yearCount), calendar);
        this.yearCount = yearCount;
    }

    @Override
    public int getBaseYear() {
        return getStartYear();
    }

    public String getStartYearName() {
        return getTimeCalendar().getYearName(getStartYear());
    }

    public String getEndYearName() {
        return getTimeCalendar().getYearName(getEndYear());
    }

//	public Iterable<HalfyearRange> getHalfyears() {
//		// TODO: 구현 중
//	}
//
//	public Iterable<MonthRange> getMonths() {
//		// TODO: 구현 중
//	}

    @Override
    public int hashCode() {
        return HashTool.compute(super.hashCode(), this.yearCount);
    }


    private static TimeRange getPeriodOf(int month, int year, int yearCount) {
        Guard.shouldBePositiveNumber(yearCount, "yearCount");

        DateTime start = new DateTime(year, month, 1, 0, 0);
        return new TimeRange(start, start.plusYears(yearCount));
    }
}
