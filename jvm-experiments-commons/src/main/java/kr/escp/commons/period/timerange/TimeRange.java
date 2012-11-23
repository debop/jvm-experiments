package kr.escp.commons.period.timerange;

import kr.escp.commons.period.ITimePeriod;
import kr.escp.commons.period.ITimeRange;
import kr.escp.commons.period.TimePeriodBase;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Time 기준으로 기간을 표현합니다. 단 하한(시작시각) 과 상한(완료시각) 은 Open 일 수 있습니다. 즉 개구간일 수 있습니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18.
 */
public class TimeRange extends TimePeriodBase implements ITimeRange {

	private static final long serialVersionUID = -8743222521934888987L;

	public static final ITimeRange Anytime = new TimeRange(true);

	public TimeRange() {
		super();
	}

	public TimeRange(boolean readonly) {
		super(readonly);
	}

	public TimeRange(Date moment) {
		super(moment, false);
	}

	public TimeRange(Date moment, boolean readonly) {
		super(moment, readonly);
	}

	public TimeRange(Date start, Date end) {
		super(start, end, false);
	}

	public TimeRange(Date start, Date end, boolean readonly) {
		super(start, end, readonly);
	}

	public TimeRange(Date start, Timestamp duration) {
		super(start, duration, false);
	}

	public TimeRange(Date start, Timestamp duration, boolean readonly) {
		super(start, duration, readonly);
	}

	public TimeRange(ITimePeriod src) {
		super(src);
	}

	@Override
	public void setStart(Date start) {
		assertMutable();
		this.start = start;
	}

	@Override
	public void setEnd(Date end) {
		assertMutable();
		this.end = end;
	}

	@Override
	public void setDuration(Timestamp duration) {
		assertMutable();
		this.end = new Date(start.getTime() + duration.getTime());
	}

	@Override
	public void expandStartTo(Date moment) {
		assertMutable();
		this.start = moment;
	}

	@Override
	public void expandEndTo(Date moment) {
		assertMutable();
		this.end = moment;
	}

	@Override
	public void expandTo(Date moment) {
		assertMutable();
		if (moment.getTime() <= start.getTime())
			setStart(moment);
		else if (moment.getTime() >= end.getTime())
			setEnd(moment);
		//setEnd(moment);
	}

	@Override
	public void expandTo(ITimePeriod period) {
		assertMutable();
		expandStartTo(period.getStart());
		expandEndTo(period.getEnd());
	}

	@Override
	public void shrinkStartTo(Date moment) {
		assertMutable();
		this.start = moment;
	}

	@Override
	public void shrinkEndTo(Date moment) {
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
