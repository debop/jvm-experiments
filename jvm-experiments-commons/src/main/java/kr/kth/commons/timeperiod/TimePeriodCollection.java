package kr.kth.commons.timeperiod;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import kr.kth.commons.timeperiod.tools.TimeTool;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import javax.annotation.Nullable;
import java.util.List;

import static com.google.common.collect.Iterables.any;
import static com.google.common.collect.Iterables.filter;

/**
 * {@link ITimePeriod} 컬렉션입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 21.
 */
@Slf4j
public class TimePeriodCollection extends TimePeriodContainer implements ITimePeriodCollection {

	private static final long serialVersionUID = -4997208034570787108L;

	public TimePeriodCollection() {}

	public TimePeriodCollection(ITimePeriod... periods) {
		super(periods);
	}

	public TimePeriodCollection(Iterable<? extends ITimePeriod> periods) {
		super(periods);
	}

	@Override
	public boolean hasInsidePeriods(final ITimePeriod target) {
		if (target == null) return false;
		boolean result = any(this.periods,
		                     new Predicate<ITimePeriod>() {
			                     @Override
			                     public boolean apply(@Nullable ITimePeriod input) {
				                     return target.hasInside(input);
			                     }
		                     });
		if (log.isDebugEnabled())
			log.debug("target [{}]에 속하는 ITimePeriod 요소가 있는가? [{}]", target, result);

		return result;
	}

	@Override
	public boolean hasOverlapPeriods(final ITimePeriod target) {
		if (target == null) return false;
		boolean result = any(this.periods,
		                     new Predicate<ITimePeriod>() {
			                     @Override
			                     public boolean apply(@Nullable ITimePeriod input) {
				                     return target.overlapsWith(input);
			                     }
		                     });
		if (log.isDebugEnabled())
			log.debug("target [{}]과 overlap 되는 ITimePeriod 요소가 있는가? [{}]", target, result);

		return result;
	}

	@Override
	public boolean hasIntersectionPeriods(final DateTime moment) {
		boolean result = any(this.periods,
		                     new Predicate<ITimePeriod>() {
			                     @Override
			                     public boolean apply(@Nullable ITimePeriod input) {
				                     return input.hasInside(moment);
			                     }
		                     });
		if (log.isDebugEnabled())
			log.debug("moment[{}]과 기간이 교차하는 ITimePeriod 요소가 있는가? [{}]", moment, result);

		return result;
	}

	@Override
	public boolean hasIntersectionPeriods(final ITimePeriod target) {
		boolean result = any(this.periods,
		                     new Predicate<ITimePeriod>() {
			                     @Override
			                     public boolean apply(@Nullable ITimePeriod input) {
				                     return target.intersectsWith(input);
			                     }
		                     });

		if (log.isDebugEnabled())
			log.debug("target [{}]과 교집합(intersection)되는 ITimePeriod 요소가 있는가? [{}]", target, result);

		return result;
	}

	@Override
	public Iterable<ITimePeriod> insidePeriods(final ITimePeriod target) {

		return filter(this.periods,
		              new Predicate<ITimePeriod>() {
			              @Override
			              public boolean apply(@Nullable ITimePeriod input) {
				              return target.hasInside(input);
			              }
		              });
	}

	@Override
	public Iterable<ITimePeriod> overlapPeriods(final ITimePeriod target) {
		return filter(this.periods,
		              new Predicate<ITimePeriod>() {
			              @Override
			              public boolean apply(@Nullable ITimePeriod input) {
				              return target.overlapsWith(input);
			              }
		              });
	}

	@Override
	public Iterable<ITimePeriod> intersectionPeriods(final DateTime moment) {
		return filter(this.periods,
		              new Predicate<ITimePeriod>() {
			              @Override
			              public boolean apply(@Nullable ITimePeriod input) {
				              return TimeTool.hasInside(input, moment);
			              }
		              });
	}

	@Override
	public Iterable<ITimePeriod> intersectionPeriods(final ITimePeriod target) {
		return filter(this.periods,
		              new Predicate<ITimePeriod>() {
			              @Override
			              public boolean apply(@Nullable ITimePeriod input) {
				              return TimeTool.intersectsWith(target, input);
			              }
		              });
	}

	@Override
	public Iterable<ITimePeriod> relationPeriods(final ITimePeriod target, final PeriodRelation relation) {
		return filter(this.periods,
		              new Predicate<ITimePeriod>() {
			              @Override
			              public boolean apply(@Nullable ITimePeriod input) {
				              return TimeTool.getRelation(target, input) == relation;
			              }
		              });
	}

	@Override
	public Iterable<ITimePeriod> relationPeriods(final ITimePeriod target, PeriodRelation... relations) {
		final List<PeriodRelation> filteringRelations = Lists.newArrayList(relations);
		return filter(this.periods,
		              new Predicate<ITimePeriod>() {
			              @Override
			              public boolean apply(@Nullable ITimePeriod input) {
				              return filteringRelations.contains(TimeTool.getRelation(target, input));
			              }
		              });
	}
}
