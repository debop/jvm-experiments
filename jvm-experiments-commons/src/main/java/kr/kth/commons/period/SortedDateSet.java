package kr.kth.commons.period;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import kr.kth.commons.Guard;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

/**
 * kr.kth.commons.period.SortedDateSet
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 2.
 */
@Slf4j
public class SortedDateSet extends TreeSet<Date> implements DateSet {

	private static final long serialVersionUID = 3251868222462713969L;

	public SortedDateSet() {}

	public SortedDateSet(Collection<Date> moments) {
		super(moments);
	}

	public Date get(int index) {
		Guard.assertTrue(index >= 0 && index < size(), "Index 의 범위가 벗어났습니다. index=%d, size()=%d", index, size());

		if (index == 0)
			return first();
		if (index == size() - 1)
			return last();

		return Iterators.get(iterator(), index);
	}

	@Override
	public Date getMin() {
		return (isEmpty() ? null : first());
	}

	@Override
	public Date getMax() {
		return (isEmpty() ? null : last());
	}

	@Override
	public Timestamp getDuration() {
		if (isEmpty())
			return null;
		Date min = getMin();
		Date max = getMax();

		return (min != null && max != null) ? new Timestamp(max.getTime() - max.getTime()) : null;
	}

	@Override
	public boolean isMoment() {
		Timestamp duration = getDuration();
		return (duration != null) && (duration.getTime() == TimeSpec.ZeroTick);
	}

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
	public List<Timestamp> getDurations(int startIndex, int count) {

		int endIndex = Math.min(startIndex + count, size() - 1);
		List<Timestamp> durations = Lists.newArrayList();

		for (int i = startIndex; i < endIndex; i++) {
			durations.add(new Timestamp(get(i + 1).getTime() - get(i).getTime()));
		}
		return durations;
	}

	@Override
	public Date findPrevious(Date moment) {
		return super.headSet(moment, false).higher(moment);
	}

	@Override
	public Date findNext(Date moment) {
		return tailSet(moment, false).lower(moment);
	}
}
