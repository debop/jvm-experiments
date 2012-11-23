package kr.escp.commons.period;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 설명을 추가하세요.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
public interface ITimeRange extends ITimePeriod {

	void setStart(Date start);

	void setEnd(Date end);

	void setDuration(Timestamp duration);

	void expandStartTo(Date moment);

	void expandEndTo(Date moment);

	void expandTo(Date moment);

	void expandTo(ITimePeriod period);

	void shrinkStartTo(Date moment);

	void shrinkEndTo(Date moment);

	void shrinkTo(ITimePeriod period);
}
