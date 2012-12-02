package kr.kth.commons.period;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Time Block 을 표현하는 인터페이스입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
public interface ITimeBlock extends ITimePeriod {

	void setStart(Date start);

	void setEnd(Date end);

	void setDuration(Timestamp duration);

	void setup(Date start, Timestamp duration);

	void durationFromStart(Timestamp duration);

	void durationFromEnd(Timestamp duration);

	ITimeBlock getPreviousBlock(Timestamp offset);

	ITimeBlock getNextBlock(Timestamp offset);
}
