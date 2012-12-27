package kr.kth.commons.timeperiod;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import kr.kth.commons.base.Guard;
import kr.kth.commons.base.SortDirection;
import kr.kth.commons.timeperiod.tools.TimeTool;
import kr.kth.commons.tools.ListTool;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.util.*;

/**
 * {@link ITimePeriod}를 항목으로 가지는 컨테이너를 표현합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 21.
 */
@Slf4j
public class TimePeriodContainer implements ITimePeriodContainer {

	private static final long serialVersionUID = -7661743810786491113L;

	protected final List<ITimePeriod> periods = Lists.newArrayList();

	protected TimePeriodContainer() {}

	protected TimePeriodContainer(Iterable<? extends ITimePeriod> periods) {
		this.periods.addAll(Lists.newArrayList(periods));
	}

	protected TimePeriodContainer(ITimePeriod... periods) {
		this.periods.addAll(Lists.newArrayList(periods));
	}


	@Override
	public void setStart(DateTime start) {
		if (size() > 0)
			move(start.getMillis() - getStart().getMillis());
	}

	@Override
	public void setEnd(DateTime end) {
		if (size() > 0)
			move(getEnd().getMillis() - end.getMillis());
	}

	@Override
	public boolean containsPeriod(ITimePeriod target) {
		return this.periods.contains(target);
	}

	@Override
	public void addAll(Iterable<? extends ITimePeriod> periods) {
		this.periods.addAll(Lists.newArrayList(periods));
	}

	@Override
	public void sortByStart(SortDirection direction) {
		if (direction == SortDirection.ASC) {
			Collections.sort(periods, new Comparator<ITimePeriod>() {
				@Override
				public int compare(ITimePeriod o1, ITimePeriod o2) {
					return o1.getStart().compareTo(o2.getStart());
				}
			});
		} else {
			Collections.sort(periods, new Comparator<ITimePeriod>() {
				@Override
				public int compare(ITimePeriod o1, ITimePeriod o2) {
					return o2.getStart().compareTo(o1.getStart());
				}
			});
		}
	}

	@Override
	public void sortByEnd(SortDirection direction) {
		if (direction == SortDirection.ASC) {
			Collections.sort(periods, new Comparator<ITimePeriod>() {
				@Override
				public int compare(ITimePeriod o1, ITimePeriod o2) {
					return o1.getEnd().compareTo(o2.getEnd());
				}
			});
		} else {
			Collections.sort(periods, new Comparator<ITimePeriod>() {
				@Override
				public int compare(ITimePeriod o1, ITimePeriod o2) {
					return o2.getStart().compareTo(o1.getEnd());
				}
			});
		}
	}

	@Override
	public void sortByDuration(SortDirection direction) {
		if (direction == SortDirection.ASC) {
			Collections.sort(periods, new Comparator<ITimePeriod>() {
				@Override
				public int compare(ITimePeriod o1, ITimePeriod o2) {
					return o1.getDuration().compareTo(o2.getDuration());
				}
			});
		} else {
			Collections.sort(periods, new Comparator<ITimePeriod>() {
				@Override
				public int compare(ITimePeriod o1, ITimePeriod o2) {
					return o2.getDuration().compareTo(o1.getDuration());
				}
			});
		}
	}

	@Override
	public DateTime getStart() {
		if (size() > 0) {
			List<DateTime> starts =
				Lists.transform(periods,
				                new Function<ITimePeriod, DateTime>() {
					                @Override
					                public DateTime apply(ITimePeriod input) {
						                return input.getStart();
					                }
				                });
			return (DateTime) ListTool.min(starts);
		}

		return TimeSpec.MinPeriodTime;
	}

	@Override
	public DateTime getEnd() {
		if (size() > 0) {
			List<DateTime> ends =
				Lists.transform(periods,
				                new Function<ITimePeriod, DateTime>() {
					                @Override
					                public DateTime apply(ITimePeriod input) {
						                return input.getEnd();
					                }
				                });
			return (DateTime) ListTool.max(ends);
		}
		return TimeSpec.MaxPeriodTime;
	}

	@Override
	public Long getDuration() {
		return hasPeriod() ? (getEnd().getMillis() - getStart().getMillis())
		                   : TimeSpec.MaxPeriodDuration;
	}

	@Override
	public String getDurationDescription() {
		return TimeFormatter.getInstance().getDuration(getDuration(), DurationFormatKind.Detailed);
	}

	@Override
	public boolean hasStart() {
		return getStart() != TimeSpec.MinPeriodTime;
	}

	@Override
	public boolean hasEnd() {
		return getEnd() != TimeSpec.MaxPeriodTime;
	}

	@Override
	public boolean hasPeriod() {
		return hasStart() && hasEnd();
	}

	@Override
	public boolean isMoment() {
		return hasPeriod() && Objects.equals(getStart(), getEnd());
	}

	@Override
	public boolean isAnyTime() {
		return !hasStart() && !hasEnd();
	}

	@Override
	public boolean isReadonly() {
		return false;
	}

	@Override
	public void setup(DateTime start, DateTime end) {
		throw new IllegalStateException("TimePeriodContainer는 setup() 메소드를 지원하지 않습니다.");
	}

	@Override
	public ITimePeriod copy(long offset) {
		throw new IllegalStateException("TimePeriodContainer는 copy(long offset) 메소드를 지원하지 않습니다.");
	}

	@Override
	public void move(long offset) {
		if (offset == 0 || size() == 0) return;

		for (ITimePeriod period : periods)
			period.move(offset);
	}

	@Override
	public boolean isSamePeriod(ITimePeriod that) {
		return (that != null) &&
			       Objects.equals(getStart(), that.getStart()) &&
			       Objects.equals(getEnd(), that.getEnd());
	}

	@Override
	public boolean hasInside(DateTime moment) {
		return TimeTool.hasInside(this, moment);
	}

	@Override
	public boolean hasInside(ITimePeriod that) {
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
		this.periods.clear();
	}

	@Override
	public PeriodRelation getRelation(ITimePeriod that) {
		return TimeTool.getRelation(this, that);
	}

	@Override
	public String getDescription(ITimeFormatter formatter) {
		return format(formatter);
	}

	public String format(ITimeFormatter formatter) {
		if (formatter == null)
			formatter = TimeFormatter.getInstance();

		return formatter.getCollectionPeriod(size(), getStart(), getEnd(), getDuration());
	}

	@Override
	public ITimePeriod getIntersection(ITimePeriod that) {
		return TimeTool.getIntersectionRange(this, that);
	}

	@Override
	public ITimePeriod getUnion(ITimePeriod that) {
		return TimeTool.getUnionRange(this, that);
	}

	@Override
	public int compareTo(ITimePeriod that) {
		Guard.shouldNotBeNull(that, "that");
		return getStart().compareTo(that.getStart());
	}

	@Override
	public int size() {
		return this.periods.size();
	}

	@Override
	public boolean isEmpty() {
		return this.periods.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return this.periods.contains(o);
	}

	@Override
	public Iterator<ITimePeriod> iterator() {
		return this.periods.iterator();
	}

	@Override
	public Object[] toArray() {
		return this.periods.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return this.periods.toArray(a);
	}

	@Override
	public boolean add(ITimePeriod period) {
		return period != null && this.periods.add(period);
	}

	@Override
	public boolean remove(Object o) {
		return o != null && this.periods.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return c != null && this.periods.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends ITimePeriod> c) {
		return c != null && this.periods.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends ITimePeriod> c) {
		return c != null && this.periods.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return c != null && this.periods.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return c != null && this.periods.retainAll(c);
	}

	@Override
	public void clear() {
		this.periods.clear();
	}

	@Override
	public ITimePeriod get(int index) {
		return this.periods.get(index);
	}

	@Override
	public ITimePeriod set(int index, ITimePeriod element) {
		return this.periods.set(index, element);
	}

	@Override
	public void add(int index, ITimePeriod element) {
		this.periods.add(index, element);
	}

	@Override
	public ITimePeriod remove(int index) {
		return this.periods.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return this.periods.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return this.periods.lastIndexOf(o);
	}

	@Override
	public ListIterator<ITimePeriod> listIterator() {
		return this.periods.listIterator();
	}

	@Override
	public ListIterator<ITimePeriod> listIterator(int index) {
		return this.periods.listIterator(index);
	}

	@Override
	public List<ITimePeriod> subList(int fromIndex, int toIndex) {
		return this.periods.subList(fromIndex, toIndex);
	}
}
