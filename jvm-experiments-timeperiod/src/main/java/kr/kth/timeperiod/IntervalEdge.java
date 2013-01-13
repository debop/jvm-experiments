package kr.kth.timeperiod;

/**
 * {@link ITimeInterval} 경계의 상태 (개구간, 폐구간)
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18.
 */
public enum IntervalEdge {
    /**
     * 폐구간
     */
    Closed,

    /**
     * 개구간
     */
    Open
}
