package kr.kth.commons.timeperiod.clock;

import org.joda.time.DateTime;

/**
 * kr.kth.commons.timeperiod.clock.SystemClock
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 19.
 */
public class SystemClock extends ClockBase {

	private static final long serialVersionUID = -2846191313649276854L;

	SystemClock() {}

	@Override
	public DateTime now() {
		return new DateTime();
	}
}
