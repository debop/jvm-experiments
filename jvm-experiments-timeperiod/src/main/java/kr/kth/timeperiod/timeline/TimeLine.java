package kr.kth.timeperiod.timeline;

import kr.kth.commons.Guard;
import kr.kth.commons.tools.ActivatorTool;
import kr.kth.timeperiod.*;
import kr.kth.timeperiod.timerange.TimeRange;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.util.Collection;

/**
 * 여러 <see cref="ITimePeriod"/>들을 시간의 흐름별로 펼쳐서 표현합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18.
 */
@Slf4j
public class TimeLine<T extends ITimePeriod> implements ITimeLine {

    private static final long serialVersionUID = -2141722270204777840L;

    @Getter
    private final Class<T> periodClass;
    @Getter
    private final ITimePeriodContainer periods;
    @Getter
    private final ITimePeriod limits;
    @Getter
    private final ITimePeriodMapper periodMapper;

    public TimeLine(Class<T> periodClass, ITimePeriodContainer periods) {
        this(periodClass, periods, null, null);
    }

    public TimeLine(Class<T> periodClass, ITimePeriodContainer periods, ITimePeriodMapper periodMapper) {
        this(periodClass, periods, null, periodMapper);
    }

    public TimeLine(Class<T> periodClass, ITimePeriodContainer periods, ITimePeriod limits, ITimePeriodMapper periodMapper) {
        Guard.shouldNotBeNull(periodClass, "periodClass");
        Guard.shouldNotBeNull(periods, "periods");

        this.periodClass = periodClass;
        this.periods = periods;
        this.limits = (limits != null) ? new TimeRange(limits) : new TimeRange(periods);
        this.periodMapper = periodMapper;
    }

    @Override
    public ITimePeriodCollection combinePeriods() {
        if (log.isDebugEnabled())
            log.debug("ITimePeriod 컬렉션의 합집합을 계산합니다...");

        if (periods.size() == 0)
            return new TimePeriodCollection();

        ITimeLineMomentCollection timeLineMoments = getTimeLineMoments();
        if (timeLineMoments.size() == 0)
            return new TimePeriodCollection(new TimeRange(periods));

        return TimeLineTool.combinePeriods(periodClass, timeLineMoments);
    }

    @Override
    public ITimePeriodCollection intersectPeriods() {
        if (log.isDebugEnabled())
            log.debug("ITimePeriod 컬렉션의 교집합을 계산합니다...");

        if (periods.size() == 0)
            return new TimePeriodCollection();

        ITimeLineMomentCollection timeLineMoments = getTimeLineMoments();
        if (timeLineMoments.size() == 0)
            return new TimePeriodCollection();

        return TimeLineTool.intersectPeriods(periodClass, timeLineMoments);
    }

    @Override
    public ITimePeriodCollection calculateGaps() {
        if (log.isDebugEnabled())
            log.debug("ITimePeriod 사이의 Gap들을 계산하여 ITimePeriod 컬렉션으로 빌드합니다...");

        // exclude periods
        TimePeriodCollection gapPeriods = new TimePeriodCollection();
        for (ITimePeriod period : periods) {
            if (getLimits().intersectsWith(period)) {
                gapPeriods.add(new TimeRange(period));
            }
        }

        ITimeLineMomentCollection timeLineLimits = getTimeLineMoments(gapPeriods);

        if (timeLineLimits.size() == 0)
            return new TimePeriodCollection(getLimits());

        T range = ActivatorTool.createInstance(periodClass);
        range.setup(mapPeriodStart(getLimits().getStart()), mapPeriodEnd(getLimits().getEnd()));

        return TimeLineTool.calculateCaps(periodClass, timeLineLimits, range);
    }

    private ITimeLineMomentCollection getTimeLineMoments() {
        return getTimeLineMoments(getPeriods());
    }

    private ITimeLineMomentCollection getTimeLineMoments(Collection<? extends ITimePeriod> momentPeriods) {

        if (log.isDebugEnabled())
            log.debug("기간 컬렉션으로부터 ITimeLineMoment 컬렉션을 빌드합니다...");

        TimeLineMomentCollection timeLineMoments = new TimeLineMomentCollection();

        if (momentPeriods.size() == 0)
            return timeLineMoments;

        TimePeriodCollection intersections = new TimePeriodCollection();

        for (ITimePeriod momentPeriod : momentPeriods) {
            if (momentPeriod.isMoment())
                continue;

            ITimePeriod intersection = getLimits().getIntersection(momentPeriod);
            if (intersection == null || intersection.isMoment())
                continue;

            if (periodMapper != null)
                intersection = new TimeRange(mapPeriodStart(intersection.getStart()),
                        mapPeriodEnd(intersection.getEnd()));
            intersections.add(intersection);
        }

        timeLineMoments.addAll(intersections);

        if (log.isDebugEnabled())
            log.debug("기간 컬렉션으로부터 ITimeLineMoment 컬렉션을 빌드했습니다. timeLineMoments=[{}]", timeLineMoments);

        return timeLineMoments;
    }

    private DateTime mapPeriodStart(DateTime start) {
        return (periodMapper != null) ? periodMapper.unmapStart(start) : start;
    }

    private DateTime mapPeriodEnd(DateTime end) {
        return (periodMapper != null) ? periodMapper.unmapEnd(end) : end;
    }
}
