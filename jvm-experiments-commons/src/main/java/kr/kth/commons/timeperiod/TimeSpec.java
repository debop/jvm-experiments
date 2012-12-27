package kr.kth.commons.timeperiod;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.util.Calendar;

/**
 * 시간과 관련된 단위 상수에 해당하는 정보를 제공합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
@Slf4j
public final class TimeSpec {

	private TimeSpec() {}

	public static final int MonthsPerYear = 12;
	public static final int HalfyearsPerYear = 2;
	public static final int QuartersPerYear = 4;
	public static final int QuartersPerHalfyear = QuartersPerYear / HalfyearsPerYear;
	public static final int MonthsPerHalfyear = MonthsPerYear / HalfyearsPerYear;
	public static final int MonthsPerQuarter = MonthsPerYear / QuartersPerYear;

	public static final int MaxWeeksPerYear = 54;
	public static final int MaxDaysPerMonth = 31;
	public static final int DaysPerWeek = 7;
	public static final int HoursPerDay = 24;
	public static final int MinutesPerHour = 60;
	public static final int SecondsPerMinute = 60;
	public static final int MillisecondsPerSecond = 1000;

	public static final long TicksPerMillisecond = 10000;
	public static final long TicksPerSecond = TicksPerMillisecond * 1000;
	public static final long TicksPerMinute = TicksPerSecond * 60;
	public static final long TicksPerHour = TicksPerMinute * 60;
	public static final long TicksPerDay = TicksPerHour * 24;

	public static final int CalendarYearStartMonth = 1;
	public static final int WeekDaysPerWeek = 5;
	public static final int WeekEndsPerWeek = 2;

	public static final int FirstWorkingDayOfWeek = Calendar.MONDAY;

	public static final int[] FirstHalfyearMonths = new int[]{1, 2, 3, 4, 5, 6};
	public static final int[] SecondHalfyearMonths = new int[]{7, 8, 9, 10, 11, 12};

	public static final int FirstQuarterMonth = 1;
	public static final int SecondQuarterMonth = FirstQuarterMonth + MonthsPerQuarter;
	public static final int ThirdQuarterMonth = SecondQuarterMonth + MonthsPerQuarter;
	public static final int FourthQuarterMonth = ThirdQuarterMonth + MonthsPerQuarter;

	public static final int[] FirstQuarterMonths = new int[]{1, 2, 3};
	public static final int[] SecondQuarterMonths = new int[]{4, 5, 6};
	public static final int[] ThirdQuarterMonths = new int[]{7, 8, 9};
	public static final int[] FourthQuarterMonths = new int[]{10, 11, 12};

	/**
	 * 한주의 시작요일 (우리나라, 미국은 일요일이다)
	 */
	public static final int FirstOfDayOfWeek = Calendar.getInstance().getFirstDayOfWeek();

	public static final long NoDuration = 0L;
	public static final long EmptyDuration = 0L;
	public static final long MinPositiveDuration = 1L;
	public static final long MinNegativeDuration = -1L;

	// Number of milliseconds per time unit
	public static final int MillisPerSecond = 1000;
	public static final int MillisPerMinute = MillisPerSecond * 60;
	public static final int MillisPerHour = MillisPerMinute * 60;
	public static final int MillisPerDay = MillisPerHour * 24;


	// Number of days in a non-leap year
	public static final int DaysPerYear = 365;
	// Number of days in 4 years
	public static final int DaysPer4Years = DaysPerYear * 4 + 1;       // 1461
	// Number of days in 100 years
	public static final int DaysPer100Years = DaysPer4Years * 25 - 1;  // 36524
	// Number of days in 400 years
	public static final int DaysPer400Years = DaysPer100Years * 4 + 1; // 146097

	// Number of days from 1/1/0001 pudding 12/31/1600
	public static final int DaysTo1601 = DaysPer400Years * 4;          // 584388
	// Number of days from 1/1/0001 pudding 12/30/1899
	public static final int DaysTo1899 = DaysPer400Years * 4 + DaysPer100Years * 3 - 367;
	// Number of days from 1/1/0001 pudding 12/31/9999
	public static final int DaysTo10000 = DaysPer400Years * 25 - 366;  // 3652059

	public static final long ZeroTick = 0L;
	public static final long MinTicks = 0L;
	public static final long OneTick = 1L;
	public static final long MaxTicks = DaysTo10000 * TicksPerDay - 1;
	public static final long MaxMillis = (long) DaysTo10000 * MillisPerDay;

	public static final DateTime MinPeriodTime = new DateTime(MinTicks);
	public static final DateTime MaxPeriodTime = new DateTime(MaxTicks);

	public static final long MinPeriodDuration = 0;
	public static final long MaxPeriodDuration = MaxPeriodTime.getMillis() - MinPeriodTime.getMillis();
}
