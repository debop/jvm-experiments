package kr.kth.commons.timeperiod;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 시간 간격을 나타냅니다.
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

	/**
	 * 시작시각이 폐구간인지 여부
	 *
	 * @return 시작시각이 폐구간이면 true, 개구간이면 false를 반환
	 */
	boolean isStartClosed();

	/**
	 * 완료시각이 폐구간인지 여부
	 *
	 * @return 완료시각이 폐구간이면 true, 개구간이면 false를 반환
	 */
	boolean isEndClosed();

	/**
	 * 기간이 폐구간인지 여부
	 */
	boolean isClosed();

	/**
	 * 기간이 비었는가 여부 (시작시각과 완료시각이 같으면 기간은 비었다)
	 */
	boolean isEmpty();

	/**
	 * 기간(Interval)로 쓸 수 없는 경우 (isMoment() 가 true 면서, isClosed() 도 true인 경우)
	 */
	boolean isDegenerate();

	/**
	 * 사용가능한 시간간격인가?
	 */
	boolean isInetervalEnabled();

	/**
	 * 시작 시각
	 */
	Date getStartInterval();

	/**
	 * 시작 시각을 설정합니다.
	 */
	void setStartInterval(Date start);

	/**
	 * 완료 시각
	 */
	Date getEndInterval();

	/**
	 * 완료 시각을 설정합니다.
	 */
	void setEndInterval(Date end);

	/**
	 * 시작 시각의 개/폐구간 종류
	 */
	IntervalEdge getStartEdge();

	/**
	 * 시작 시각의 걔/페구간 종류를 설정합니다.
	 */
	void setStartEdge(IntervalEdge edge);

	/**
	 * 완료 시각의 개/폐구간 종류
	 */
	IntervalEdge getEndEdge();

	/**
	 * 완료 시각의 개/폐구간 종류를 설정합니다.
	 */
	void setEndEdge(IntervalEdge edge);

	void expandStartTo(Date moment);

	void expandEndTo(Date moment);

	void expandTo(Date moment);

	void expandTo(ITimePeriod period);

	void shrinkStartTo(Date moment);

	void shrinkEndTo(Date moment);

	void shrinkTo(Date moment);

	void shrinkTo(ITimePeriod period);

	@Override
	ITimeInterval copy(Timestamp offset);
}
