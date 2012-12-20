package kr.kth.commons.timeperiod;

import org.joda.time.DateTime;

/**
 * 설명을 추가하세요.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
public interface ITimeRange extends ITimePeriod {

	/**
	 * 시작시각을 설정합니다.
	 *
	 * @param start
	 */
	void setStart(DateTime start);

	/**
	 * 완료시각을 설정합니다.
	 *
	 * @param end
	 */
	void setEnd(DateTime end);

	/**
	 * 기간을 설정합니다.
	 *
	 * @param duration
	 */
	void setDuration(long duration);

	/**
	 * 시작시각을 지정된 시각으로 변경합니다. 기존 시작시각보다 작은 값 (과거) 이여야 합니다.
	 *
	 * @param moment
	 */
	void expandStartTo(DateTime moment);

	/**
	 * 완료시각을 지정된 시각으로 변경합니다. 기존 완료시각보다 큰 값 (미래) 이어야 합니다.
	 *
	 * @param moment
	 */
	void expandEndTo(DateTime moment);

	/**
	 * 시작시각과 완료시각을 지정된 시각으로 설정합니다.
	 *
	 * @param moment
	 */
	void expandTo(DateTime moment);

	/**
	 * 시작시각과 완료시각을 지정된 기간 정보를 기준으로 변경합니다.
	 *
	 * @param period
	 */
	void expandTo(ITimePeriod period);

	/**
	 * 시작시각을 지정된 시각으로 변경합합니다. 기존 시작시각보다 큰 값(미래) 이어야 합니다.
	 *
	 * @param moment
	 */
	void shrinkStartTo(DateTime moment);

	/**
	 * 완료시각을 지정된 시각으로 변경합니다. 기존 완료시각보다 작은 값(과거) 이어야 하고, 시작시각보다는 같거나 커야 합니다.
	 *
	 * @param moment
	 */
	void shrinkEndTo(DateTime moment);

	/**
	 * 기간을 지정된 기간으로 축소시킵니다.
	 *
	 * @param period
	 */
	void shrinkTo(ITimePeriod period);
}
