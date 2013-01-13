package kr.kth.timeperiod.timerange;

import com.google.common.collect.Lists;
import kr.kth.commons.Guard;
import kr.kth.timeperiod.ITimeCalendar;
import kr.kth.timeperiod.ITimeFormatter;
import kr.kth.timeperiod.TimeCalendar;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.util.List;

/**
 * 일분 단위의 {@link MinuteRange}의 연속된 컬렉션
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 25.
 */
@Slf4j
public class MinuteRangeCollection extends MinuteTimeRange {

    private static final long serialVersionUID = 3798232473967314158L;

    public MinuteRangeCollection(DateTime moment, int minuteCount) {
        this(moment, minuteCount, TimeCalendar.Default());
    }

    public MinuteRangeCollection(DateTime moment, int minuteCount, ITimeCalendar calendar) {
        super(moment, minuteCount, calendar);
    }

    public MinuteRangeCollection(int year, int month, int day, int hour, int minute, int minuteCount) {
        this(year, month, day, hour, minute, minuteCount, TimeCalendar.Default());
    }

    public MinuteRangeCollection(int year, int month, int day, int hour, int minute, int minuteCount, ITimeCalendar calendar) {
        super(year, month, day, hour, minute, minuteCount, calendar);
    }

    public List<MinuteRange> getMinutes() {

        DateTime startMinute = getStart().withHourOfDay(getStartHour()).withMinuteOfHour(getStartMinute());
        List<MinuteRange> minuteRanges = Lists.newArrayListWithCapacity(getMinuteCount());

        if (log.isDebugEnabled())
            log.debug("분단위 기간을 나타내는 MinuteRange 컬렉션을 빌드합니다. startMinute=[{}], minuteCount=[{}]",
                    startMinute, getMinuteCount());

        for (int i = 0; i < getMinuteCount(); i++) {
            minuteRanges.add(new MinuteRange(startMinute.plusMinutes(i), getTimeCalendar()));
        }

        return minuteRanges;
    }

    @Override
    protected String format(ITimeFormatter formatter) {
        ITimeFormatter fmt = Guard.firstNotNull(formatter, ITimeFormatter.getInstance());
        return fmt.getCalendarPeriod(fmt.getShortDate(getStart()),
                fmt.getShortDate(getEnd()),
                fmt.getShortTime(getStart()),
                fmt.getShortTime(getEnd()),
                getDuration());
    }
}
