package kr.kth.commons.period;

/**
 * {@link ITimePeriod} 컬렉션을 연산할 수 있는 인터페이스입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
public interface ITimeLine {

	ITimePeriodContainer getPeriods();

	ITimePeriod getLimits();

	ITimePeriodMapper getPriodMapper();

	ITimePeriodCollection combinePeriods();

	ITimePeriodCollection intersectPeriods();

	ITimePeriodCollection calculateGaps();
}
