package kr.kth.commons.timeperiod;

import org.joda.time.DateTimeConstants;

/**
 * kr.kth.commons.timeperiod.DayOfWeek
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 27.
 */
public enum DayOfWeek {

    Monday(DateTimeConstants.MONDAY),
    Tuesday(DateTimeConstants.TUESDAY),
    Wednesday(DateTimeConstants.WEDNESDAY),
    Thursday(DateTimeConstants.THURSDAY),
    Friday(DateTimeConstants.FRIDAY),
    Saturday(DateTimeConstants.SATURDAY),
    Sunday(DateTimeConstants.SUNDAY);


    private final int day;

    DayOfWeek(int day) {
        this.day = day;
    }

    public int toInt() {
        return day;
    }

    public static DayOfWeek valueOf(int dayOfWeek) {

        int day = (dayOfWeek % TimeSpec.DaysPerWeek) + 1;

        switch (day) {
            case DateTimeConstants.MONDAY:
                return DayOfWeek.Monday;

            case DateTimeConstants.TUESDAY:
                return DayOfWeek.Tuesday;

            case DateTimeConstants.WEDNESDAY:
                return DayOfWeek.Wednesday;

            case DateTimeConstants.THURSDAY:
                return DayOfWeek.Thursday;

            case DateTimeConstants.FRIDAY:
                return DayOfWeek.Friday;

            case DateTimeConstants.SATURDAY:
                return DayOfWeek.Saturday;

            case DateTimeConstants.SUNDAY:
                return DayOfWeek.Sunday;

            default:
                throw new IllegalArgumentException("dayOfWeek 값이 잘못되었습니다. dayOfWeek=" + dayOfWeek);
        }
    }
}
