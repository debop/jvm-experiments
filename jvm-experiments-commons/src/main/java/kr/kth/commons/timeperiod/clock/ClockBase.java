package kr.kth.commons.timeperiod.clock;

import kr.kth.commons.timeperiod.IClock;
import lombok.Setter;
import org.apache.commons.lang3.time.DateUtils;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * kr.kth.commons.timeperiod.clock.ClockBase
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 19.
 */
public abstract class ClockBase implements IClock, Serializable {

	private static final long serialVersionUID = 2363723693141613884L;

	@Setter
	private Date now;

	protected ClockBase() {}

	protected ClockBase(Date now) {
		this.now = now;
	}

	@Override
	public Date getNow() {
		if (now == null)
			now = new Date();
		return now;
	}


	@Override
	public Date getToday() {
		return DateUtils.truncate(getNow(), Calendar.DATE);
	}

	@Override
	public long getTimeOfDay() {
		return getNow().getTime() - getToday().getTime();
	}
}
