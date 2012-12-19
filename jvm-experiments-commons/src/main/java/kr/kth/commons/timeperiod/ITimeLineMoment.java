package kr.kth.commons.timeperiod;

import java.util.Date;

/**
 * kr.kth.commons.timeperiod.ITimeLineMoment
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 19.
 */
public interface ITimeLineMoment {

	Date getMoment();

	ITimePeriodCollection getPeriods();

	int getStartCount();

	int getEndCount();
}
