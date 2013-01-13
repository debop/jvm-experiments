package kr.kth.timeperiod

import org.joda.time.DateTime

/**
 * kr.kth.timeperiod.ITimeRange
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 13.
 */
trait ITimeRange extends ITimePeriod {

  /**
   * 시작시각을 설정합니다.
   */
  def setStart(newStart: DateTime)

  /**
   * 완료시각을 설정합니다.
   */
  def setEnd(newEnd: DateTime)

  /**
   * 기간을 설정합니다.
   */
  def setDuration(newDuration: Long)

  /**
   * 시작시각을 지정된 시각으로 변경합니다. 기존 시작시각보다 작은 값 (과거) 이여야 합니다.
   */
  def expandStartTo(moment: DateTime)

  /**
   * 완료시각을 지정된 시각으로 변경합니다. 기존 완료시각보다 큰 값 (미래) 이어야 합니다.
   */
  def expandEndTo(moment: DateTime)

  /**
   * 시작시각과 완료시각을 지정된 시각으로 설정합니다.
   */
  def expandTo(moment: DateTime)

  /**
   * 시작시각과 완료시각을 지정된 기간 정보를 기준으로 변경합니다.
   */
  def expandTo(period: ITimePeriod)

  /**
   * 시작시각을 지정된 시각으로 변경합합니다. 기존 시작시각보다 큰 값(미래) 이어야 합니다.
   */
  def shrinkStartTo(moment: DateTime)

  /**
   * 완료시각을 지정된 시각으로 변경합니다. 기존 완료시각보다 작은 값(과거) 이어야 하고, 시작시각보다는 같거나 커야 합니다.
   */
  def shrinkEndTo(moment: DateTime)

  /**
   * 기간을 지정된 기간으로 축소시킵니다.
   */
  def shrinkTo(period: ITimePeriod)
}
