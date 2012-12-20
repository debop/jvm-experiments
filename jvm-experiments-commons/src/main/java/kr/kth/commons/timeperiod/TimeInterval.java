package kr.kth.commons.timeperiod;

import kr.kth.commons.base.Guard;
import kr.kth.commons.timeperiod.timerange.TimeRange;
import kr.kth.commons.timeperiod.tools.TimeTool;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * 설명을 추가하세요.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 20
 */
@Slf4j
public class TimeInterval extends TimePeriodBase implements ITimeInterval {

	private static final long serialVersionUID = -5917038576117115113L;

	public static TimeInterval Anytime = new TimeInterval(TimeSpec.MinPeriodTime,
	                                                      TimeSpec.MaxPeriodTime,
	                                                      IntervalEdge.Closed,
	                                                      IntervalEdge.Closed,
	                                                      false,
	                                                      true);

	public static TimeRange toTimeRange(ITimeInterval interval) {
		if (interval == null)
			return null;

		return interval.isAnyTime()
		       ? TimeRange.Anytime
		       : new TimeRange(interval.getStartInterval(),
		                       interval.getEndInterval(),
		                       interval.isReadonly());
	}

	public static TimeBlock toTimeBlock(ITimeInterval interval) {
		if (interval == null)
			return null;
		return interval.isAnyTime()
		       ? TimeBlock.Anytime
		       : new TimeBlock(interval.getStartInterval(),
		                       interval.getEndInterval(),
		                       interval.isReadonly());
	}

	// region << constructors >>

	public TimeInterval() {
		this(TimeSpec.MinPeriodTime, TimeSpec.MaxPeriodTime);
	}

	public TimeInterval(Date moment) {
		this(moment, IntervalEdge.Closed, IntervalEdge.Closed, true, false);
	}

	public TimeInterval(Date moment, IntervalEdge startEdge, IntervalEdge endEdge) {
		this(moment, startEdge, endEdge, true, false);
	}

	public TimeInterval(Date moment, IntervalEdge startEdge, IntervalEdge endEdge, boolean intervalEnabled, boolean readonly) {
		this(moment, moment, startEdge, endEdge, intervalEnabled, readonly);
	}

	public TimeInterval(Date startInterval, Date endInterval) {
		this(startInterval, endInterval, IntervalEdge.Closed, IntervalEdge.Closed, true, false);
	}

	public TimeInterval(Date startInterval, Date endInterval, IntervalEdge startEdge, IntervalEdge endEdge) {
		this(startInterval, endInterval, startEdge, endEdge, true, false);
	}

	public TimeInterval(Date startInterval, Date endInterval, IntervalEdge startEdge, IntervalEdge endEdge,
	                    boolean intervalEnabled, boolean readonly) {
		super(startInterval, endInterval, readonly);

		this.startEdge = startEdge;
		this.endEdge = endEdge;
		this.intervalEnabled = intervalEnabled;
	}

	public TimeInterval(ITimePeriod src) {
		super(src);

		if (src instanceof ITimeInterval) {
			ITimeInterval interval = (ITimeInterval) src;

			this.start = interval.getStartInterval();
			this.end = interval.getEndInterval();
			this.startEdge = interval.getStartEdge();
			this.endEdge = interval.getEndEdge();
			this.intervalEnabled = interval.isInetervalEnabled();
		}
	}

	// endregion

	private boolean intervalEnabled;
	private IntervalEdge startEdge;
	private IntervalEdge endEdge;

	@Override
	public boolean isStartOpen() {
		return (this.startEdge == IntervalEdge.Open);
	}

	@Override
	public boolean isEndOpen() {
		return (this.endEdge == IntervalEdge.Open);
	}

	@Override
	public boolean isStartClosed() {
		return (this.startEdge == IntervalEdge.Closed);
	}

	@Override
	public boolean isEndClosed() {
		return (this.endEdge == IntervalEdge.Closed);
	}

	@Override
	public boolean isClosed() {
		return isStartClosed() && isEndClosed();
	}

	@Override
	public boolean isEmpty() {
		return isMoment() && !isClosed();
	}

	@Override
	public boolean isDegenerate() {
		return isMoment() && isClosed();
	}

	@Override
	public boolean isIntervalEnabled() {
		return intervalEnabled;
	}

	@Override
	public void setIntervalEnabled(boolean intervalEnabled) {
		assertMutable();
		this.intervalEnabled = intervalEnabled;
	}

	@Override
	public boolean hasStart() {
		return (this.start != TimeSpec.MinPeriodTime) || !isStartClosed();
	}

	@Override
	public Date getStartInterval() {
		return this.start;
	}

	@Override
	public void setStartInterval(Date start) {
		assertMutable();
		Guard.assertTrue(start.getTime() <= this.end.getTime(), "start");
		this.start = start;
	}

	@Override
	public Date getStart() {
		if (this.intervalEnabled && isStartOpen())
			return new Date(this.start.getTime() + 1);
		return this.start;
	}

	@Override
	public IntervalEdge getStartEdge() {
		return this.startEdge;
	}

	@Override
	public void setStartEdge(IntervalEdge edge) {
		assertMutable();
		this.startEdge = edge;
	}

	@Override
	public boolean hasEnd() {
		return this.end != TimeSpec.MaxPeriodTime || !isEndClosed();
	}


	@Override
	public Date getEndInterval() {
		return this.end;
	}

	@Override
	public void setEndInterval(Date end) {
		assertMutable();
		Guard.assertTrue(end.getTime() >= this.start.getTime(), "end");
		this.end = end;
	}

	@Override
	public Date getEnd() {
		if (intervalEnabled || isEndOpen())
			return new Date(this.end.getTime() - 1);
		return this.end;
	}

	@Override
	public IntervalEdge getEndEdge() {
		return endEdge;
	}

	@Override
	public void setEndEdge(IntervalEdge edge) {
		assertMutable();
		this.endEdge = edge;
	}

	@Override
	public Long getDuration() {
		return this.end.getTime() - this.start.getTime();
	}

	@Override
	public void setup(Date start, Date end) {
		super.setup(start, end);

		if (log.isDebugEnabled())
			log.debug("기간을 다시 설정합니다. start=[{}], end=[{}]", start, end);

		assertMutable();

		TimeTool.adjustPeriod(start, end);

		this.start = Guard.firstNotNull(start, TimeSpec.MinPeriodTime);
		this.end = Guard.firstNotNull(end, TimeSpec.MaxPeriodTime);
	}

	@Override
	public void expandStartTo(Date moment) {

	}

	@Override
	public void expandEndTo(Date moment) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void expandTo(Date moment) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void expandTo(ITimePeriod period) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void shrinkStartTo(Date moment) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void shrinkEndTo(Date moment) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void shrinkTo(Date moment) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void shrinkTo(ITimePeriod period) {
		//To change body of implemented methods use File | Settings | File Templates.
	}
}
