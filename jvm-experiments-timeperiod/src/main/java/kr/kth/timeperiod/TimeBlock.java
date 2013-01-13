package kr.kth.timeperiod;

import kr.kth.commons.Guard;
import kr.kth.timeperiod.tools.TimeTool;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

/**
 * 기준 일자의 시간 간격을 이용하여 기간을 표현합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 20
 */
@Slf4j
public class TimeBlock extends TimePeriodBase implements ITimeBlock {

    public static final TimeBlock Anytime = new TimeBlock(true);

    private static final long serialVersionUID = -6360640930351210761L;

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
        ITimeBlock result = new TimeBlock(getEnd().plus(startOff), this.duration, isReadonly());

        if (TimeBlock.log.isDebugEnabled())
            TimeBlock.log.debug("Next Block을 구합니다. offset=[{}], NextBlock=[{}]", offset, result);

        return result;
    }

    @Override
    public ITimePeriod getIntersection(ITimePeriod that) {
        Guard.shouldNotBeNull(that, "that");
        return TimeTool.getIntersectionBlock(this, that);
    }

    @Override
    public ITimePeriod getUnion(ITimePeriod that) {
        Guard.shouldNotBeNull(that, "that");
        return TimeTool.getUnionBlock(this, that);
    }

    protected void assertValidDuration(long duration) {
        Guard.shouldBe(duration >= TimeSpec.MinPeriodDuration, "getDuration");
    }
}
