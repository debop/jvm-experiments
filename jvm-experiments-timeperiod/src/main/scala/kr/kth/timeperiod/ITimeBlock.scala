package kr.kth.timeperiod

import org.joda.time.DateTime

/**
 * Time Block 을 표현하는 인터페이스입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 13.
 */
trait ITimeBlock extends ITimePeriod {
  /**
   * 시작 시각을 설정합니다.
   */
  def setStart(nv: DateTime)

  /**
   * 완료 시각을 설정합니다.
   */
  def setEnd(nv: DateTime)

  /**
   * 기간을 설정합니다.
   */
  def setDuration(nv: Long)

  /**
   * 기간을 설정합니다.
   *
   * @param newStart    시작 시각
   * @param newDuration 기간
   */
  def setup(newStart: DateTime, newDuration: Long)

  /**
   * 시작 시각으로부터 지정된 getDuration 만큼을 기간으로 설정합니다.
   *
   * @param duration 간격
   */
  def durationFromStart(duration: Long)

  /**
   * 완료 시각 기준으로 getDuration 만큼을 기간으로 설정합니다.
   *
   * @param duration 간격
   */
  def durationFromEnd(duration: Long)

  /**
   * 지정된 offset 이전의 {@link ITimeBlock} 을 찾습니다.
   */
  def getPreviousBlock(offset: Long): ITimeBlock

  /**
   * 지정된 offset 이후의 {@link ITimeBlock} 을 찾습니다.
   */
  def getNextBlock(offset: Long): ITimeBlock
}
