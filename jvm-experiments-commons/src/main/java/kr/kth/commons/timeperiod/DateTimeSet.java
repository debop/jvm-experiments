package kr.kth.commons.timeperiod;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import kr.kth.commons.base.Guard;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

/**
 * Date 를 요소로 가지는 {@link TreeSet[Date]} 입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 2.
 */
@Slf4j
public class DateTimeSet extends TreeSet<DateTime> implements IDateTimeSet {

	private static final long serialVersionUID = 6897988915122604105L;

	public DateTimeSet() {}

	public DateTimeSet(Collection<? extends DateTime> moments) {
		super(moments);
	}

	/**
	 * {@inheritDoc}
	 */
	public DateTime get(int index) {
		Guard.assertTrue(index >= 0 && index < size(), "Index 의 범위가 벗어났습니다. index=%d, size()=%d", index, size());

		if (index == 0)
			return first();
		if (index == size() - 1)
			return last();

		return Iterators.get(iterator(), index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DateTime getMin() {
		return (isEmpty() ? null : first());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DateTime getMax() {
		return (isEmpty() ? null : last());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long getDuration() {
		if (isEmpty())
			return null;

		DateTime min = getMin();
		DateTime max = getMax();

		return (min != null && max != null) ? (max.getMillis() - min.getMillis()) : null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isMoment() {
		Long duration = getDuration();
		return (duration != null) && (duration == TimeSpec.ZeroTick);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAnyTime() {
		DateTime min = getMin();
		DateTime max = getMax();

		return (min != null) &&
			       (min.getMillis() == TimeSpec.ZeroTick) &&
			       (max != null) &&
			       (max.getMillis() == TimeSpec.ZeroTick);
	}

	@Override
	public boolean addAll(Iterable<? extends DateTime> moments) {
		for (DateTime moment : moments)
			super.add(moment);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Long> getDurations(int startIndex, int count) {

		int endIndex = Math.min(startIndex + count, size() - 1);
		List<Long> durations = Lists.newArrayList();

		for (int i = startIndex; i < endIndex; i++) {
			durations.add(get(i + 1).getMillis() - get(i).getMillis());
		}
		return durations;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DateTime findPrevious(DateTime moment) {
		return super.headSet(moment, false).higher(moment);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DateTime findNext(DateTime moment) {
		return tailSet(moment, false).lower(moment);
	}
}
