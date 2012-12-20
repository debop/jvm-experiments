package kr.kth.commons.timeperiod.clock;

import kr.kth.commons.timeperiod.IClock;
import kr.kth.commons.timeperiod.tools.TimeTool;
import lombok.Setter;
import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Clock
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 19.
 */
public abstract class ClockBase implements IClock, Serializable {

	private static final long serialVersionUID = 2363723693141613884L;

	@Setter
	private DateTime now;

	protected ClockBase() {}

	protected ClockBase(DateTime now) {
		this.now = now;
	}

	@Override
	public DateTime getNow() {
		if (now == null)
			now = new DateTime();
		return now;
	}


	@Override
	public DateTime getToday() {
		return TimeTool.trimToDay(this.getNow());
	}

	@Override
	public long getTimeOfDay() {
		return getNow().getMillisOfDay();
	}
}
