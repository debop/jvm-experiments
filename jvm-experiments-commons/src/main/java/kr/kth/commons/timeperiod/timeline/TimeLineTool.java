package kr.kth.commons.timeperiod.timeline;

import kr.kth.commons.base.Guard;
import kr.kth.commons.timeperiod.*;
import kr.kth.commons.tools.ActivatorTool;
import lombok.extern.slf4j.Slf4j;

import static kr.kth.commons.tools.StringTool.listToString;

/**
 * {@link ITimeLine}, {@link ITimeLineMoment}, {@link ITimeLineMomentCollection}에 대한 메소드를 제공합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 21.
 */
@Slf4j
public final class TimeLineTool {

	private TimeLineTool() {}

	/**
	 * {@link ITimeLineMomentCollection}의 모든 기간의 합집합을 구합니다.
	 *
	 * @param periodClass
	 * @param timeLineMoments
	 * @param <T>
	 * @return
	 */
	public static <T extends ITimePeriod> ITimePeriodCollection combinePeriods(Class<T> periodClass,
	                                                                           ITimeLineMomentCollection timeLineMoments) {
		if (log.isDebugEnabled())
			log.debug("ITimeLineMoment 컬렉션의 모든 기간의 합집합을 구합니다...");

		TimePeriodCollection periods = new TimePeriodCollection();
		if (timeLineMoments.isEmpty()) {
			return periods;
		}

		// search for periods
		int itemIndex = 0;
		while (itemIndex < timeLineMoments.size()) {
			ITimeLineMoment periodStart = timeLineMoments.get(itemIndex);

			Guard.shouldBe(periodStart.getStartCount() != 0,
			               "getStartCount() 값은 [0]이 아니여야 합니다. periodStart.getStartCount()=[%s]",
			               periodStart.getStartCount());

			// search next period end
			// use balancing to handle overlapping periods
			int balance = periodStart.getStartCount();
			ITimeLineMoment periodEnd = null;
			while (itemIndex < timeLineMoments.size() - 1 && balance > 0) {
				itemIndex++;
				periodEnd = timeLineMoments.get(itemIndex);
				balance += periodEnd.getStartCount();
				balance -= periodEnd.getEndCount();
			}

			Guard.shouldNotBeNull(periodEnd, "periodEnd");
			if (periodEnd != null && periodEnd.getStartCount() == 0) {
				if (itemIndex < timeLineMoments.size()) {
					ITimePeriod period = ActivatorTool.createInstance(periodClass);
					period.setup(periodStart.getMoment(), periodEnd.getMoment());

					if (log.isDebugEnabled())
						log.debug("Combine period를 추가합니다. period=[{}]", period);

					periods.add(period);
				}
			}

			itemIndex++;
		}

		if (log.isDebugEnabled())
			log.debug("기간들을 결합했습니다. periods=[{}]", listToString(periods));

		return periods;
	}

	/**
	 * {@link ITimeLineMomentCollection} 으로부터 교집합에 해당하는 기간들을 구합니다.
	 *
	 * @param periodClass
	 * @param timeLineMoments
	 * @param <T>
	 * @return
	 */
	public static <T extends ITimePeriod> ITimePeriodCollection intersectPeriods(Class<T> periodClass,
	                                                                             ITimeLineMomentCollection timeLineMoments) {
		if (log.isDebugEnabled())
			log.debug("ItimeLineMomentCollection으로부터 교집합에 해당하는 기간들을 구합니다.");

		TimePeriodCollection periods = new TimePeriodCollection();

		if (timeLineMoments.isEmpty())
			return periods;

		// search periods
		int intersectionStart = -1;
		int balance = 0;
		for (int i = 0; i < timeLineMoments.size(); i++) {
			ITimeLineMoment moment = timeLineMoments.get(i);
			balance += moment.getStartCount();
			balance -= moment.getEndCount();

			// intersection is starting by a period start
			if (moment.getStartCount() > 0 && balance > 1 && intersectionStart < 0) {
				intersectionStart = i;
				continue;
			}

			if (moment.getEndCount() > 0 && balance <= 1 && intersectionStart >= 0) {
				ITimePeriod period = ActivatorTool.createInstance(periodClass);
				period.setup(timeLineMoments.get(intersectionStart).getMoment(), moment.getMoment());

				if (log.isDebugEnabled())
					log.debug("Intersection period를 추가합니다. period=[{}]", period);

				periods.add(period);
				intersectionStart = -1;
			}
		}

		if (log.isDebugEnabled())
			log.debug("ITimeLineMomentCollection으로부터 교집합에 해당하는 기간을 구했습니다. periods=[{}]", listToString(periods));

		return periods;
	}

	/**
	 * {@link ITimeLineMomentCollection}의 모든 기간에 속하지 않는 Gap 들을 찾아냅니다.
	 *
	 * @param periodClass
	 * @param timeLineMoments
	 * @param range
	 * @param <T>
	 * @return
	 */
	public static <T extends ITimePeriod> ITimePeriodCollection calculateCaps(Class<T> periodClass,
	                                                                          ITimeLineMomentCollection timeLineMoments,
	                                                                          ITimePeriod range) {
		if (log.isDebugEnabled())
			log.debug("ITimeLineMomentCollection의 Gap을 계산합니다...");

		Guard.shouldNotBeNull(timeLineMoments, "timeLineMoments");

		TimePeriodCollection gaps = new TimePeriodCollection();

		if (timeLineMoments.isEmpty())
			return gaps;

		// find leading gap
		//
		ITimeLineMoment periodStart = timeLineMoments.getMin();
		if (periodStart != null && range.getStart().compareTo(periodStart.getMoment()) < 0) {
			ITimePeriod startingGap = ActivatorTool.createInstance(periodClass);
			startingGap.setup(range.getStart(), periodStart.getMoment());

			if (log.isDebugEnabled())
				log.debug("Starting Gap을 추가합니다... startingGap=[{}]", startingGap);

			gaps.add(startingGap);
		}

		// find intermediated gap
		//
		int itemIndex = 0;
		while (itemIndex < timeLineMoments.size()) {
			ITimeLineMoment moment = timeLineMoments.get(itemIndex);

			Guard.shouldBe(moment.getStartCount() != 0,
			               "moment.getStartCount() 값은 [0] 이 아니어야 합니다. moment=[%s]", moment);

			// search next gap start
			// use balancing to handle overlapping periods
			int balance = moment.getStartCount();
			ITimeLineMoment gapStart = null;
			while (itemIndex < timeLineMoments.size() - 1 && balance > 0) {
				itemIndex++;
				gapStart = timeLineMoments.get(itemIndex);
				balance += gapStart.getStartCount();
				balance -= gapStart.getEndCount();
			}

			Guard.shouldNotBeNull(gapStart, "gapStart");

			// not touching
			if (gapStart != null && gapStart.getStartCount() == 0) {
				// found a gap
				if (itemIndex < timeLineMoments.size() - 1) {
					ITimePeriod gap = ActivatorTool.createInstance(periodClass);
					gap.setup(gapStart.getMoment(), timeLineMoments.get(itemIndex + 1).getMoment());

					if (log.isDebugEnabled())
						log.debug("intermediated gap 을 추가합니다. gap=[{}]", gap);

					gaps.add(gap);
				}
			}

			itemIndex++;
		}

		// find ending gap
		//
		ITimeLineMoment periodEnd = timeLineMoments.getMax();

		if (periodEnd != null && range.getEnd().compareTo(periodEnd.getMoment()) > 0) {

			ITimePeriod endingGap = ActivatorTool.createInstance(periodClass);
			endingGap.setup(periodEnd.getMoment(), range.getEnd());

			if (log.isDebugEnabled())
				log.debug("Ending Gap을 추가합니다. endingGap=[{}]", endingGap);

			gaps.add(endingGap);
		}

		if (log.isDebugEnabled())
			log.debug("기간들의 gap에 해당하는 부분을 계산했습니다!!! gaps=[{}]", listToString(gaps));

		return gaps;
	}
}
