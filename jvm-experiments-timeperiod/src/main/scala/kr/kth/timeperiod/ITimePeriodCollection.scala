package kr.kth.timeperiod

import org.joda.time.DateTime
import PeriodRelation._
import scala.annotation.varargs

/**
 * kr.kth.timeperiod.ITimePeriodCollection
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 13.
 */
trait ITimePeriodCollection extends ITimePeriodContainer {

  def hasInsidePeriods(target: ITimePeriod): Boolean

  def hasOverlapPeriods(target: ITimePeriod): Boolean

  def hasIntersectionPeriods(moment: DateTime): Boolean

  def hasIntersectionPeriods(target: ITimePeriod): Boolean

  def insidePeriods(target: ITimePeriod): Iterable[ITimePeriod]

  def overlapPeriods(target: ITimePeriod): Iterable[ITimePeriod]

  def intersectionPeriods(moment: DateTime): Iterable[ITimePeriod]

  def intersectionPeriods(target: ITimePeriod): Iterable[ITimePeriod]

  def relationPeriods(target: ITimePeriod, relation: PeriodRelation): Iterable[ITimePeriod]

  @varargs
  def relationPeriods(target: ITimePeriod, relations: PeriodRelation*): Iterable[ITimePeriod]
}
