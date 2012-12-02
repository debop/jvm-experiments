package kr.kth.commons.period;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * {@link Date} 의 집합을 나타내는 인터페이스
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 2.
 */
public interface DateSet extends Collection<Date> {

	/**
	 * 지정된 인텍스에 해당하는 요소를 반환합니다.
	 */
	Date get(int index);

	/**
	 * 요소 중 최소값을 반환한다. 없다면 null을 반환한다.
	 */
	Date getMin();

	/**
	 * 요소 중 최대 값을 반환합니다. 없다면 null을 반환한다.
	 */
	Date getMax();

	/**
	 * {@link #getMax()} - {@link #getMin()} 값을 나타낸다. 둘 중 하나라도 null이면, null을 반환한다.
	 */
	Timestamp getDuration();

	/**
	 * 요소가 없는 컬렉션인가?
	 */
	boolean isEmpty();

	/**
	 * 모둔 요소가 같은 값을 가졌는가?
	 */
	boolean isMoment();

	/**
	 * 요소가 모든 시각을 나타내는가? 최소값, 최소값 모두 null일 때 any time 입니다.
	 */
	boolean isAnyTime();

	/**
	 * 지정한 컬렉션의 요소들을 모두 추가합니다.
	 *
	 * @param moments
	 */
	boolean addAll(Collection<? extends Date> moments);

	/**
	 * 지정한 인덱스와 갯수에 해당하는 시각들의 Duration을 구합니다.
	 */
	List<Timestamp> getDurations(int startIndex, int count);

	/**
	 * 지정된 시각 바로 전 시각을 찾습니다. 없으면 null을 반환합니다.
	 */
	Date findPrevious(Date moment);

	/**
	 * 지정된 시각 바로 다음 시각을 찾습니다. 없으면 null을 반환합니다.
	 */
	Date findNext(Date moment);
}
