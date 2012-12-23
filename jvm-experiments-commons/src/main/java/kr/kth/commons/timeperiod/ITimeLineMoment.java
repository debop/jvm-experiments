package kr.kth.commons.timeperiod;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * {@link ITimePeriod}의 컬렉션인
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 19.
 */
public interface ITimeLineMoment extends Serializable {

	DateTime getMoment();

	ITimePeriodCollection getPeriods();

	int getStartCount();

	int getEndCount();
}
