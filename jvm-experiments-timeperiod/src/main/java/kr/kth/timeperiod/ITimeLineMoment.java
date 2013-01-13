package kr.kth.timeperiod;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * {@link ITimePeriod}의 컬렉션을 제공하는 {@link #getPeriods()} 을 제공하고,
 * {@link #getMoment()}를 기준으로 선행기간의 수와 후행기간의 수를 파악합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 19.
 */
public interface ITimeLineMoment extends Serializable {

    /**
     * 기준 시각
     */
    DateTime getMoment();

    /**
     * 기간의 컬렉션을 반환합니다.
     */
    ITimePeriodCollection getPeriods();

    /**
     * 시작 시각 갯수
     */
    int getStartCount();

    /**
     * 종료 시각 갯수
     */
    int getEndCount();
}
