package kr.kth.commons.timeperiod;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import kr.kth.commons.base.Guard;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

/**
 * Date 를 요소로 가지는 {@link TreeSet[Date]} 입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 2.
 */
@Slf4j
public class DateSet extends TreeSet<Date> implements IDateSet {

	private static final long serialVersionUID = 3251868222462713969L;

	public DateSet() {}

	public DateSet(Collection<Date> moments) {
		super(moments);
	}

	/**
	 * {@inheritDoc}
	 */
	public Date get(int index) {
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
	public Date getMin() {
		return (isEmpty() ? null : first());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Date getMax() {
		return (isEmpty() ? null : last());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long getDuration() {
		if (isEmpty())
			return null;
		Date min = getMin();
		Date max = getMax();

		return (min != null && max != null) ? (max.getTime() - min.getTime()) : null;
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
		Date min = getMin();
		Date max = getMax();

		return (min != null) &&
			(min.getTime() == TimeSpec.ZeroTick) &&
			(max != null) &&
			(max.getTime() == TimeSpec.ZeroTick);
	}

	@Override
	public boolean addAll(Iterable<? extends Date> moments) {
		for (Date moment : moments)
			super.add(moment);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Timestamp> getDurations(int startIndex, int count) {

		int endIndex = Math.min(startIndex + count, size() - 1);
		List<Timestamp> durations = Lists.newArrayList();

		for (int i = startIndex; i < endIndex; i++) {
			durations.add(new Timestamp(get(i + 1).getTime() - get(i).getTime()));
		}
		return durations;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Date findPrevious(Date moment) {
		return super.headSet(moment, false).higher(moment);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Date findNext(Date moment) {
		return tailSet(moment, false).lower(moment);
	}
}
