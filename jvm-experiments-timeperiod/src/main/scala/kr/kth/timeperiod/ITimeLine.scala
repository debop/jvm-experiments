package kr.kth.timeperiod

import kr.kth.commons.slf4j.Logging
import kr.kth.timeperiod.timeline.{TimeLineTool, TimeLineMomentCollection}
import org.joda.time.DateTime


/**
 * {@link ITimePeriod} 컬렉션을 연산할 수 있는 인터페이스입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 13.
 */
@SerialVersionUID(-2141722270204777840L)
class TimeLine[T <: ITimePeriod](val periodClass: Class[T],
                                 val periods: ITimePeriodContainer,
                                 alimits: ITimePeriod,
                                 val periodMapper: ITimePeriodMapper)
  extends ITimeLine with Logging {

  private val limits = if (alimits != null) TimeRange(limits) else TimeRange(periods)

  def this(periodClass: Class[T], periods: ITimePeriodContainer) {
    this(periodClass, periods, null, null)
  }

  def this(periodClass: Class[T], periods: ITimePeriodContainer, periodMapper: ITimePeriodMapper) {
    this(periodClass, periods, null, periodMapper)
  }

  def getPeriods: ITimePeriodContainer = periods

  def getLimits: ITimePeriod = limits

  def getPeriodMapper: ITimePeriodMapper = periodMapper

  def combinePeriods: ITimePeriodCollection = {
    log.debug("ITimePeriod 컬렉션의 합집합을 계산합니다...")
    if (periods.size == 0)
      return new TimePeriodCollection()

    val timeLineMoments = getTimeLineMoments
    if (timeLineMoments.size == 0)
      TimePeriodCollection(TimeRange(getPeriods))
    else
      TimeLineTool.combinePeriods(periodClass, timeLineMoments)
  }

  def intersectPeriods: ITimePeriodCollection = {
    log.debug("ITimePeriod 컬렉션의 교집합을 계산합니다...")
    if (periods.size == 0)
      return new TimePeriodCollection()

    val timeLineMoments = getTimeLineMoments
    if (timeLineMoments.size == 0)
      TimePeriodCollection()
    else
      TimeLineTool.combinePeriods(periodClass, timeLineMoments)
  }

  def calculateGaps: ITimePeriodCollection = {
    log.debug("ITimePeriod 사이의 Gap 들을 계산하여, ITimePeriodCollection 으로 빌드합니다...")

    val gapPeriods = new TimePeriodCollection()

    getPeriods
    .filter(p => limits.intersectsWith(p))
    .foreach(p => gapPeriods.add(TimeRange(p)))

    val timeLineLimits = getTimeLineMoments(gapPeriods)
    if (timeLineLimits.size() == 0)
      return TimePeriodCollection(limits)

    val range = periodClass.newInstance()
    range.setup(mapPeriodStart(limits.getStart), mapPeriodEnd(limits.getEnd))

    TimeLineTool.calculateCaps(periodClass, timeLineLimits, range)
  }

  private def getTimeLineMoments: ITimeLineMomentCollection = {
    getTimeLineMoments(getPeriods)
  }

  private def getTimeLineMoments(momentPeriods: Iterable[_ <: ITimePeriod]): ITimeLineMomentCollection = {
    log.debug("기간 컬렉션으로부터 ITimeLineMoment 컬렉션을 빌드합니다...")

    val timeLineMoments = new TimeLineMomentCollection()

    if (momentPeriods.size == 0)
      return timeLineMoments

    val intersections = new TimePeriodCollection()

    for (momentPeriod <- momentPeriods if !momentPeriod.isMoment) {
      val intersection = getLimits.getIntersection(momentPeriod)
      if (intersection != null && !intersection.isMoment) {
        if (periodMapper != null) {
          intersection.setup(mapPeriodStart(intersection.getStart),
                             mapPeriodEnd(intersection.getEnd))
        }
        intersections.add(intersection)
      }
    }

    timeLineMoments
  }

  private def mapPeriodStart(start: DateTime): DateTime =
    if (periodMapper != null) periodMapper.unmapStart(start) else start

  private def mapPeriodEnd(end: DateTime): DateTime =
    if (periodMapper != null) periodMapper.unmapEnd(end) else end
}

object TimeLine {

}

trait ITimeLine extends Serializable {

  def getPeriods: ITimePeriodContainer

  def getLimits: ITimePeriod

  def getPeriodMapper: ITimePeriodMapper

  def combinePeriods: ITimePeriodCollection

  def intersectPeriods: ITimePeriodCollection

  def calculateGaps: ITimePeriodCollection
}