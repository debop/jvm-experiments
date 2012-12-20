package kr.kth.commons.timeperiod;

import org.joda.time.DateTime;

/**
 * Time Block 을 표현하는 인터페이스입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
public interface ITimeBlock extends ITimePeriod {

	void setStart(DateTime start);

	void setEnd(DateTime end);

	void setDuration(long duration);

	void setup(DateTime start, long duration);

	void durationFromStart(long duration);

	void durationFromEnd(long duration);

	ITimeBlock getPreviousBlock(long offset);

	ITimeBlock getNextBlock(long offset);
}
