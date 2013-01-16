package kr.kth.timeperiod

import org.joda.time.DateTime

/**
 * kr.kth.timeperiod.ITimeLineMoment
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 14
 */
class TimeLineMoment(val moment: DateTime) extends ITimeLineMoment {

  private var _periods: ITimePeriodCollection = new TimePeriodCollection()

  /**
   * 기준 시각
   */
  def getMoment: DateTime = moment

  /**
     * 기간의 컬렉션을 반환합니다.
     */
  def getPeriods: ITimePeriodCollection = _periods

  protected def setPeriods(periods: ITimePeriodCollection) {
    _periods = periods
  }

  /**
     * 시작 시각 갯수
     */
  def getStartCount: Int = _periods.count(p => p.getStart == moment)

  /**
     * 종료 시각 갯수
     */
  def getEndCount: Int = _periods.count(p => p.getEnd == moment)
}


trait ITimeLineMoment extends Serializable {

  /**
   * 기준 시각
   */
  def getMoment: DateTime

  /**
   * 기간의 컬렉션을 반환합니다.
   */
  def getPeriods: ITimePeriodCollection

  /**
   * 시작 시각 갯수
   */
  def getStartCount: Int

  /**
   * 종료 시각 갯수
   */
  def getEndCount: Int

}
