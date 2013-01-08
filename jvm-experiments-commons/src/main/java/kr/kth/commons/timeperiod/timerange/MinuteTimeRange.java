package kr.kth.commons.timeperiod.timerange;

import com.google.common.base.Objects;
import kr.kth.commons.timeperiod.ITimeCalendar;
import kr.kth.commons.timeperiod.ITimePeriod;
import kr.kth.commons.timeperiod.TimeCalendar;
import kr.kth.commons.tools.HashTool;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

/**
 * 분 단위로 기간을 표현하는 클래스입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 25.
 */
@Slf4j
public abstract class MinuteTimeRange extends CalendarTimeRange {

    private static final long serialVersionUID = -6521723448360027848L;

    @Getter
    private final int minuteCount;
    @Getter
    private final int endMinute;

    protected MinuteTimeRange(DateTime moment, int minuteCount, ITimeCalendar calendar) {
        this(calendar.getYear(moment), calendar.getMonth(moment), calendar.getDayOfMonth(moment),
                calendar.getHour(moment), calendar.getMinute(moment), minuteCount, calendar);
    }

    protected MinuteTimeRange(int year, int month, int day, int hour, int minute, int minuteCount) {
        this(year, month, day, hour, minute, minuteCount, TimeCalendar.Default());
    }

    protected MinuteTimeRange(int year, int month, int day, int hour, int minute, int minuteCount, ITimeCalendar calendar) {
        super(getPeriodOf(year, month, day, hour, minute, minuteCount), calendar);

        this.minuteCount = minuteCount;
        this.endMinute = getStart().plusMinutes(minuteCount).getMinuteOfHour();
    }

    @Override
    public int hashCode() {
        return HashTool.compute(super.hashCode(), this.minuteCount);
    }

    @Override
    protected Objects.ToStringHelper buildStringHelper() {
        return super.buildStringHelper()
                .add("minuteCount", minuteCount)
                .add("endMinute", endMinute);
    }

    static ITimePeriod getPeriodOf(int year, int month, int day, int hour, int minute, int minuteCount) {
        DateTime start = new DateTime(year, month, day, hour, minute, 0);
        return new TimeRange(start, start.plusMinutes(minuteCount));
    }
}
