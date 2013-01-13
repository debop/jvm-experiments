package kr.kth.timeperiod

import org.joda.time.DateTime

import IntervalEdge._

/**
 * 시간 간격을 나타냅니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 13.
 */
trait ITimeInterval extends ITimePeriod {
  /**
   * 시작 시각이 개구간인지 여부
   */
  def isStartOpen: Boolean

  /**
   * 완료 시각이 개구간인지 여부
   */
  def isEndOpen: Boolean

  /**
   * 시작시각이 폐구간인지 여부
   *
   * @return 시작시각이 폐구간이면 true, 개구간이면 false를 반환
   */
  def isStartClosed: Boolean

  /**
   * 완료시각이 폐구간인지 여부
   *
   * @return 완료시각이 폐구간이면 true, 개구간이면 false를 반환
   */
  def isEndClosed: Boolean

  /**
   * 기간이 폐구간인지 여부
   */
  def isClosed: Boolean

  /**
   * 기간이 비었는가 여부 (시작시각과 완료시각이 같으면 기간은 비었다)
   */
  def isEmpty: Boolean

  /**
   * 기간(Interval)로 쓸 수 없는 경우 (isMoment() 가 true 면서, isClosed() 도 true인 경우)
   */
  def isDegenerate: Boolean

  /**
   * 사용가능한 Interval 인가?
   */
  def isIntervalEnabled: Boolean

  /**
   * 사용가능한 Interval 인지를 설정합니다.
   */
  def setIntervalEnabled(enabled: Boolean = true)

  /**
   * 시작 시각
   */
  def getStartInterval: DateTime

  /**
   * 시작 시각을 설정합니다.
   */
  def setStartInterval(newStart: DateTime)

  /**
   * 완료 시각
   */
  def getEndInterval: DateTime

  /**
   * 완료 시각을 설정합니다.
   */
  def setEndInterval(newEnd: DateTime)

  /**
   * 시작 시각의 개/폐구간 종류
   */
  def getStartEdge: IntervalEdge

  /**
   * 시작 시각의 걔/페구간 종류를 설정합니다.
   */
  def setStartEdge(edge: IntervalEdge)

  /**
   * 완료 시각의 개/폐구간 종류
   */
  def getEndEdge: IntervalEdge

  /**
   * 완료 시각의 개/폐구간 종류를 설정합니다.
   */
  def setEndEdge(edge: IntervalEdge)

  def expandStartTo(moment: DateTime)

  def expandEndTo(moment: DateTime)

  def expandTo(moment: DateTime)

  def expandStartTo(period: ITimePeriod)

  def shrinkStartTo(moment: DateTime)

  def shrinkEndTo(moment: DateTime)

  def shrinkTo(moment: DateTime)

  def shrinkStartTo(period: ITimePeriod)
}
