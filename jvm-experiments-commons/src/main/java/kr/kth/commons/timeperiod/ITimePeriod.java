package kr.kth.commons.timeperiod;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 시간 간격을 표현합니다. 시작~완료 구간과 기간을 표현하는 인터페이스입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
public interface ITimePeriod extends Comparable<ITimePeriod>, Serializable {

	Date getStart();

	Date getEnd();

	Long getDuration();

	void setDuration(Long duration);

	String getDurationDescription();

	boolean hasStart();

	boolean hasEnd();

	boolean hasPeriod();

	boolean isMoment();

	boolean isAnyTime();

	boolean isReadonly();

	void setup(Date start, Date end);

	ITimePeriod copy();

	ITimePeriod copy(Timestamp offset);

	void move(Timestamp offset);

	void isSamePeriod(ITimePeriod that);

	boolean hasInside(Date moment);

	boolean overlapsWith(ITimePeriod that);

	void reset();

	PeriodRelation getRelation(ITimePeriod that);

	ITimePeriod getIntersection(ITimePeriod that);

	ITimePeriod getUnion(ITimePeriod that);
}
