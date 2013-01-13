package kr.kth.timeperiod;

/**
 * 기간 종류 {@link java.util.concurrent.TimeUnit} 과 유사
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
public enum PeriodKind {
    /**
     * 알 수 없음
     */
    Unknown,
    /**
     * 년 (Year)
     */
    Year,
    /**
     * 반기 (Half Year)
     */
    Halfyear,
    /**
     * 분기 (Quarter)
     */
    Quarter,
    /**
     * 월 (Month)
     */
    Month,
    /**
     * 주 (Week)
     */
    Week,
    /**
     * 일 (Day)
     */
    Day,
    /**
     * 시간 (Hour)
     */
    Hour,
    /**
     * 분 (Minute)
     */
    Minute,
    /**
     * 초 (Second)
     */
    Second,
    /**
     * 밀리초 (Millisecond)
     */
    Millisecond,
    /**
     * 나노초 (Nanosecond)
     */
    Nanosecond,
}
