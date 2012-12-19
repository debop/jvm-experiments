package kr.kth.commons.timeperiod.timeline;

import kr.kth.commons.timeperiod.*;

/**
 * pudding.pudding.commons.timeperiod.timeline.TimeLine
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18.
 */
public class TimeLine extends TimePeriodBase implements ITimeLine {

	private static final long serialVersionUID = -2141722270204777840L;


	@Override
	public ITimePeriodContainer getPeriods() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public ITimePeriod getLimits() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public ITimePeriodMapper getPriodMapper() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public ITimePeriodCollection combinePeriods() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public ITimePeriodCollection intersectPeriods() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public ITimePeriodCollection calculateGaps() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void setDuration(Long duration) {
		//To change body of implemented methods use File | Settings | File Templates.
	}
}
