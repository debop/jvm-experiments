package kr.kth.commons.timeperiod;

import com.google.common.base.Objects;
import kr.kth.commons.base.ValueObjectBase;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * kr.kth.commons.timeperiod.YearAndHalfYear
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 23.
 */
@Slf4j
public class YearAndHalfYear extends ValueObjectBase implements Comparable<YearAndHalfYear> {

	private static final long serialVersionUID = 4272394545140829436L;

	@Getter @Setter
	private Integer year;
	@Getter @Setter
	HalfYearKind halfyear;

	public YearAndHalfYear() {}

	public YearAndHalfYear(Integer year, HalfYearKind halfyear) {
		this.year = year;
		this.halfyear = halfyear;
	}

	public YearAndHalfYear(Integer year, Integer halfyear) {
		this.year = year;
		if (halfyear != null) {
			switch (halfyear) {
				case 1:
					this.halfyear = HalfYearKind.First;
					break;
				case 2:
					this.halfyear = HalfYearKind.Second;
					break;
			}
		}
	}

	@Override
	public int compareTo(YearAndHalfYear o) {
		return hashCode() - o.hashCode();
	}

	@Override
	public int hashCode() {
		return ((year != null) ? year : 0) * 100 + ((halfyear != null) ? halfyear.getValue() : 0);
	}

	@Override
	protected Objects.ToStringHelper buildStringHelper() {
		return super.buildStringHelper()
		            .add("year", year)
		            .add("halfyear", halfyear);
	}
}
