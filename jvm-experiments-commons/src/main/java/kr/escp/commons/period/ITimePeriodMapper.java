package kr.escp.commons.period;

import java.util.Date;

/**
 * 설명을 추가하세요.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
public interface ITimePeriodMapper {

	Date mapStart(Date moment);

	Date mapEnd(Date moment);

	Date unmapStart(Date moment);

	Date unmapEnd(Date moment);
}
