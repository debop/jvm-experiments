package kr.kth.commons.timeperiod;

import org.joda.time.DateTime;

/**
 * kr.kth.commons.timeperiod.ITimeLineMoment
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 19.
 */
public interface ITimeLineMoment {

	DateTime getMoment();

	ITimePeriodCollection getPeriods();

	int getStartCount();

	int getEndCount();
}
