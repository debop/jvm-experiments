package kr.kth.commons.timeperiod;

import kr.kth.commons.base.Guard;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

/**
 * 설명을 추가하세요.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 20
 */
@Slf4j
public class TimeBlock extends TimePeriodBase implements ITimeBlock {

	public static final TimeBlock Anytime = new TimeBlock(true);


	private long duration;

	// region << constructors >>

	public TimeBlock() {
		this(false);
	}

	public TimeBlock(boolean readonly) {
		super(readonly);
		this.duration = super.getDuration();
	}

	public TimeBlock(DateTime moment) {
		this(moment, false);
	}

	public TimeBlock(DateTime moment, boolean readonly) {
		super(moment, readonly);
		this.duration = super.getDuration();
	}

	public TimeBlock(DateTime start, DateTime end) {
		this(start, end, false);
	}

	public TimeBlock(DateTime start, DateTime end, boolean readonly) {
		super(start, end, readonly);
		this.duration = duration;
	}

	public TimeBlock(DateTime start, long duration) {
		this(start, duration, false);
	}

	public TimeBlock(DateTime start, long duration, boolean readonly) {
		super(start, duration, readonly);
		this.duration = duration;
	}

	public TimeBlock(long duration, DateTime end) {
		this(duration, end, false);
	}

	public TimeBlock(long duration, DateTime end, boolean readonly) {
		super(null, end, readonly);
		this.duration = duration;
		setStart(end.minus(duration));
	}

	public TimeBlock(ITimePeriod source) {
		super(source);
		this.duration = super.getDuration();
	}

	public TimeBlock(ITimePeriod source, boolean readonly) {
		super(source, readonly);
		this.duration = super.getDuration();
	}

	// endregion

	@Override
	public void setStart(DateTime start) {
		assertMutable();
		super.start = start;
	}

	@Override
	public void setEnd(DateTime end) {
		assertMutable();
		super.end = end;
	}

	@Override
	public Long getDuration() {
		return this.duration;
	}

	@Override
	public void setDuration(long duration) {
		assertMutable();
		assertValidDuration(duration);
		durationFromStart(duration);
	}

	@Override
	public TimeBlock copy() {
		return copy(0);
	}

	@Override
	public TimeBlock copy(long offset) {
		if (offset == 0)
			return new TimeBlock(this);
		return new TimeBlock(hasStart() ? getStart().plus(offset) : getStart(),
		                     hasEnd() ? getEnd().plus(offset) : getEnd(),
		                     isReadonly());
	}

	@Override
	public void setup(DateTime start, long duration) {
		assertMutable();
		setStart(start);
		this.duration = duration;
		setEnd(getStart().plus(duration));
	}

	@Override
	public void durationFromStart(long duration) {
		assertMutable();
		assertValidDuration(duration);

		this.duration = duration;
		setEnd(getStart().plus(duration));
	}

	@Override
	public void durationFromEnd(long duration) {
		assertMutable();
		assertValidDuration(duration);

		this.duration = duration;
		setStart(getEnd().minus(duration));
	}

	public ITimeBlock getPreviousBlock() {
		return getPreviousBlock(0);
	}

	@Override
	public ITimeBlock getPreviousBlock(long offset) {
		long endOff = (offset > 0) ? -offset : offset;
		return new TimeBlock(this.duration, getStart().plus(endOff), isReadonly());
	}

	public ITimeBlock getNextBlock() {
		return getNextBlock(0);
	}

	@Override
	public ITimeBlock getNextBlock(long offset) {
		long startOff = (offset > 0) ? offset : -offset;
		return new TimeBlock(getEnd().plus(startOff), this.duration, isReadonly());
	}

	@Override
	public ITimePeriod getIntersection(ITimePeriod that) {
		Guard.shouldNotBeNull(that, "that");
		// TODO: TimeTool.getIntersectionBlock 구현 필요
		// return TimeTool.getIntersectionBlock(this, that);
		throw new IllegalStateException("구현 중");
	}

	@Override
	public ITimePeriod getUnion(ITimePeriod that) {
		Guard.shouldNotBeNull(that, "that");
		// TODO: TimeTool.getUnionBlock 구현 필요
		// return TimeTool.getUnionBlock(this, that);
		throw new IllegalStateException("구현 중");
	}

	protected void assertValidDuration(long duration) {
		Guard.assertTrue(duration >= TimeSpec.MinPeriodDuration, "duration");
	}
}
