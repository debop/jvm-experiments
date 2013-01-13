package kr.kth.timeperiod;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.Locale;

/**
 * 문화권에 따른 날짜 표현, 날짜 계산 등을 제공하는 Calendar 입니다. (ISO 8601, Korean 등)
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 19.
 */
public interface ITimeCalendar extends ITimePeriodMapper {

    Locale getLocale();

    YearKind getYearKind();

    /**
     * 시작일 오프셋
     */
    Duration getStartOffset();

    /**
     * 종료일 오프셋
     */
    Duration getEndOffset();

    /**
     * 년의 기준 월
     */
    int getBaseMonthOfYear();

    WeekOfYearRuleKind getWeekOfYearRule();

    // .NET CalendarWeekRule 구현 필요

    /**
     * 한주의 시작 요일 (1: 월요일 ... 6:토요일, 7:일요일)
     */
    DayOfWeek getFirstDayOfWeek();

    int getYear(DateTime time);

    /**
     * 월을 구합니다.
     */
    int getMonth(DateTime time);

    /**
     * 해당 월의 일를 구합니다.
     */
    int getDayOfMonth(DateTime time);

    /**
     * 요일을 구합니다.
     */
    int getDayOfWeek(DateTime time);

    int getHour(DateTime time);

    int getMinute(DateTime time);

    /**
     * 해당 년/월의 일자 수 (28~31)
     */
    int getDaysInMonth(int year, int month);

    /**
     * 년도 이름
     */
    String getYearName(int year);

    /**
     * 반기를 표현하는 문자열을 반환합니다.
     */
    String getHalfYearName(HalfYearKind halfyear);

    /**
     * 지정한 년도와 반기를 표현하는 문자열을 반환합니다.
     */
    String getHalfYearOfYearName(int year, HalfYearKind halfyear);

    /**
     * 분기를 표현하는 문자열을 반환합니다.
     */
    String getQuarterName(QuarterKind quarter);

    /**
     * 특정년도의 분기를 표현하는 문자열을 반환합니다. (2013년 1사분기)
     */
    String getQuarterOfYearName(int year, QuarterKind quarter);

    /**
     * 월을 문자열로
     */
    String getMonthName(int month);

    /**
     * 년/월을 문자열로 표현합니다.
     */
    String getMonthOfYearName(int year, int month);

    /**
     * 년도와 주차를 문자열로 표현합니다.
     */
    String getWeekOfYearName(int year, int weekOfYear);

    /**
     * 요일을 문자열로 표현합니다.
     */
    String getDayName(int dayOfWeek);

    /**
     * 해당 일자의 주차를 구합니다. (주차계산 규칙에 따라 달라집니다)
     */
    int getWeekOfYear(DateTime time);

    /**
     * 해당 년도와 주차의 첫번째 일자를 반환합니다. (주차계산 규칙에 따라 달라집니다. 일요일|월요일 등)
     */
    DateTime getStartOfYearWeek(int year, int weekOfYear);
}
