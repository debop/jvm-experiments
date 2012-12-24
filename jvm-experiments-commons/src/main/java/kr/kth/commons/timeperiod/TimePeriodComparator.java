package kr.kth.commons.timeperiod;

import java.util.Comparator;

/**
 * {@link ITimePeriod} 수형에 대한 비교자 (Comparator) 입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 24.
 */
public class TimePeriodComparator implements Comparator<ITimePeriod> {

	/**
	 * {@link ITimePeriod} 수형에 대한 기본 비교자
	 */
	public static final TimePeriodComparator Default = new TimePeriodComparator();

	@Override
	public int compare(ITimePeriod o1, ITimePeriod o2) {
		return o1.getStart().compareTo(o2.getStart());
	}
}
