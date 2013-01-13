package kr.kth.timeperiod;

import org.joda.time.DateTime;

/**
 * Time Block 을 표현하는 인터페이스입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
public interface ITimeBlock extends ITimePeriod {

    /**
     * 시작 시각을 설정합니다.
     */
    void setStart(DateTime start);

    /**
     * 완료 시각을 설정합니다.
     */
    void setEnd(DateTime end);

    /**
     * 기간을 설정합니다.
     */
    void setDuration(long duration);

    /**
     * 기간을 설정합니다.
     *
     * @param start    시작 시각
     * @param duration 기간
     */
    void setup(DateTime start, long duration);

    /**
     * 시작 시각으로부터 지정된 duration 만큼을 기간으로 설정합니다.
     *
     * @param duration 간격
     */
    void durationFromStart(long duration);

    /**
     * 완료 시각 기준으로 duration 만큼을 기간으로 설정합니다.
     *
     * @param duration 간격
     */
    void durationFromEnd(long duration);

    /**
     * 지정된 offset 이전의 {@link ITimeBlock} 을 찾습니다.
     */
    ITimeBlock getPreviousBlock(long offset);

    /**
     * 지정된 offset 이후의 {@link ITimeBlock} 을 찾습니다.
     */
    ITimeBlock getNextBlock(long offset);
}
