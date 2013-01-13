package kr.kth.timeperiod;

/**
 * 두 개의 {@link ITimePeriod} 의 관계를 표현합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
public enum PeriodRelation {
    /**
     * 관계 없음 (두 개의 기간이 모두 AnyTime 일 경우)
     */
    NoRelation,
    /**
     * 현 {@link ITimePeriod} 이후에 대상 {@link ITimePeriod} 가 있을 때
     */
    After,
    /**
     * 현 ITimePeriod의 완료시각이 대상 ITimePeriod의 시작시각과 같습니다.
     */
    StartTouching,
    /**
     * 현 ITimePeriod 기간 안에 대상 ITimePeriod의 시작 시각만 포함될 때
     */
    StartInside,
    /**
     * 현 ITimePeriod의 시작시각과 대상 ITimePeriod의 시작시각이 일치하고, 대상 ITimePeriod가 현 ITimePeriod에 포함될 때
     */
    InsideStartTouching,
    /**
     * 현 ITimePeriod의 시작시각과 대상 ITimePeriod의 시작시각이 일치하고, 현 ITimePeriod가 대상 ITimePeriod에 포함될 때
     */
    EnclosingStartTouching,
    /**
     * 현 ITimePeriod 가 대상 ITimePeriod 기간에 포함될 때
     */
    Enclosing,
    /**
     * 현 ITimePeriod의 시작시각과 대상 ITimePeriod의 시작시각이 일치하고, 현 ITimePeriod가 대상 ITimePeriod에 포함될 때
     */
    EnclosingEndTouching,
    /**
     * 현 ITimePeriod 와 대상 ITimePeriod 가 완전히 일치할 때, 둘 다 AnyTime이라면 Exact Match는 아니다.
     */
    ExactMatch,
    /**
     * 현 ITimePeriod 안에 대상 ITimePeriod가 완전히 포함될 때
     */
    Inside,
    /**
     * 현 ITimePeriod 안에 대상 ITimePeriod이 포함되는데, 완료시각만 같을 때
     */
    InsideEndTouching,
    /**
     * 현 ITimePeriod 안에 대상 ITimePeriod의 완료시각만 포함될 때
     */
    EndInside,
    /**
     * 현 ITimePeriod의 시작시각이 대상 ITimePeriod의 완료시각과 같을 때
     */
    EndTouching,
    /**
     * 현 ITimePeriod의 시작시각 전에 대상 ITimePeriod의 완료시각이 있을 때
     */
    Before
}
