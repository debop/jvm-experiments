package kr.kth.commons.timeperiod.timerange;

import com.google.common.base.Objects;
import kr.kth.commons.base.Guard;
import kr.kth.commons.timeperiod.*;
import kr.kth.commons.timeperiod.tools.TimeTool;
import kr.kth.commons.tools.HashTool;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import static kr.kth.commons.base.Guard.shouldNotBeNull;

/**
 * {@link ITimeCalendar} 기준의 기간을 나타냅니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 23.
 */
@Slf4j
public abstract class CalendarTimeRange extends TimeRange implements ICalendarTimeRange {

	private static final long serialVersionUID = 3653199102426865862L;

	private static TimeRange toCalendarTimeRange(ITimePeriod period, ITimePeriodMapper mapper) {
		shouldNotBeNull(period, "period");
		if (mapper == null)
			mapper = new TimeCalendar();

		DateTime mappedStart = mapper.mapStart(period.getStart());
		DateTime mappedEnd = mapper.mapEnd(period.getEnd());

		TimeTool.assertValidPeriod(mappedStart, mappedEnd);
		TimeRange mapped = new TimeRange(mappedStart, mappedEnd);

		if (log.isDebugEnabled())
			log.debug("TimeCalendar 매퍼를 이용하여 기간을 매핑했습니다. period=[{}], mapped=[{}]", period, mapped);

		return mapped;
	}

	private final ITimeCalendar timeCalendar;

	// region << constructors >>

	protected CalendarTimeRange(DateTime start, DateTime end) {
		this(start, end, new TimeCalendar());
	}

	protected CalendarTimeRange(DateTime start, DateTime end, ITimeCalendar timeCalendar) {
		this(new TimeRange(start, end), timeCalendar);
	}

	protected CalendarTimeRange(DateTime start, long duration) {
		this(new TimeRange(start, duration), new TimeCalendar());
	}

	protected CalendarTimeRange(DateTime start, long duration, ITimeCalendar timeCalendar) {
		this(new TimeRange(start, duration), timeCalendar);
	}

	protected CalendarTimeRange(ITimePeriod period) {
		this(period, new TimeCalendar());
	}

	protected CalendarTimeRange(ITimePeriod period, ITimeCalendar timeCalendar) {
		super(toCalendarTimeRange(period, timeCalendar), true);
		this.timeCalendar = timeCalendar;
	}

	// endregion

	@Override
	public ITimeCalendar getTimeCalendar() {
		return timeCalendar;
	}

	public int getStartYear() {
		return getStart().getYear();
	}

	public int getStartMonth() {
		return getStart().getMonthOfYear();
	}

	public int getStartDay() {
		return getStart().getDayOfMonth();
	}

	public int getStartHour() {
		return getStart().getHourOfDay();
	}

	public int getStartMinute() {
		return getStart().getMinuteOfHour();
	}


	public int getEndYear() {
		return getEnd().getYear();
	}

	public int getEndMonth() {
		return getEnd().getMonthOfYear();
	}

	public int getEndDay() {
		return getStart().getDayOfMonth();
	}

	public int getEndHour() {
		return getStart().getHourOfDay();
	}

	public int getEndMinute() {
		return getStart().getMinuteOfHour();
	}

	public DateTime getMappedStart() {
		return timeCalendar.mapStart(getStart());
	}

	public DateTime getMappedEnd() {
		return timeCalendar.mapEnd(getEnd());
	}

	public DateTime getUnmappedStart() {
		return timeCalendar.unmapStart(getStart());
	}

	public DateTime getUnmappedEnd() {
		return timeCalendar.unmapEnd(getEnd());
	}

	public DateTime getStartMonthStart() {
		return TimeTool.trimToDay(getStart());
	}

	public DateTime getEndMonthStart() {
		return TimeTool.trimToDay(getEnd());
	}

	public DateTime getStartDayStart() {
		return TimeTool.trimToHour(getStart());
	}

	public DateTime getEndDayEnd() {
		return TimeTool.trimToHour(getEnd());
	}

	public DateTime getStartHourStart() {
		return TimeTool.trimToMinute(getStart());
	}

	public DateTime getEndHourEnd() {
		return TimeTool.trimToMinute(getEnd());
	}

	public DateTime getStartMinuteStart() {
		return TimeTool.trimToSecond(getStart());
	}

	public DateTime getEndMinuteEnd() {
		return TimeTool.trimToSecond(getEnd());
	}

	public DateTime getStartSecondStart() {
		return TimeTool.trimToMillis(getStart());
	}

	public DateTime getEndSecondEnd() {
		return TimeTool.trimToMillis(getEnd());
	}

	@Override
	public ITimePeriod copy(long offset) {
		return toCalendarTimeRange(super.copy(offset), timeCalendar);
	}

	@Override
	protected String format(ITimeFormatter formatter) {
		return formatter.getCalendarPeriod(formatter.getDate(getStart()),
		                                   formatter.getDate(getEnd()),
		                                   getDuration());
	}

	public int compareTo(CalendarTimeRange other) {
		return getStart().compareTo(other.getStart());
	}

	@Override
	public int compareTo(ITimePeriod o) {
		Guard.shouldNotBeNull(o, "o");

		if (o instanceof CalendarTimeRange)
			return compareTo((CalendarTimeRange) o);

		return 1;
	}


	@Override
	public boolean equals(Object other) {
		return (other != null) && (other instanceof ICalendarTimeRange) && hashCode() == other.hashCode();
	}

	@Override
	public int hashCode() {
		return HashTool.compute(super.hashCode(), timeCalendar);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
		              .add("description", getDescription(null))
		              .add("timeCalendar", timeCalendar)
		              .toString();
	}
}
