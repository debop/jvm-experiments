package kr.kth.commons.timeperiod;

import java.util.Date;

/**
 * {@link ITimePeriod}의 시작시각 또는 완료시각을 키로 가지고, {@link ITimePeriod} 를 Value로 가지는
 * {@link org.apache.commons.collections.MultiMap[Date, ITimePeriod]}을 생성합니다. 단 시각으로 정렬됩니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 19.
 */
public interface ITimeLineMomentCollection {

	int getCount();

	boolean isEmpty();

	ITimeLineMoment getMin();

	ITimeLineMoment getMax();

	ITimeLineMoment get(int index);

	void Add(ITimePeriod period);

	void AddAll(Iterable<? extends ITimePeriod> periods);

	void Remove(ITimePeriod period);

	ITimeLineMoment find(Date moment);

	boolean contains(Date moment);
}
