package kr.kth.timeperiod;

import com.google.common.base.Objects;
import kr.kth.commons.Guard;
import kr.kth.commons.ValueObjectBase;
import kr.kth.commons.tools.HashTool;
import kr.kth.timeperiod.timerange.TimeRange;
import kr.kth.timeperiod.tools.TimeTool;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import static kr.kth.commons.Guard.firstNotNull;

/**
 * 기간을 나타내는 기본 클래스입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
@Slf4j
public abstract class TimePeriodBase extends ValueObjectBase implements ITimePeriod {

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
        this.start = firstNotNull(start, TimeSpec.MinPeriodTime);
        this.end = firstNotNull(end, TimeSpec.MaxPeriodTime);
        setReadonly(readonly);
    }

    protected TimePeriodBase(DateTime start, Long duration) {
        this(start, duration, false);
    }

    protected TimePeriodBase(DateTime start, Long duration, boolean readonly) {
        this.start = firstNotNull(start, TimeSpec.MinPeriodTime);
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

    public void setDuration(Long duration) {
        assertMutable();
        assert (duration != null) && (duration >= 0);

        if (hasStart())
            this.end = this.start.plus(duration);
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
        this.start = firstNotNull(start, TimeSpec.MinPeriodTime);
        this.end = firstNotNull(end, TimeSpec.MaxPeriodTime);
    }

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
    public boolean isSamePeriod(ITimePeriod that) {
        return (that != null) &&
                Objects.equal(this.start, that.getStart()) &&
                Objects.equal(this.end, that.getEnd());

    }

    @Override
    public boolean hasInside(DateTime moment) {
        return TimeTool.hasInside(this, moment);
    }

    @Override
    public boolean hasInside(ITimePeriod that) {
        Guard.shouldNotBeNull(that, "that");
        return TimeTool.hasInside(this, that);
    }

    @Override
    public boolean intersectsWith(ITimePeriod that) {
        return TimeTool.intersectsWith(this, that);
    }

    @Override
    public boolean overlapsWith(ITimePeriod that) {
        return TimeTool.overlapsWith(this, that);
    }

    @Override
    public void reset() {
        assertMutable();
        this.start = TimeSpec.MinPeriodTime;
        this.end = TimeSpec.MaxPeriodTime;
    }

    @Override
    public PeriodRelation getRelation(ITimePeriod that) {
        Guard.shouldNotBeNull(that, "that");
        return TimeTool.getRelation(this, that);
    }

    @Override
    public String getDescription(ITimeFormatter formatter) {
        return format(formatter);
    }

    @Override
    public ITimePeriod getIntersection(ITimePeriod that) {
        Guard.shouldNotBeNull(that, "that");
        return TimeTool.getIntersectionRange(this, that);
    }

    @Override
    public ITimePeriod getUnion(ITimePeriod that) {
        Guard.shouldNotBeNull(that, "that");
        return TimeTool.getUnionBlock(this, that);
    }

    @Override
    public int compareTo(ITimePeriod o) {
        Guard.shouldNotBeNull(o, "o");
        return this.start.compareTo(o.getStart());
    }

    protected final void assertMutable() {
        if (readonly)
            throw new IllegalStateException("Current object is readonly.");
    }

    protected String format(ITimeFormatter formatter) {
        return formatter.getPeriod(getStart(), getEnd(), getDuration());
    }

    @Override
    public int hashCode() {
        return HashTool.compute(this.start, this.end, this.readonly);
    }

    @Override
    protected Objects.ToStringHelper buildStringHelper() {
        return super.buildStringHelper()
                .add("start", start)
                .add("end", end)
                .add("readonly", readonly);
    }
}
