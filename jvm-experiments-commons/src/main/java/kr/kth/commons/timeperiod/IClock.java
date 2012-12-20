package kr.kth.commons.timeperiod;

import org.joda.time.DateTime;

/**
 * kr.kth.commons.timeperiod.IClock
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 19.
 */
public interface IClock {

	/**
	 * 현재 시각
	 */
	DateTime getNow();

	/**
	 * 오늘 (현재 시각의 날짜부분만)
	 */
	DateTime getToday();

	/**
	 * 현재 시각의 시간 부분만
	 */
	long getTimeOfDay();
}
