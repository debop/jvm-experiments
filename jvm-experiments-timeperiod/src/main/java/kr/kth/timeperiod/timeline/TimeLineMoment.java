package kr.kth.timeperiod.timeline;

import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import kr.kth.timeperiod.ITimeLineMoment;
import kr.kth.timeperiod.ITimePeriod;
import kr.kth.timeperiod.ITimePeriodCollection;
import kr.kth.timeperiod.TimePeriodCollection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import javax.annotation.Nullable;
import java.util.Iterator;

/**
 * 특정 기준 시각에 대한 필터링을 수행합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 21.
 */
@Slf4j
public class TimeLineMoment implements ITimeLineMoment {

    private static final long serialVersionUID = -4700989258602962868L;

    @Getter
    private final DateTime moment;

    @Getter
    @Setter(value = AccessLevel.PROTECTED)
    private ITimePeriodCollection periods = new TimePeriodCollection();

    public TimeLineMoment(DateTime moment) {
        this.moment = moment;
    }

    @Override
    public int getStartCount() {
        Iterator<ITimePeriod> filtered =
                Iterators.filter(periods.iterator(),
                        new Predicate<ITimePeriod>() {
                            @Override
                            public boolean apply(@Nullable ITimePeriod input) {
                                return input != null && input.getStart() == moment;
                            }
                        });
        return Iterators.size(filtered);
    }

    @Override
    public int getEndCount() {
        Iterator<ITimePeriod> filtered =
                Iterators.filter(periods.iterator(),
                        new Predicate<ITimePeriod>() {
                            @Override
                            public boolean apply(@Nullable ITimePeriod input) {
                                return input != null && input.getEnd() == moment;
                            }
                        });
        return Iterators.size(filtered);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("moment", moment)
                .add("getStartCount()", getStartCount())
                .add("getEndCount()", getEndCount())
                .toString();
    }
}
