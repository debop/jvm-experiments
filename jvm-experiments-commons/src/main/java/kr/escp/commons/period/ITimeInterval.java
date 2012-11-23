package kr.escp.commons.period;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 설명을 추가하세요.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
public interface ITimeInterval extends ITimePeriod {

	/**
	 * 시작 시각이 개구간인지 여부
	 */
	boolean isStartOpen();

	/**
	 * 완료 시각이 개구간인지 여부
	 */
	boolean isEndOpen();

	boolean isStartClosed();

	boolean isEndClosed();

	boolean isClosed();

	boolean isEmpty();

	boolean isDegenerate();

	boolean isInetervalEnabled();

	Date getStartInterval();

	void setStartInterval(Date start);

	Date getEndInterval();

	void setEndInterval(Date end);

	IntervalEdge getStartEdge();

	void setStartEdge(IntervalEdge edge);

	IntervalEdge getEndEdge();

	void setEndEdge(IntervalEdge edge);

	void expandStartTo(Date moment);

	void expandEndTo(Date moment);

	void shrinkStartTo(Date moment);

	void shrinkEndTo(Date moment);

	void shrinkTo(ITimePeriod period);

	@Override
	ITimeInterval copy(Timestamp offset);
}
