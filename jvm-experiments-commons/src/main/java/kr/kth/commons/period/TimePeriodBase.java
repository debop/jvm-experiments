package kr.kth.commons.period;

import kr.kth.commons.Guard;
import kr.kth.commons.period.timerange.TimeRange;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 기간을 나타내는 기본 클래스입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
public abstract class TimePeriodBase implements ITimePeriod {

	private static final long serialVersionUID = 7093665366996191218L;

	protected Date start;
	protected Date end;
	protected boolean readonly = false;

	protected TimePeriodBase() {
		this(null, null, false);
	}

	protected TimePeriodBase(boolean readonly) {
		this(TimeSpec.MinPeriodTime, TimeSpec.MaxPeriodTime, readonly);
	}

	protected TimePeriodBase(Date moment) {
		this(moment, moment, false);
	}

	protected TimePeriodBase(Date moment, boolean readonly) {
		this(moment, moment, readonly);
	}

	protected TimePeriodBase(Date start, Date end) {
		this(start, end, false);
	}

	protected TimePeriodBase(Date start, Date end, boolean readonly) {
		this.start = Guard.firstNotNull(start, TimeSpec.MinPeriodTime);
		this.end = Guard.firstNotNull(end, TimeSpec.MaxPeriodTime);
		setReadonly(readonly);
	}

	protected TimePeriodBase(Date start, Timestamp duration) {
		this(start, duration, false);
	}

	protected TimePeriodBase(Date start, Timestamp duration, boolean readonly) {
		this.start = Guard.firstNotNull(start, TimeSpec.MinPeriodTime);
		this.end = TimeSpec.MaxPeriodTime;
		setDuration(duration);
		setReadonly(readonly);
	}

	protected TimePeriodBase(ITimePeriod src) {
		Guard.shouldNotBeNull(src, "src");

		this.start = src.getStart();
		this.end = src.getEnd();
		setReadonly(src.isReadonly());
	}

	@Override
	public Date getStart() {
		return this.start;
	}

	@Override
	public Date getEnd() {
		return this.end;
	}

	@Override
	public Timestamp getDuration() {
		return new Timestamp(end.getTime() - start.getTime());
	}

	@Override
	public void setDuration(Timestamp duration) {
		assertMutable();
		assert (duration != null) && (duration.getTime() >= 0);

		if (hasStart())
			this.end = new Date(this.start.getTime() + duration.getTime());
	}

	@Override
	public String getDurationDescription() {
		// TODO: TimeFormatter를 이용하여 구현 필요
		return getDuration().toString();
	}

	@Override
	public boolean hasStart() {
		return this.start.getTime() != TimeSpec.MinPeriodTime.getTime();
	}

	@Override
	public boolean hasEnd() {
		return this.end.getTime() != TimeSpec.MaxPeriodTime.getTime();
	}

	@Override
	public boolean hasPeriod() {
		return hasStart() && hasEnd();
	}

	@Override
	public boolean isMoment() {
		return hasPeriod() && (start == end);
	}

	@Override
	public boolean isAnyTime() {
		return !hasStart() && !hasEnd();
	}

	@Override
	public boolean isReadonly() {
		return this.readonly;
	}

	protected void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	@Override
	public void setup(Date start, Date end) {
		assertMutable();
		start = Guard.firstNotNull(start, TimeSpec.MinPeriodTime);
		end = Guard.firstNotNull(end, TimeSpec.MaxPeriodTime);
	}

	@Override
	public ITimePeriod copy() {
		return copy(TimeSpec.ZeroTimestamp);
	}

	@Override
	public ITimePeriod copy(Timestamp offset) {
		if (offset.getTime() == 0)
			return new TimeRange(this);

		return new TimeRange(hasStart() ? new Date(start.getTime() + offset.getTime()) : start,
		                     hasEnd() ? new Date(end.getTime() + offset.getTime()) : end);
	}

	@Override
	public void move(Timestamp offset) {
		if (offset == null || offset.getTime() == 0)
			return;

		if (hasStart())
			this.start = new Date(this.start.getTime() + offset.getTime());

		if (hasEnd())
			this.end = new Date(this.end.getTime() + offset.getTime());
	}

	@Override
	public void isSamePeriod(ITimePeriod that) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public boolean hasInside(Date moment) {
		return false;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public boolean overlapsWith(ITimePeriod that) {
		return false;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void reset() {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public PeriodRelation getRelation(ITimePeriod that) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public ITimePeriod getIntersection(ITimePeriod that) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public ITimePeriod getUnion(ITimePeriod that) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public int compareTo(ITimePeriod o) {
		return 0;  //To change body of implemented methods use File | Settings | File Templates.
	}

	protected final void assertMutable() {
		if (readonly)
			throw new IllegalStateException("Current object is readonly.");
	}
}
