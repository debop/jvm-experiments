package kr.kth.commons.timeperiod;

import kr.kth.commons.base.SortDirection;
import org.joda.time.DateTime;

import java.util.List;

/**
 * {@link ITimePeriod} 를 항목으로 가지는 컨테이너를 나타냅니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
public interface ITimePeriodContainer extends List<ITimePeriod>, ITimePeriod {

    /**
     * 컨테이너의 시작시각을 설정합니다.
     */
    void setStart(DateTime start);

    /**
     * 컨테이너의 완료시각을 설정합니다.
     */
    void setEnd(DateTime end);

    /**
     * 지정한 기간을 포함하고 있는지 검사한다.
     */
    boolean containsPeriod(ITimePeriod target);

    /**
     * 지정한 {@link ITimePeriod} 들을 컨테이너에 모두 추가한다.
     */
    void addAll(Iterable<? extends ITimePeriod> periods);

    /**
     * 모든 항목을 {@link ITimePeriod#getStart()} 를 기준으로 정렬합니다.
     */
    void sortByStart(SortDirection direction);

    /**
     * 모든 항목을 {@link ITimePeriod#getEnd()} 를 기준으로 정렬합니다.
     */
    void sortByEnd(SortDirection direction);

    /**
     * 모든 항목을 {@link ITimePeriod#getDuration()} 를 기준으로 정렬합니다.
     */
    void sortByDuration(SortDirection direction);
}
