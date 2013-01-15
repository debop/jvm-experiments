package kr.kth.timeperiod

import org.joda.time.DateTime

/**
 * kr.kth.timeperiod.ITimeLineMomentCollection
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 14
 */
trait ITimeLineMomentCollection extends java.util.Collection[ITimeLineMoment] with Serializable {

  def getMin: ITimeLineMoment

  def getMax: ITimeLineMoment

  def get(index: Int): ITimeLineMoment

  def add(period: ITimePeriod)

  def addAll(periods: Iterable[_ <: ITimePeriod])

  def remove(period: ITimePeriod)

  def find(moment: DateTime): ITimeLineMoment

  def contains(moment: DateTime): Boolean

}
