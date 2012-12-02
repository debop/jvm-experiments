package kr.kth.commons;

import com.google.common.base.Objects;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 년차 (Week of Year) 를 나타냅니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 12
 */
public class YearWeek implements Serializable, Comparable<YearWeek> {

	private static final long serialVersionUID = -5529645755326276780L;

	public static final YearWeek MIN_VALUE = new YearWeek();

	@Getter @Setter private int year;
	@Getter @Setter private int week;

	public YearWeek() {
		this(0, 1);
	}

	/**
	 * @param year 해당 년도
	 * @param week 해당 주차 (1 ~ 53)
	 */
	public YearWeek(Integer year, Integer week) {
		this.year = (int) Objects.firstNonNull(year, 0);
		this.week = (int) Objects.firstNonNull(week, 1);
	}

	public YearWeek(YearWeek src) {
		this.year = src.year;
		this.week = src.week;
	}

	@Override
	public int compareTo(YearWeek that) {
		if (this.year != that.year)
			return this.year - that.year;
		return this.week - that.week;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (o == null) return false;
		if (!(o instanceof YearWeek)) {
			return false;
		}
		YearWeek that = (YearWeek) o;
		return (this.year == that.year) && (this.week == that.week);
	}

	@Override
	public int hashCode() {
		return year * 100 + week;
	}

	@Override
	public String toString() {
		return
			Objects.toStringHelper(this)
			       .add("year", year)
			       .add("week", week)
			       .toString();
	}
}
