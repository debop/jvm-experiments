package kr.kth.timeperiod;

import kr.kth.commons.Guard;
import kr.kth.timeperiod.timerange.TimeRange;
import kr.kth.timeperiod.tools.TimeTool;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import static kr.kth.commons.Guard.shouldNotBeNull;

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

    public TimeInterval(DateTime moment) {
        this(moment, IntervalEdge.Closed, IntervalEdge.Closed, true, false);
    }

    public TimeInterval(DateTime moment, IntervalEdge startEdge, IntervalEdge endEdge) {
        this(moment, startEdge, endEdge, true, false);
    }

    public TimeInterval(DateTime moment, IntervalEdge startEdge, IntervalEdge endEdge, boolean intervalEnabled, boolean readonly) {
        this(moment, moment, startEdge, endEdge, intervalEnabled, readonly);
    }

    public TimeInterval(DateTime startInterval, DateTime endInterval) {
        this(startInterval, endInterval, IntervalEdge.Closed, IntervalEdge.Closed, true, false);
    }

    public TimeInterval(DateTime startInterval, DateTime endInterval, IntervalEdge startEdge, IntervalEdge endEdge) {
        this(startInterval, endInterval, startEdge, endEdge, true, false);
    }

    public TimeInterval(DateTime startInterval, DateTime endInterval, IntervalEdge startEdge, IntervalEdge endEdge,
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
            this.intervalEnabled = interval.isIntervalEnabled();
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
    public DateTime getStartInterval() {
        return this.start;
    }

    @Override
    public void setStartInterval(DateTime start) {
        assertMutable();
        Guard.shouldBe(start.compareTo(this.end) <= 0,
                "새로운 getStart=[%s]는 getEnd=[%s] 보다 작거나 같아야 합니다.", start, this.end);
        this.start = start;
    }

    @Override
    public DateTime getStart() {
        if (this.intervalEnabled && isStartOpen())
            return this.start.plus(1L);
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
    public DateTime getEndInterval() {
        return this.end;
    }

    @Override
    public void setEndInterval(DateTime end) {
        assertMutable();
        Guard.shouldBe(end.compareTo(this.start) >= 0,
                "새로운 getEnd=[%s]가 getStart=[{}]보다 크거나 같아야 합니다.", end, this.start);
        this.end = end;
    }

    @Override
    public DateTime getEnd() {
        if (intervalEnabled || isEndOpen())
            return this.end.minus(1L);
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
        return this.end.minus(this.start.getMillis()).getMillis();
    }

    @Override
    public void setup(DateTime start, DateTime end) {
        super.setup(start, end);

        if (TimeInterval.log.isDebugEnabled())
            TimeInterval.log.debug("기간을 다시 설정합니다. getStart=[{}], getEnd=[{}]", start, end);

        assertMutable();

        TimeTool.adjustPeriod(start, end);

        this.start = Guard.firstNotNull(start, TimeSpec.MinPeriodTime);
        this.end = Guard.firstNotNull(end, TimeSpec.MaxPeriodTime);
    }

    @Override
    public void expandStartTo(DateTime moment) {
        assertMutable();
        if (start.compareTo(moment) > 0)
            start = moment;
    }

    @Override
    public void expandEndTo(DateTime moment) {
        assertMutable();
        if (end.compareTo(moment) < 0)
            end = moment;
    }

    @Override
    public void expandTo(DateTime moment) {
        shouldNotBeNull(moment, "moment");
        assertMutable();

        expandStartTo(moment);
        expandEndTo(moment);
    }

    @Override
    public void expandTo(ITimePeriod period) {
        shouldNotBeNull(period, "period");
        assertMutable();

        expandStartTo(period.getStart());
        expandEndTo(period.getEnd());
    }

    @Override
    public void shrinkStartTo(DateTime moment) {
        shouldNotBeNull(moment, "moment");
        assertMutable();

        if (start.compareTo(moment) < 0)
            start = moment;
    }

    @Override
    public void shrinkEndTo(DateTime moment) {
        shouldNotBeNull(moment, "moment");
        assertMutable();

        if (end.compareTo(moment) > 0)
            end = moment;
    }

    @Override
    public void shrinkTo(DateTime moment) {
        shouldNotBeNull(moment, "moment");
        assertMutable();

        shrinkStartTo(moment);
        shrinkEndTo(moment);
    }

    @Override
    public void shrinkTo(ITimePeriod period) {
        shouldNotBeNull(period, "period");
        assertMutable();

        shrinkStartTo(period.getStart());
        shrinkEndTo(period.getEnd());
    }
}
