package kr.kth.commons.timeperiod.clock;

import org.joda.time.DateTime;

/**
 * kr.kth.commons.timeperiod.clock.StaticClock
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 19.
 */
public class StaticClock extends ClockBase {

	private static final long serialVersionUID = -3634515685313254258L;

	public StaticClock(DateTime now) {
		super(now);
	}
}
