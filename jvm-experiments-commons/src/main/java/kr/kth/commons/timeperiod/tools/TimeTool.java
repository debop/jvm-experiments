package kr.kth.commons.timeperiod.tools;

import com.google.common.collect.Lists;
import kr.kth.commons.base.Guard;
import kr.kth.commons.timeperiod.ITimePeriod;
import kr.kth.commons.timeperiod.PeriodRelation;
import kr.kth.commons.timeperiod.TimeBlock;
import kr.kth.commons.timeperiod.timerange.TimeRange;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.*;

/**
 * 설명을 추가하세요.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
@Slf4j
public final class TimeTool {

	private TimeTool() {}

	private static final boolean isDebugEnabled = log.isDebugEnabled();

	private static final String NullStr = "null";
	private static final Date UnixEpoch = new Date(0);


	public static DateTime getNow() {
		return DateTime.now();
	}

	public static Long getNowTime() {
		return DateTime.now().getMillis();
	}

	public static Timestamp getNowTimestamp() {
		return new Timestamp(getNowTime());
	}

	public static String asString(ITimePeriod period) {
		return (period == null) ? TimeTool.NullStr : period.getDurationDescription();
	}

	public static Date toDate(String value) {
		return toDate(value, UnixEpoch);
	}

	public static Date toDate(String value, Date defaultValue) {
		try {
			return DateFormat.getInstance().parse(value);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	// region << Iteration >>

	public static List<? extends ITimePeriod> forEachYears(ITimePeriod period) {
		if (isDebugEnabled)
			log.debug("기간[{}]에 대해 Year 단위로 열거합니다", period);

		if (period == null || period.isAnyTime())
			return Lists.newArrayList();

		assertHasPeriod(period);

		if (period.getStart().getYear() == period.getEnd().getYear()) {
			return Lists.newArrayList(new TimeRange(period.getStart(), period.getEnd()));
		}

		List<ITimePeriod> years = Lists.newArrayList();

		return years;
	}


	// endregion

	// region << Math >>

	public static Date min(Date a, Date b) {
		if (a != null && b != null) {
			return (a.compareTo(b) < 0) ? a : b;
		}
		if (a != null)
			return a;
		return b;
	}

	public static Date max(Date a, Date b) {
		if (a != null && b != null) {
			return (a.compareTo(b) > 0) ? a : b;
		}
		if (a != null)
			return a;
		return b;
	}

	public static DateTime min(DateTime a, DateTime b) {
		if (a != null && b != null)
			return (a.compareTo(b) < 0) ? a : b;
		if (a != null)
			return a;
		return b;
	}

	public static DateTime max(DateTime a, DateTime b) {
		if (a != null && b != null)
			return (a.compareTo(b) > 0) ? a : b;
		if (a != null)
			return a;
		return b;
	}

	public static void adjustPeriod(Date start, Date end) {
		if (start != null && end != null) {
			if (start.compareTo(end) > 0) {
				Date temp = start;
				start = end;
				end = temp;
			}
		}
	}

	public static void adjustPeriod(Date start, Long duration) {
		if (start == null || duration == null)
			return;

		if (duration < 0) {
			start = new Date(start.getTime() + duration);
			duration = -duration;
		}
	}

	public static void adjustPeriod(DateTime start, DateTime end) {
		if (start == null || end == null)
			return;

		if (start.compareTo(end) > 0) {
			DateTime temp = start;
			start = end;
			end = temp;
		}
	}

	public static void adjustPeriod(DateTime start, Long duration) {
		if (start == null || duration == null)
			return;
		if (duration < 0) {
			start = start.plus(duration);
			duration = -duration;
		}
	}

	// endregion

	// region << Relation >>

	public static boolean hasInside(ITimePeriod period, DateTime target) {
		Guard.shouldNotBeNull(period, "period");
		Guard.shouldNotBeNull(target, "target");

		boolean hasInside = target.compareTo(period.getStart()) >= 0 &&
			target.compareTo(period.getEnd()) <= 0;

		if (isDebugEnabled)
			log.debug("기간[{}]에 대상날짜[{}]가 포함(Inside)되는지 여부를 검사. hasInside=[{}]",
			          asString(period), target, hasInside);

		return hasInside;
	}

	public static boolean hasInside(ITimePeriod period, ITimePeriod target) {
		return hasInside(period, target.getStart()) && hasInside(period, target.getEnd());
	}

	public static boolean isAnytime(ITimePeriod period) {
		return period != null && period.isAnyTime();
	}

	public static boolean isNotAnytime(ITimePeriod period) {
		return period != null && !period.isAnyTime();
	}

	/**
	 * 두 기간이 어떤 관계인지 파악합니다. (선/후/중첩 등)
	 */
	public static PeriodRelation getRelation(ITimePeriod period, ITimePeriod target) {
		Guard.shouldNotBeNull(period, "period");
		Guard.shouldNotBeNull(target, "target");

		PeriodRelation relation = PeriodRelation.NoRelation;

		if (period.getStart().compareTo(target.getEnd()) > 0) {
			relation = PeriodRelation.After;
		} else if (period.getEnd().compareTo(target.getStart()) < 0) {
			relation = PeriodRelation.Before;
		} else if (period.getStart().equals(target.getStart()) && period.getEnd().equals(target.getEnd())) {
			relation = PeriodRelation.ExactMatch;
		} else if (period.getStart().equals(target.getEnd())) {
			relation = PeriodRelation.StartTouching;
		} else if (period.getEnd().equals(target.getStart())) {
			relation = PeriodRelation.EndTouching;
		} else if (hasInside(period, target)) {
			if (period.getStart().equals(target.getStart())) {
				relation = PeriodRelation.EnclosingStartTouching;
			} else {
				relation = (period.getEnd().equals(target.getEnd()))
				           ? PeriodRelation.EnclosingEndTouching
				           : PeriodRelation.Enclosing;
			}
		} else {
			boolean insideStart = hasInside(target, period.getStart());
			boolean insideEnd = hasInside(target, period.getEnd());

			if (insideStart && insideEnd) {
				relation = period.getStart().equals(target.getStart())
				           ? PeriodRelation.InsideStartTouching
				           : (period.getEnd().equals(target.getEnd()))
				             ? PeriodRelation.InsideEndTouching
				             : PeriodRelation.Inside;
			} else if (insideStart) {
				relation = PeriodRelation.StartInside;
			} else if (insideEnd) {
				relation = PeriodRelation.EndInside;
			}
		}

		if (isDebugEnabled)
			log.debug("period[{}], target[{}] 의 relation=[{}] 입니다.", asString(period), asString(target), relation);

		return relation;
	}

	/**
	 * 두 기간의 교집합에 해당하는 기간이 있는지 검사한다.
	 */
	public static boolean intersectsWith(ITimePeriod period, ITimePeriod target) {
		Guard.shouldNotBeNull(period, "period");
		Guard.shouldNotBeNull(target, "target");

		boolean isIntersected = hasInside(period, target.getStart()) ||
			hasInside(period, target.getEnd()) ||
			(target.getStart().compareTo(period.getStart()) < 0 &&
				target.getEnd().compareTo(period.getEnd()) > 0);
		if (isDebugEnabled)
			log.debug("period=[{}], target=[{}]에 교차구간이 있는지 확인합니다. isIntersected=[{}]",
			          asString(period), asString(target), isIntersected);

		return isIntersected;
	}

	/**
	 * 두 기간이 겹치는 기간이 있는지 검사한다.
	 */
	public static boolean overlapsWith(ITimePeriod period, ITimePeriod target) {
		Guard.shouldNotBeNull(period, "period");
		Guard.shouldNotBeNull(target, "target");

		PeriodRelation relation = getRelation(period, target);

		boolean isOverlaps = relation != PeriodRelation.After &&
			relation != PeriodRelation.StartTouching &&
			relation != PeriodRelation.EndTouching &&
			relation != PeriodRelation.Before &&
			relation != PeriodRelation.NoRelation;

		if (isDebugEnabled)
			log.debug("period=[{}], target=[{}]이 overlap 되는지 확인합니다. isOverlaps=[{}]",
			          asString(period), asString(target), isOverlaps);

		return isOverlaps;
	}

	/**
	 * 두 기간의 교집합을 반환한다.
	 */
	public static TimeBlock getIntersectionBlock(ITimePeriod period, ITimePeriod target) {
		Guard.shouldNotBeNull(period, "period");
		Guard.shouldNotBeNull(target, "target");

		TimeBlock intersectionBlock = null;

		if (intersectsWith(period, target)) {
			DateTime start = max(period.getStart(), target.getStart());
			DateTime end = min(period.getEnd(), target.getEnd());

			intersectionBlock = new TimeBlock(start, end, period.isReadonly());
		}
		if (isDebugEnabled)
			log.debug("period=[{}], target=[{}]의 교집합 TimeBlock=[{}]을 구했습니다.",
			          asString(period), asString(target), asString(intersectionBlock));
		return intersectionBlock;
	}

	public static TimeBlock getUnionBlock(ITimePeriod period, ITimePeriod target) {
		Guard.shouldNotBeNull(period, "period");
		Guard.shouldNotBeNull(target, "target");

		DateTime start = min(period.getStart(), target.getStart());
		DateTime end = max(period.getEnd(), target.getEnd());
		TimeBlock unionBlock = new TimeBlock(start, end, period.isReadonly());

		if (isDebugEnabled)
			log.debug("period=[{}]와 target=[{}]의 합집합 TimeBlock=[{}]을 구했습니다.",
			          asString(period), asString(target), asString(unionBlock));

		return unionBlock;
	}

	/**
	 * 두 기간의 교집합을 구하여 {@link TimeRange} 인스턴스를 빌드하여 반환합니다.
	 */
	public static TimeRange getIntersectionRange(ITimePeriod period, ITimePeriod target) {
		Guard.shouldNotBeNull(period, "period");
		Guard.shouldNotBeNull(target, "target");

		TimeRange intersectionRange = null;

		if (intersectsWith(period, target)) {
			DateTime start = max(period.getStart(), target.getStart());
			DateTime end = min(period.getEnd(), target.getEnd());
			intersectionRange = new TimeRange(start, end, period.isReadonly());
		}

		if (isDebugEnabled)
			log.debug("period=[{}]와 target=[{}]의 교집합 TimeRange=[{}]을 구했습니다.",
			          asString(period), asString(target), asString(intersectionRange));

		return intersectionRange;
	}

	/**
	 * 두 기간의 합집합을 구하여 {@link TimeRange} 인스턴스를 빌드하여 반환합니다.
	 */
	public static TimeRange getUnionRange(ITimePeriod period, ITimePeriod target) {
		Guard.shouldNotBeNull(period, "period");
		Guard.shouldNotBeNull(target, "target");

		TimeRange unionRange = null;

		if (intersectsWith(period, target)) {
			DateTime start = min(period.getStart(), target.getStart());
			DateTime end = max(period.getEnd(), target.getEnd());
			unionRange = new TimeRange(start, end, period.isReadonly());
		}

		if (isDebugEnabled)
			log.debug("period=[{}]와 target=[{}]의 합집합 TimeRange=[{}]을 구했습니다.",
			          asString(period), asString(target), asString(unionRange));

		return unionRange;
	}

	// endregion

	// region << Trim >>

	public static DateTime trimToYear(DateTime moment) {
		return new DateTime().withYear(moment.getYear());
	}

	public static DateTime trimToMonth(DateTime moment) {
		return trimToMonth(moment, moment.getMonthOfYear());
	}

	public static DateTime trimToMonth(DateTime moment, int month) {
		return trimToYear(moment).plusMonths(month);
	}

	public static DateTime trimToDay(DateTime moment) {
		return trimToDay(moment, moment.getDayOfMonth());
	}

	public static DateTime trimToDay(DateTime moment, int dayOfMonth) {
		return trimToMonth(moment, moment.getMonthOfYear()).plusDays(dayOfMonth);
	}

	public static DateTime trimToHour(DateTime moment) {
		return trimToHour(moment, moment.getHourOfDay());
	}

	public static DateTime trimToHour(DateTime moment, int hourOfDay) {
		return trimToDay(moment, moment.getDayOfMonth()).plusHours(hourOfDay);
	}

	public static DateTime trimToMinute(DateTime moment) {
		return trimToMinute(moment, moment.getMinuteOfHour());
	}

	public static DateTime trimToMinute(DateTime moment, int minuteOfHour) {
		return trimToHour(moment, moment.getHourOfDay()).plusMinutes(minuteOfHour);
	}

	public static DateTime trimToSecond(DateTime moment) {
		return trimToSecond(moment, moment.getSecondOfMinute());
	}

	public static DateTime trimToSecond(DateTime moment, int secondsOfMinute) {
		return trimToMinute(moment).plusSeconds(secondsOfMinute);
	}

	public static DateTime trimToMillis(DateTime moment) {
		return trimToSecond(moment, moment.getMillisOfSecond());
	}

	public static DateTime trimToMillis(DateTime moment, int millisOfSecond) {
		return trimToSecond(moment).plusMillis(millisOfSecond);
	}

	// endregion

	// region << Validation >>

	public static void assertHasPeriod(ITimePeriod period) {
		Guard.assertTrue(period != null && period.hasPeriod(), "구체적인 기간이 없습니다. period=[%s]", period);
	}

	public static void assertValidPeriod(DateTime start, DateTime end) {
		if (start != null && end != null)
			Guard.assertTrue(start.compareTo(end) <= 0,
			                 "시작시각이 완료시각보다 이전 시각이어야 합니다. start=[%s], end=[%s]",
			                 start, end);
	}

	public static void assertMutable(ITimePeriod period) {
		Guard.assertTrue(!period.isReadonly(), "TimePeriod 가 읽기전용입니다. period=[%s]", period);
	}

	public static boolean allItemsAreEquals(Collection<? extends ITimePeriod> left,
	                                        Collection<? extends ITimePeriod> right) {
		if (left.size() != right.size())
			return false;

		Iterator<? extends ITimePeriod> leftIter = left.iterator();
		Iterator<? extends ITimePeriod> rightIter = left.iterator();
		while (leftIter.hasNext() && rightIter.hasNext()) {
			if (leftIter.next().equals(rightIter.next()))
				return false;
		}
		return true;
	}

	public static boolean isWeekDay(int dayOfWeek) {
		return !isWeekEnd(dayOfWeek);
	}

	public static boolean isWeekEnd(int dayOfWeek) {
		return (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY);
	}

	// endregion
}
