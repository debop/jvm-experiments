package kr.kth.commons.timeperiod;

import com.google.common.base.Objects;
import kr.kth.commons.base.Guard;
import kr.kth.commons.timeperiod.timerange.TimeRange;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.Duration;

/**
 * 기간을 나타내는 기본 클래스입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
@Slf4j
public abstract class TimePeriodBase implements ITimePeriod {

	private static final long serialVersionUID = 7093665366996191218L;

	protected DateTime start;
	protected DateTime end;
	protected boolean readonly = false;

	protected TimePeriodBase() {
		this((DateTime) null, (DateTime) null, false);
	}

	protected TimePeriodBase(boolean readonly) {
		this(TimeSpec.MinPeriodTime, TimeSpec.MaxPeriodTime, readonly);
	}

	protected TimePeriodBase(DateTime moment) {
		this(moment, moment, false);
	}

	protected TimePeriodBase(DateTime moment, boolean readonly) {
		this(moment, moment, readonly);
	}

	protected TimePeriodBase(DateTime start, DateTime end) {
		this(start, end, false);
	}

	protected TimePeriodBase(DateTime start, DateTime end, boolean readonly) {
		this.start = Guard.firstNotNull(start, TimeSpec.MinPeriodTime);
		this.end = Guard.firstNotNull(end, TimeSpec.MaxPeriodTime);
		setReadonly(readonly);
	}

	protected TimePeriodBase(DateTime start, Long duration) {
		this(start, duration, false);
	}

	protected TimePeriodBase(DateTime start, Long duration, boolean readonly) {
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

	protected TimePeriodBase(ITimePeriod src, boolean readonly) {
		this(src);
		setReadonly(readonly);
	}

	@Override
	public DateTime getStart() {
		return this.start;
	}

	@Override
	public DateTime getEnd() {
		return this.end;
	}

	@Override
	public Long getDuration() {
		return (end.getMillis() - start.getMillis());
	}

	@Override
	public void setDuration(Long duration) {
		assertMutable();
		assert (duration != null) && (duration >= 0);

		if (hasStart())
			this.end = start.plus(duration);
	}

	@Override
	public String getDurationDescription() {
		return Duration.millis(getDuration()).toString();
	}

	@Override
	public boolean hasStart() {
		return this.start != TimeSpec.MinPeriodTime;
	}

	@Override
	public boolean hasEnd() {
		return this.end != TimeSpec.MaxPeriodTime;
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
	public void setup(DateTime start, DateTime end) {
		assertMutable();
		start = Guard.firstNotNull(start, TimeSpec.MinPeriodTime);
		end = Guard.firstNotNull(end, TimeSpec.MaxPeriodTime);
	}

	@Override
	public ITimePeriod copy() {
		return copy(0);
	}

	@Override
	public ITimePeriod copy(long offset) {
		if (offset == 0)
			return new TimeRange(this);

		return new TimeRange(hasStart() ? start.plus(offset) : start,
		                     hasEnd() ? end.plus(offset) : end);
	}

	@Override
	public void move(long offset) {
		if (offset == 0)
			return;

		if (hasStart())
			this.start = this.start.plus(offset);

		if (hasEnd())
			this.end = this.end.plus(offset);
	}

	@Override
	public void isSamePeriod(ITimePeriod that) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public boolean hasInside(DateTime moment) {
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

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
		              .add("start", getStart())
		              .add("end", getEnd())
		              .add("duration", getDuration())
		              .add("readonly", isReadonly())
		              .toString();
	}
}
