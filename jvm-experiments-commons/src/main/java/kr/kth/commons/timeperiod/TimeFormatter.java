package kr.kth.commons.timeperiod;

import org.joda.time.DateTime;

import java.util.Locale;

/**
 * kr.kth.commons.timeperiod.TimeFormatter
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 23.
 */
public class TimeFormatter implements ITimeFormatter {

	private static ITimeFormatter instance = new TimeFormatter();
	private static final Object syncLock = new Object();

	// region << Constructors >>


	// endregion

	public static ITimeFormatter getInstance() {
		return instance;
	}

	public static void setInstance(ITimeFormatter formatter) {
		if (formatter == null) return;

		synchronized (syncLock) {
			instance = formatter;
		}
	}

	@Override
	public Locale getLocale() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String getListSeparator() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String getContextSeparator() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String getStartEndSeparator() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String getDurationSeparator() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String getDateFormat() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String getShortDateFormat() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String getLongDateFormat() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String getShortTimeFormat() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String getLongTimeFormat() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public DurationFormatKind getDurationKind() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public boolean isUseDurationSeconds() {
		return false;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String getCollection(int count) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String getCollectionPeriod(int count, DateTime start, DateTime end, long duration) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String getDate(DateTime date) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String getShortDate(DateTime date) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String getLongDate(DateTime date) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String getTime(DateTime date) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String getShortTime(DateTime date) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String getLongTime(DateTime date) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String getDuration(long duration) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String getDuration(long duration, DurationFormatKind durationFormatKind) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String getPeriod(DateTime start, DateTime end, long duration) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String getInterval(DateTime start, DateTime end, IntervalEdge startEdge, IntervalEdge endEdge, long duration) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String getCalendarPeriod(String start, String end, long duration) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String getCalendarPeriod(String context, String start, String end, long duration) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String getCalendarPeriod(String startContext, String endContext, String start, String end, long duration) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}
}
