package kr.kth.commons.timeperiod.timeline;

import com.google.common.collect.SortedMultiset;
import com.google.common.collect.TreeMultiset;
import kr.kth.commons.timeperiod.ITimeLineMoment;
import kr.kth.commons.timeperiod.ITimeLineMomentCollection;
import kr.kth.commons.timeperiod.ITimePeriod;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.util.Iterator;

/**
 * {@link ITimePeriod}의 시작시각,완료시각을 키로 가지고, {@link ITimePeriod}를 Value로 가지는 MultiMap[DateTime, ITimePeriod] 을 생성합니다.
 * 단 Key 값으로 정렬합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 21.
 */
@Slf4j
public class TimeLineMomentCollection implements ITimeLineMomentCollection {

    private static final long serialVersionUID = 7467469934686869525L;

    private final SortedMultiset<ITimeLineMoment> timeLineMoments = TreeMultiset.create(new TimeLineMomentComparer());

    @Override
    public int size() {
        return timeLineMoments.size();
    }

    @Override
    public boolean isEmpty() {
        return timeLineMoments.isEmpty();
    }

    @Override
    public ITimeLineMoment getMin() {
        return isEmpty() ? null : timeLineMoments.elementSet().first();
    }

    @Override
    public ITimeLineMoment getMax() {
        return isEmpty() ? null : timeLineMoments.elementSet().last();
    }

    @Override
    public ITimeLineMoment get(int index) {
        int i = 0;
        for (ITimeLineMoment element : timeLineMoments.elementSet()) {
            if (i == index) {
                return element;
            }
            i++;
        }
        return null;
    }

    @Override
    public void add(ITimePeriod period) {
        if (period != null) {
            addPeriod(period.getStart(), period);
            addPeriod(period.getEnd(), period);
        }
    }

    @Override
    public void addAll(Iterable<? extends ITimePeriod> periods) {
        for (ITimePeriod period : periods)
            add(period);
    }

    @Override
    public void remove(ITimePeriod period) {
        if (period != null) {
            removePeriod(period.getStart(), period);
            removePeriod(period.getEnd(), period);
        }
    }

    @Override
    public ITimeLineMoment find(DateTime moment) {
        if (moment == null)
            return null;

        for (ITimeLineMoment element : timeLineMoments.elementSet()) {
            if (element.getMoment().equals(moment)) {
                return element;
            }
        }
        return null;
    }

    @Override
    public boolean contains(DateTime moment) {
        return (moment != null) && (find(moment) != null);
    }

    @Override
    public Iterator<ITimeLineMoment> iterator() {
        return timeLineMoments.iterator();
    }

    protected void addPeriod(DateTime moment, ITimePeriod period) {
        if (log.isDebugEnabled())
            log.debug("ITimeLineMoment를 추가합니다... moment=[{}], period=[{}]", moment, period);

        ITimeLineMoment timeLineMoment;
        synchronized (this.timeLineMoments) {
            timeLineMoment = find(moment);
            if (timeLineMoment == null) {
                timeLineMoment = new TimeLineMoment(moment);
                this.timeLineMoments.add(timeLineMoment);
            }
            timeLineMoment.getPeriods().add(period);
        }
        if (log.isDebugEnabled())
            log.debug("ITimeLineMoment를 추가했습니다!!! timeLineMoment=[{}]", timeLineMoments);
    }

    protected void removePeriod(DateTime moment, ITimePeriod period) {
        if (log.isDebugEnabled())
            log.debug("ITimeLineMoment를 제거합니다... moment=[{}], period=[{}]", moment, period);

        synchronized (this.timeLineMoments) {
            ITimeLineMoment timeLineMoment = find(moment);

            if (timeLineMoment != null && timeLineMoment.getPeriods().contains(period)) {

                timeLineMoment.getPeriods().remove(period);
                if (log.isDebugEnabled())
                    log.debug("ITimeLineMoment를 제거했습니다. moment=[{}], period=[{}]", moment, period);

                if (timeLineMoment.getPeriods().size() == 0) {
                    timeLineMoments.remove(timeLineMoment);

                    if (log.isDebugEnabled())
                        log.debug("ITimeLineMoment를 제거했습니다. timeLineMoment=[{}]", timeLineMoment);
                }
            }
        }
    }
}
