package kr.kth.commons.timeperiod;

import java.io.Serializable;

/**
 * {@link ITimePeriod} 컬렉션을 연산할 수 있는 인터페이스입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
public interface ITimeLine extends Serializable {

    ITimePeriodContainer getPeriods();

    ITimePeriod getLimits();

    ITimePeriodMapper getPeriodMapper();

    ITimePeriodCollection combinePeriods();

    ITimePeriodCollection intersectPeriods();

    ITimePeriodCollection calculateGaps();
}
