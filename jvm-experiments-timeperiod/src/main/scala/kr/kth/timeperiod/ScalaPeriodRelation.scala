package kr.kth.timeperiod

/**
 * kr.kth.timeperiod.ScalaPeriodRelation
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 13.
 */
object ScalaPeriodRelation extends Enumeration {

  type ScalaPeriodRelation = Value

  /**
   * 관계 없음 (두 개의 기간이 모두 AnyTime 일 경우)
   */
  val NoRelation = Value("NoRelation")
  /**
   * 현 {@link ITimePeriod} 이후에 대상 {@link ITimePeriod} 가 있을 때
   */
  val After = Value("After")
  /**
   * 현 ITimePeriod의 완료시각이 대상 ITimePeriod의 시작시각과 같습니다.
   */
  val StartTouching = Value("StartTouching")
  /**
   * 현 ITimePeriod 기간 안에 대상 ITimePeriod의 시작 시각만 포함될 때
   */
  val StartInside = Value("StartInside")
  /**
   * 현 ITimePeriod의 시작시각과 대상 ITimePeriod의 시작시각이 일치하고, 대상 ITimePeriod가 현 ITimePeriod에 포함될 때
   */
  val InsideStartTouching = Value("InsideStartTouching")
  /**
   * 현 ITimePeriod의 시작시각과 대상 ITimePeriod의 시작시각이 일치하고, 현 ITimePeriod가 대상 ITimePeriod에 포함될 때
   */
  val EnclosingStartTouching = Value("EnclosingStartTouching")
  /**
   * 현 ITimePeriod 가 대상 ITimePeriod 기간에 포함될 때
   */
  val Enclosing = Value("Enclosing")
  /**
   * 현 ITimePeriod의 시작시각과 대상 ITimePeriod의 시작시각이 일치하고, 현 ITimePeriod가 대상 ITimePeriod에 포함될 때
   */
  val EnclosingEndTouching = Value("EnclosingEndTouching")
  /**
   * 현 ITimePeriod 와 대상 ITimePeriod 가 완전히 일치할 때, 둘 다 AnyTime이라면 Exact Match는 아니다.
   */
  val ExactMatch = Value("ExactMatch")
  /**
   * 현 ITimePeriod 안에 대상 ITimePeriod가 완전히 포함될 때
   */
  val Inside = Value("Inside")
  /**
   * 현 ITimePeriod 안에 대상 ITimePeriod이 포함되는데, 완료시각만 같을 때
   */
  val InsideEndTouching = Value("InsideEndTouching")
  /**
   * 현 ITimePeriod 안에 대상 ITimePeriod의 완료시각만 포함될 때
   */
  val EndInside = Value("EndInside")
  /**
   * 현 ITimePeriod의 시작시각이 대상 ITimePeriod의 완료시각과 같을 때
   */
  val EndTouching = Value("EndTouching")
  /**
   * 현 ITimePeriod의 시작시각 전에 대상 ITimePeriod의 완료시각이 있을 때
   */
  val Before = Value("Before")

}
