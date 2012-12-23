package kr.kth.commons.timeperiod.timerange;

import kr.kth.commons.timeperiod.ITimePeriod;
import kr.kth.commons.timeperiod.ITimeRange;
import kr.kth.commons.timeperiod.TimePeriodBase;
import org.joda.time.DateTime;

/**
 * Time 기준으로 기간을 표현합니다. 단 하한(시작시각) 과 상한(완료시각) 은 Open 일 수 있습니다. 즉 개구간일 수 있습니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18.
 */
public class TimeRange extends TimePeriodBase implements ITimeRange {

	private static final long serialVersionUID = -8743222521934888987L;

	public static final TimeRange Anytime = new TimeRange(true);

	// region << Constructors >>

	public TimeRange() {
		super();
	}

	public TimeRange(boolean readonly) {
		super(readonly);
	}

	public TimeRange(DateTime moment) {
		super(moment, false);
	}

	public TimeRange(DateTime moment, boolean readonly) {
		super(moment, readonly);
	}

	public TimeRange(DateTime start, DateTime end) {
		super(start, end, false);
	}

	public TimeRange(DateTime start, DateTime end, boolean readonly) {
		super(start, end, readonly);
	}

	public TimeRange(DateTime start, long duration) {
		super(start, duration, false);
	}

	public TimeRange(DateTime start, long duration, boolean readonly) {
		super(start, duration, readonly);
	}

	public TimeRange(ITimePeriod src) {
		super(src);
	}

	public TimeRange(ITimePeriod src, boolean readonly) {
		super(src, readonly);
	}

	// endregion

	@Override
	public void setStart(DateTime start) {
		assertMutable();
		this.start = start;
	}

	@Override
	public void setEnd(DateTime end) {
		assertMutable();
		this.end = end;
	}

	@Override
	public void setDuration(long duration) {
		assertMutable();
		this.end = getStart().plus(duration);
	}

	@Override
	public void expandStartTo(DateTime moment) {
		assertMutable();
		this.start = moment;
	}

	@Override
	public void expandEndTo(DateTime moment) {
		assertMutable();
		this.end = moment;
	}

	@Override
	public void expandTo(DateTime moment) {
		assertMutable();

		if (moment.compareTo(start) <= 0)
			setStart(moment);
		else if (moment.compareTo(end) >= 0)
			setEnd(moment);
	}

	@Override
	public void expandTo(ITimePeriod period) {
		assertMutable();
		expandStartTo(period.getStart());
		expandEndTo(period.getEnd());
	}

	@Override
	public void shrinkStartTo(DateTime moment) {
		assertMutable();
		this.start = moment;
	}

	@Override
	public void shrinkEndTo(DateTime moment) {
		assertMutable();
		this.end = moment;
	}

	@Override
	public void shrinkTo(ITimePeriod period) {
		assertMutable();
		shrinkStartTo(period.getStart());
		shrinkEndTo(period.getEnd());
	}
}
