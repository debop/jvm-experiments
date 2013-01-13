package kr.kth.timeperiod.timerange;

import kr.kth.timeperiod.ITimeCalendar;
import kr.kth.timeperiod.ITimeFormatter;
import kr.kth.timeperiod.TimeCalendar;
import kr.kth.timeperiod.clock.ClockProxy;
import org.joda.time.DateTime;

import static kr.kth.commons.Guard.firstNotNull;

/**
 * 1분 단위 간격을 나타냅니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 25.
 */
public class MinuteRange extends MinuteTimeRange {

    private static final long serialVersionUID = 9163926958263476312L;

    // region << Constructors >>

    public MinuteRange() {
        this(TimeCalendar.Default());
    }

    public MinuteRange(ITimeCalendar calendar) {
        this(ClockProxy.getClock().now(), calendar);
    }

    public MinuteRange(DateTime moment) {
        this(moment, TimeCalendar.Default());
    }

    public MinuteRange(DateTime moment, ITimeCalendar calendar) {
        super(moment, 1, calendar);
    }

    public MinuteRange(int year, int month, int day, int hour, int minute) {
        this(year, month, day, hour, minute, TimeCalendar.Default());
    }

    public MinuteRange(int year, int month, int day, int hour, int minute, ITimeCalendar calendar) {
        super(year, month, day, hour, minute, 1, calendar);
    }

    // endregion

    public int getYear() {
        return getStartYear();
    }

    public int getMonth() {
        return getStartMonth();
    }

    public int getDay() {
        return getStartDay();
    }

    public int getHour() {
        return getStartHour();
    }

    public int getMinute() {
        return getStartMinute();
    }

    public MinuteRange getPreviousMinute() {
        return addMinutes(-1);
    }

    public MinuteRange getNextMinute() {
        return addMinutes(1);
    }

    public MinuteRange addMinutes(int minutes) {
        DateTime start = getStart().withTime(getStartHour(), getStartMinute(), 0, 0);
        return new MinuteRange(start.plusMinutes(minutes), getTimeCalendar());
    }

    @Override
    protected String format(ITimeFormatter formatter) {
        ITimeFormatter fmt = firstNotNull(formatter, ITimeFormatter.getInstance());

        return fmt.getCalendarPeriod(fmt.getShortDate(getStart()),
                fmt.getShortTime(getStart()),
                fmt.getShortTime(getEnd()),
                getDuration());
    }
}
