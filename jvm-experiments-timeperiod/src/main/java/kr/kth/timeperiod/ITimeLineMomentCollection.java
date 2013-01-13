package kr.kth.timeperiod;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * {@link ITimePeriod}의 시작시각 또는 완료시각을 키로 가지고, {@link ITimePeriod} 를 Value로 가지는
 * {@link org.apache.commons.collections.MultiMap[Date, ITimePeriod]}을 생성합니다. 단 시각으로 정렬됩니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 19.
 */
public interface ITimeLineMomentCollection extends Iterable<ITimeLineMoment>, Serializable {

    /**
     * 컬렉션 크기
     */
    int size();

    /**
     * 빈 컬렉션인가?
     */
    boolean isEmpty();

    ITimeLineMoment getMin();

    ITimeLineMoment getMax();

    ITimeLineMoment get(int index);

    void add(ITimePeriod period);

    void addAll(Iterable<? extends ITimePeriod> periods);

    void remove(ITimePeriod period);

    ITimeLineMoment find(DateTime moment);

    boolean contains(DateTime moment);
}
