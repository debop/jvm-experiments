package kr.kth.commons.period;

import java.util.Date;

/**
 * 설명을 추가하세요.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
public interface ITimePeriodCollection extends ITimePeriodContainer {

	boolean hasInsidePeriods(ITimePeriod period);

	boolean hasOverlapPeriods(ITimePeriod period);

	boolean hasIntersectionPeriods(Date moment);

	boolean hasIntersectionPeriods(ITimePeriod period);

	Iterable<ITimePeriod> insidePeriods(ITimePeriod period);

	Iterable<ITimePeriod> overlapPeriods(ITimePeriod period);

	Iterable<ITimePeriod> intersectionPeriods(Date moment);

	Iterable<ITimePeriod> intersectionPeriods(ITimePeriod period);

	Iterable<ITimePeriod> relationPeriods(ITimePeriod period, PeriodRelation relation);

	Iterable<ITimePeriod> relationPeriods(ITimePeriod period, PeriodRelation... relations);
}
