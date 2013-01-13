package kr.kth.timeperiod;

/**
 * {@link ITimePeriod}의 Linked List 스타일의 컨테이너입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
public interface ITimePeriodChain extends ITimePeriodContainer {

    /**
     * 첫번째 항목
     */
    ITimePeriod getFirst();

    /**
     * 마지막 항목
     */
    ITimePeriod getLast();
}
