package kr.kth.commons.period;

import java.util.Date;
import java.util.List;

/**
 * 설명을 추가하세요.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
public interface ITimePeriodContainer extends List<ITimePeriod>, ITimePeriod {

	void setStart(Date start);

	void setEnd(Date end);

	boolean containsPeriod(ITimePeriod target);

	void addAll(Iterable<ITimePeriod> periods);

	void sortByStart(boolean ascending);
}
