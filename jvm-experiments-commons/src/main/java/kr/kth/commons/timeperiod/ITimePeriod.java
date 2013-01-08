package kr.kth.commons.timeperiod;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * 시간 간격을 표현합니다. 시작~완료 구간과 기간을 표현하는 인터페이스입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
public interface ITimePeriod extends Comparable<ITimePeriod>, Serializable {

    DateTime getStart();

    DateTime getEnd();

    Long getDuration();

    String getDurationDescription();

    boolean hasStart();

    boolean hasEnd();

    boolean hasPeriod();

    boolean isMoment();

    boolean isAnyTime();

    boolean isReadonly();

    void setup(DateTime start, DateTime end);

    ITimePeriod copy(long offset);

    void move(long offset);

    boolean isSamePeriod(ITimePeriod that);

    boolean hasInside(DateTime moment);

    boolean hasInside(ITimePeriod that);

    boolean intersectsWith(ITimePeriod that);

    boolean overlapsWith(ITimePeriod that);

    void reset();

    PeriodRelation getRelation(ITimePeriod that);

    String getDescription(ITimeFormatter formatter);

    ITimePeriod getIntersection(ITimePeriod that);

    ITimePeriod getUnion(ITimePeriod that);
}
