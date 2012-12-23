package kr.kth.commons.timeperiod;

import kr.kth.commons.base.Guard;
import kr.kth.commons.timeperiod.timeline.TimeLine;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * {@link ITimePeriod} 기간들을 결합하는 클래스입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 21.
 */
@Slf4j
public class TimePeriodCombiner<T extends ITimePeriod> {

	@Getter
	private final ITimePeriodMapper periodMapper;

	private final Class<T> periodClass;

	public TimePeriodCombiner(Class<T> periodClass) {
		this(periodClass, null);
	}

	public TimePeriodCombiner(Class<T> periodClass, ITimePeriodMapper mapper) {
		Guard.shouldNotBeNull(periodClass, "periodClass");
		this.periodClass = periodClass;
		this.periodMapper = mapper;
	}

	public ITimePeriodCollection CombinePeriods(ITimePeriod... periods) {
		TimePeriodCollection collection = new TimePeriodCollection(periods);
		return new TimeLine(periodClass, collection, periodMapper).combinePeriods();
	}

	public ITimePeriodCollection combinePeriods(ITimePeriodContainer periods) {
		return new TimeLine(periodClass, periods, periodMapper).combinePeriods();
	}
}
