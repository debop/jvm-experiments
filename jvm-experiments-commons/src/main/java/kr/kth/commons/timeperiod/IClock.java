package kr.kth.commons.timeperiod;

import java.util.Date;

/**
 * kr.kth.commons.timeperiod.IClock
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 19.
 */
public interface IClock {

	/**
	 * 현재 시각
	 */
	Date getNow();

	/**
	 * 오늘 (현재 시각의 날짜부분만)
	 */
	Date getToday();

	/**
	 * 현재 시각의 시간 부분만
	 */
	long getTimeOfDay();
}
