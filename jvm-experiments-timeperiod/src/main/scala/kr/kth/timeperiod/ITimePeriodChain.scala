package kr.kth.timeperiod

/**
 * kr.kth.timeperiod.ITimePeriodChain
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 14
 */
trait ITimePeriodChain extends ITimePeriodContainer {
  /**
   * 첫번째 항목
   */
  def getFirst: ITimePeriod

  /**
   * 마지막 항목
   */
  def getLast: ITimePeriod
}
