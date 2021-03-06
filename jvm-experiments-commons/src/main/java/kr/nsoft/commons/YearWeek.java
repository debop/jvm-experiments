package kr.nsoft.commons;

import com.google.common.base.Objects;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 년차 (Week of Year) 를 나타냅니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 12
 */
@Slf4j
public class YearWeek extends ValueObjectBase implements IYearWeek, Comparable<YearWeek> {

    private static final long serialVersionUID = -5529645755326276780L;

    public static final YearWeek MIN_VALUE = new YearWeek();

    @Getter
    @Setter
    private int year;
    @Getter
    @Setter
    private int week;

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
    public int hashCode() {
        return year * 100 + week;
    }

    @Override
    protected Objects.ToStringHelper buildStringHelper() {
        return super.buildStringHelper()
                .add("year", year)
                .add("week", week);
    }
}
