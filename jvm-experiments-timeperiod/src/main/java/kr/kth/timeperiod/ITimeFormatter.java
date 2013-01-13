package kr.kth.timeperiod;

import org.joda.time.DateTime;

import java.util.Locale;

/**
 * 시간 정보를 여러가지 문자 포맷으로 제공하기 위한 Formatter 입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 19.
 */
public interface ITimeFormatter {

    Locale getLocale();

    String getListSeparator();

    String getContextSeparator();

    String getStartEndSeparator();

    String getDurationSeparator();

    String getDateFormat();

    String getShortDateFormat();

    String getLongDateFormat();

    String getShortTimeFormat();

    String getLongTimeFormat();

    DurationFormatKind getDurationKind();

    boolean isUseDurationSeconds();

    String getCollection(int count);

    String getCollectionPeriod(int count, DateTime start, DateTime end, long duration);

    String getDate(DateTime date);

    String getShortDate(DateTime date);

    String getLongDate(DateTime date);

    String getTime(DateTime date);

    String getShortTime(DateTime date);

    String getLongTime(DateTime date);

    String getDuration(long duration);

    String getDuration(long duration, DurationFormatKind durationFormatKind);

    String getPeriod(DateTime start, DateTime end, long duration);


    String getInterval(DateTime start, DateTime end, IntervalEdge startEdge, IntervalEdge endEdge, long duration);

    String getCalendarPeriod(String start, String end, long duration);

    String getCalendarPeriod(String context, String start, String end, long duration);

    String getCalendarPeriod(String startContext, String endContext, String start, String end, long duration);


}
