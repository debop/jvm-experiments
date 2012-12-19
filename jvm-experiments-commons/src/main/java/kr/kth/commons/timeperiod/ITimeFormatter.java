package kr.kth.commons.timeperiod;

import java.util.Date;
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

	String getCollectionPeriod(int count, Date start, Date end, long duration);

	String getDate(Date date);

	String getShortDate(Date date);

	String getLongDate(Date date);

	String getTime(Date date);

	String getShortTime(Date date);

	String getLongTime(Date date);

	String getDuration(long duration);

	String getDuration(long duration, DurationFormatKind durationFormatKind);

	String getPeriod(Date start, Date end, long duration);


	String getInterval(Date start, Date end, IntervalEdge startEdge, IntervalEdge endEdge, long duration);

	String getCalendarPeriod(String start, String end, long duration);

	String getCalendarPeriod(String context, String start, String end, long duration);

	String getCalendarPeriod(String startContext, String endContext, String start, String end, long duration);


}
