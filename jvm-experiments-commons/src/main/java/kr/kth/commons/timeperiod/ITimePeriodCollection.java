package kr.kth.commons.timeperiod;

import org.joda.time.DateTime;

/**
 * {@link ITimePeriod} 컬렉션입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
public interface ITimePeriodCollection extends ITimePeriodContainer {

    boolean hasInsidePeriods(final ITimePeriod target);

    boolean hasOverlapPeriods(final ITimePeriod target);

    boolean hasIntersectionPeriods(final DateTime moment);

    boolean hasIntersectionPeriods(final ITimePeriod target);

    Iterable<ITimePeriod> insidePeriods(final ITimePeriod target);

    Iterable<ITimePeriod> overlapPeriods(final ITimePeriod target);

    Iterable<ITimePeriod> intersectionPeriods(final DateTime moment);

    Iterable<ITimePeriod> intersectionPeriods(final ITimePeriod target);

    Iterable<ITimePeriod> relationPeriods(final ITimePeriod target, PeriodRelation relation);

    Iterable<ITimePeriod> relationPeriods(final ITimePeriod target, PeriodRelation... relations);
}
