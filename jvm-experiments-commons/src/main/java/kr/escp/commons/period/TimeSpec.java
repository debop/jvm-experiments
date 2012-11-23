package kr.escp.commons.period;

import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 시간과 관련된 단위 상수에 해당하는 정보를 제공합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
@Slf4j
public final class TimeSpec {

	private TimeSpec() {}

	public static final long TicksPerMillisecond = 10000;
	public static final long TicksPerSecond = TicksPerMillisecond * 1000;
	public static final long TicksPerMinute = TicksPerSecond * 60;
	public static final long TicksPerHour = TicksPerMinute * 60;
	public static final long TicksPerDay = TicksPerHour * 24;

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

	public static final long MinTicks = 0;
	public static final long MaxTicks = DaysTo10000 * TicksPerDay - 1;
	public static final long MaxMillis = (long) DaysTo10000 * MillisPerDay;


	public static final Date MinPeriodTime = new Date(MinTicks);
	public static final Date MaxPeriodTime = new Date(MaxTicks);

	public static final Timestamp ZeroTimestamp = new Timestamp(0);
}
