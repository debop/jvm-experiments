package kr.kth.commons.timeperiod;

import com.google.common.base.Objects;
import kr.kth.commons.base.Guard;
import kr.kth.commons.base.ValueObjectBase;
import lombok.Getter;
import lombok.Setter;

/**
 * 년/월 (Year/Month) 를 표현하는 클래스입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 26.
 */
public class YearAndMonth extends ValueObjectBase implements Comparable<YearAndMonth> {

	private static final long serialVersionUID = -4306646338050532714L;

	@Getter @Setter private Integer year;
	@Getter @Setter private Integer month;

	public YearAndMonth(Integer year, Integer month) {
		this.year = year;
		this.month = month;
	}

	@Override
	public int compareTo(YearAndMonth other) {
		Guard.shouldNotBeNull(other, "other");
		return this.hashCode() - other.hashCode();
	}

	@Override
	public int hashCode() {
		int yearNo = (year != null) ? year : 0;
		int monthNo = (month != null) ? month : 0;

		return yearNo * 100 + monthNo;
	}

	@Override
	protected Objects.ToStringHelper buildStringHelper() {
		return super.buildStringHelper()
		            .add("year", year)
		            .add("month", month);
	}


}
