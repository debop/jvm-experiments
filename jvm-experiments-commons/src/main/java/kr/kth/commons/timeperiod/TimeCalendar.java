package kr.kth.commons.timeperiod;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.util.Locale;

/**
 * kr.kth.commons.timeperiod.TimeCalendar
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 23.
 */
@Slf4j
public class TimeCalendar implements ITimeCalendar {

	private static final long serialVersionUID = -7246039577107197119L;

	public static final TimeCalendar Default = new TimeCalendar();


	@Override
	public Locale getLocale() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public YearKind getYearKind() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public long getStartOffset() {
		return 0;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public long getEndOffset() {
		return 0;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public int getBaseMonthOfYear() {
		return 0;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public WeekOfYearRuleKind getWeekOfYearRule() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public int getFirstDayOfWeek() {
		return 0;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public int getYear(DateTime time) {
		return 0;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public int getMonth(DateTime time) {
		return 0;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public int getDayOfMonth(DateTime time) {
		return 0;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public int getDayOfWeek(DateTime time) {
		return 0;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public int getHour(DateTime time) {
		return 0;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public int getMinute(DateTime time) {
		return 0;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public int getDaysInMonth(int year, int month) {
		return 0;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String getYearName(int year) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String getHalfYearName(HalfYearKind halfyear) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String getHalfYearOfYearName(int year, HalfYearKind halfyear) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String getQuarterName(QuarterKind quarter) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String getQuarterOfYearName(int year, QuarterKind quarter) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String getMonthOfYearName(int year, int month) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String getWeekOfYearName(int year, int weekOfYear) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String getDayName(int dayOfWeek) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public int getWeekOfYear(DateTime time) {
		return 0;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public DateTime getStartOfYearWeek(int year, int weekOfYear) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public DateTime mapStart(DateTime moment) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public DateTime mapEnd(DateTime moment) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public DateTime unmapStart(DateTime moment) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public DateTime unmapEnd(DateTime moment) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}
}
