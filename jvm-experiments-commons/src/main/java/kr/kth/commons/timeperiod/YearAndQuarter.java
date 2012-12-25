package kr.kth.commons.timeperiod;

import com.google.common.base.Objects;
import kr.kth.commons.base.Guard;
import kr.kth.commons.base.ValueObjectBase;
import lombok.Getter;
import lombok.Setter;

/**
 * 년도와 분기를 표현합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 25.
 */
public class YearAndQuarter extends ValueObjectBase implements Comparable<YearAndQuarter> {

	private static final long serialVersionUID = -9136294587041186630L;

	@Getter @Setter private Integer year;
	@Getter @Setter private QuarterKind quarter;

	public YearAndQuarter(Integer year, Integer quarter) {
		this(year, QuarterKind.valueOf(quarter));
	}

	public YearAndQuarter(Integer year, QuarterKind quarter) {
		this.year = year;
		this.quarter = quarter;
	}

	@Override
	public int compareTo(YearAndQuarter o) {
		Guard.shouldNotBeNull(o, "o");
		return this.hashCode() - o.hashCode();
	}

	@Override
	public int hashCode() {
		int yearNo = (year != null) ? year : 0;
		int quarterNo = (quarter != null) ? quarter.intValue() : 0;
		return yearNo * 100 + quarterNo * 25;
	}

	@Override
	protected Objects.ToStringHelper buildStringHelper() {
		return super.buildStringHelper()
		            .add("year", year)
		            .add("quarter", quarter);
	}
}
