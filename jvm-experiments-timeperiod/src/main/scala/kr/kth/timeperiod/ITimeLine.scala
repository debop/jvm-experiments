package kr.kth.timeperiod

/**
 * {@link ITimePeriod} 컬렉션을 연산할 수 있는 인터페이스입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 13.
 */
trait ITimeLine {

  def getPeriod: ITimePeriodContainer

  def getLimits: ITimePeriod

  def getPeriodMapper: ITimePeriodMapper

  def combinePeriods: ITimePeriodCollection

  def intersectPeriods: ITimePeriodCollection

  def calculateGaps: ITimePeriodCollection
}