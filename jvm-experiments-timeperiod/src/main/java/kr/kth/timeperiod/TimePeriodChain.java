package kr.kth.timeperiod;

import com.google.common.collect.Lists;
import kr.kth.commons.Guard;
import kr.kth.timeperiod.tools.TimeTool;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.util.Collection;

/**
 * kr.kth.timeperiod.TimePeriodChain
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 21.
 */
@Slf4j
public class TimePeriodChain extends TimePeriodContainer implements ITimePeriodChain {

    private static final long serialVersionUID = 4552651168746712869L;

    public TimePeriodChain() {
    }

    public TimePeriodChain(Collection<? extends ITimePeriod> periods) {
        if (periods != null)
            addAll(periods);
    }

    @Override
    public DateTime getStart() {
        return (getFirst() != null) ? getFirst().getStart() : TimeSpec.MinPeriodTime;
    }

    @Override
    public void setStart(DateTime start) {
        if (getFirst() != null)
            move(start.getMillis() - getStart().getMillis());
    }

    @Override
    public DateTime getEnd() {
        return (getLast() != null) ? getLast().getEnd() : TimeSpec.MaxPeriodTime;
    }

    @Override
    public void setEnd(DateTime end) {
        if (getLast() != null)
            move(end.getMillis() - getEnd().getMillis());
    }

    @Override
    public ITimePeriod getFirst() {
        return (size() > 0) ? periods.get(0) : null;
    }

    @Override
    public ITimePeriod getLast() {
        return (size() > 0) ? periods.get(size() - 1) : null;
    }

    @Override
    public ITimePeriod set(int index, ITimePeriod value) {
        Guard.shouldBe(index >= 0 && index < size(), "인덱스 범위가 잘못되었습니다. size()=[%d], index=[%d]", size(), index);
        remove(index);
        addAll(index, Lists.newArrayList(value));
        return value;
    }

    /**
     * 새로운 {@link ITimePeriod} 를 Chain의 제일 끝에 붙여 넣습니다. 지정된 item의 기간이 변경됩니다.
     */
    @Override
    public boolean add(ITimePeriod item) {
        Guard.shouldNotBeNull(item, "item");
        TimeTool.assertMutable(item);

        ITimePeriod last = getLast();

        if (last != null) {
            assertSpaceAfter(last.getEnd(), item.getDuration());
            item.setup(last.getEnd(), last.getEnd().plus(item.getDuration()));
        }
        if (TimePeriodChain.log.isDebugEnabled())
            TimePeriodChain.log.debug("Period [{}]를 Chain의 끝에 추가합니다.", item);

        return periods.add(item);
    }

    @Override
    public void add(int index, ITimePeriod item) {
        Guard.shouldNotBeNull(item, "item");
        Guard.shouldBeInRange(index, 0, size(), "index");
        TimeTool.assertMutable(item);

        if (TimePeriodChain.log.isDebugEnabled())
            TimePeriodChain.log.debug("Chain의 인덱스[{}]에 요소 [{}]fmf 삽입합니다...", index, item);

        long itemDuration = item.getDuration();
        ITimePeriod prevItem = null;
        ITimePeriod nextItem = null;

        if (size() > 0) {
            if (index > 0) {
                prevItem = get(index - 1);
                assertSpaceAfter(getEnd(), itemDuration);
            }
            if (index < size() - 1) {
                nextItem = get(index);
                assertSpaceBefore(getStart(), itemDuration);
            }
        }

        this.periods.add(index, item);

        if (prevItem != null) {
            if (TimePeriodChain.log.isDebugEnabled())
                TimePeriodChain.log.debug("선행 Period에 기초하여 삽입한 Period와 후행 Period들의 시간을 조정합니다...");

            item.setup(prevItem.getEnd(), prevItem.getEnd().plus(itemDuration));
            for (int i = index + 1; i < size(); i++) {
                DateTime start = get(i).getStart().plus(itemDuration);
                get(i).setup(start, start.plus(get(i).getDuration()));
            }
        }
        if (nextItem != null) {
            if (TimePeriodChain.log.isDebugEnabled())
                TimePeriodChain.log.debug("후행 Period에 기초하여 삽입한 Period와 선행 Period들의 시간을 조정합니다...");

            DateTime nextStart = nextItem.getStart().minus(itemDuration);
            item.setup(nextStart, nextStart.plus(itemDuration));

            for (int i = 0; i < index - 1; i++) {
                nextStart = get(i).getStart().minus(itemDuration);
                get(i).setup(nextStart, nextStart.plus(get(i).getDuration()));
            }
        }
    }

    /**
     * 기간이 존재하는(hasPeriod() 가 true 인) {@link ITimePeriod} 들을 추가합니다.
     */
    @Override
    public void addAll(Iterable<? extends ITimePeriod> items) {
        if (items == null) return;

        for (ITimePeriod item : items) {
            add(item);
        }
    }


    @Override
    public boolean remove(Object item) {
        Guard.shouldNotBeNull(item, "item");
        if (!(item instanceof ITimePeriod))
            return false;

        if (size() <= 0)
            return false;

        if (TimePeriodChain.log.isDebugEnabled())
            TimePeriodChain.log.debug("요소 ITimePeriod [{}] 를 제거합니다...", item);

        ITimePeriod period = (ITimePeriod) item;
        long itemDuration = period.getDuration();
        int index = indexOf(item);

        ITimePeriod next = null;
        if (itemDuration > 0 && index > 0 && index < size() - 1)
            next = get(index);

        boolean isRemoved = periods.remove(item);

        if (isRemoved && next != null) {
            if (TimePeriodChain.log.isDebugEnabled())
                TimePeriodChain.log.debug("요소[{}]를 제거하고, Chain의 후속 Period 들의 기간을 조정합니다...", item);
            for (int i = index; i < size(); i++) {
                DateTime start = get(i).getStart().minus(itemDuration);
                get(i).setup(start, start.plus(get(i).getDuration()));
            }
        }
        if (TimePeriodChain.log.isDebugEnabled())
            TimePeriodChain.log.debug("요소[{}]를 제거했습니다. 제거결과=[{}]", item, isRemoved);

        return isRemoved;
    }

    @Override
    public ITimePeriod remove(int index) {
        if (TimePeriodChain.log.isDebugEnabled())
            TimePeriodChain.log.debug("index [{}] 의 요소를 제거합니다...", index);
        Guard.shouldBeInRange(index, 0, size(), "index");

        ITimePeriod removedPeriod = periods.get(index);
        remove(removedPeriod);
        return removedPeriod;
    }


    /**
     * moment 이전에 duration 만큼의 시간적 공간이 있는지 여부 (새로운 기간을 추가하기 위해서는 빈 공간이 필요합니다)
     */
    protected void assertSpaceBefore(DateTime moment, long duration) {
        if (TimePeriodChain.log.isDebugEnabled())
            TimePeriodChain.log.debug("moment=[{}] 이전에 duration=[{}] 만큼의 공간이 있는지 확인합니다.", moment, duration);
        boolean hasSpace = (moment != TimeSpec.MinPeriodTime);

        if (hasSpace) {
            long remaining = moment.getMillis() - TimeSpec.MinPeriodTime.getMillis();
            hasSpace = (duration <= remaining);
        }
        Guard.shouldBe(hasSpace, "duration [%d] 은 범위를 벗어났습니다.", duration);
    }

    /**
     * moment 이후에 duration 만큼의 시간적 공간이 있는지 여부 (새로운 기간을 추가하기 위해서는 빈 공간이 필요합니다)
     */
    protected void assertSpaceAfter(DateTime moment, long duration) {
        if (TimePeriodChain.log.isDebugEnabled())
            TimePeriodChain.log.debug("moment=[{}] 이후에 duration=[{}] 만큼의 공간이 있는지 확인합니다.", moment, duration);
        boolean hasSpace = moment != TimeSpec.MaxPeriodTime;

        if (hasSpace) {
            long remaining = TimeSpec.MaxPeriodTime.getMillis() - moment.getMillis();
            hasSpace = duration <= remaining;
        }
        Guard.shouldBe(hasSpace, "duration [%d]은 범위를 벗어났습니다.", duration);
    }
}
