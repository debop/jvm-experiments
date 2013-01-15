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
  def getFirst: ITimePeriod =
    if (size > 0) get(0)
    else null

  /**
   * 마지막 항목
   */
  def getLast: ITimePeriod =
    if (size > 0) get(size - 1)
    else null
}
