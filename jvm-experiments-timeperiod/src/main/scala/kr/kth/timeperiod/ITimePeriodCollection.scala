package kr.kth.timeperiod

import PeriodRelation._
import org.joda.time.DateTime
import scala.annotation.varargs

/**
 * kr.kth.timeperiod.ITimePeriodCollection
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 13.
 */
trait ITimePeriodCollection extends ITimePeriodContainer {

  def hasInsidePeriods(moment: DateTime): Boolean =
    getPeriods.exists(p => Times.hasInside(p, moment))

  def hasInsidePeriods(target: ITimePeriod): Boolean =
    getPeriods.exists(p => Times.hasInside(p, target))

  def hasOverlapPeriods(target: ITimePeriod): Boolean =
    getPeriods.exists(p => Times.overlapsWith(p, target))

  def hasIntersectionPeriods(moment: DateTime): Boolean =
    hasIntersectionPeriods(TimeRange(start = moment, end = moment))

  def hasIntersectionPeriods(target: ITimePeriod): Boolean =
    getPeriods.exists(p => Times.intersectsWith(p, target))

  def insidePeriods(target: ITimePeriod): Iterable[ITimePeriod] =
    getPeriods.filter(p => Times.hasInside(p, target))

  def overlapPeriods(target: ITimePeriod): Iterable[ITimePeriod] =
    getPeriods.filter(p => Times.overlapsWith(p, target))

  def intersectionPeriods(moment: DateTime): Iterable[ITimePeriod] =
    intersectionPeriods(TimeRange(start = moment, end = moment))

  def intersectionPeriods(target: ITimePeriod): Iterable[ITimePeriod] =
    getPeriods.filter(p => Times.intersectsWith(p, target))

  def relationPeriods(target: ITimePeriod, relation: PeriodRelation): Iterable[ITimePeriod] =
    getPeriods.filter(p => Times.getRelation(p, target) == relation)

  @varargs
  def relationPeriods(target: ITimePeriod, relations: PeriodRelation*): Iterable[ITimePeriod] = {
    getPeriods.filter(p => relations.contains(Times.getRelation(p, target)))
  }
}
