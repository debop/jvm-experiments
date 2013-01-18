package kr.kth.timeperiod

import kr.kth.commons.slf4j.Logging
import org.joda.time.DateTime
import scala.reflect.{ClassTag, classTag}

/**
 * {@link ITimePeriod} 컬렉션을 연산할 수 있는 인터페이스입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 13.
 */
@SerialVersionUID(-2141722270204777840L)
class TimeLine[T <: ITimePeriod: ClassTag](periods: ITimePeriodContainer,
                                 limits: ITimePeriod,
                                 periodMapper: ITimePeriodMapper)
    extends ITimeLine with Logging {

    private val _periods = periods
    private val _limits = if (limits != null) TimeRange(limits) else TimeRange(periods)
    private val _periodMapper = periodMapper
    private val _periodClass = classTag[T].runtimeClass

    def this(periods: ITimePeriodContainer) {
        this(periods, null, null)
    }

    def this(periods: ITimePeriodContainer, periodMapper: ITimePeriodMapper) {
        this(periods, null, periodMapper)
    }

    def getPeriods: ITimePeriodContainer = _periods

    def getLimits: ITimePeriod = _limits

    def getPeriodMapper: ITimePeriodMapper = _periodMapper

    def combinePeriods: ITimePeriodCollection = {
        log.debug("ITimePeriod 컬렉션의 합집합을 계산합니다...")
        if (_periods.size == 0)
            return new TimePeriodCollection()

        val timeLineMoments = getTimeLineMoments
        if (timeLineMoments.size == 0)
            TimePeriodCollection(TimeRange(_periods))
        else
            TimeLines.combinePeriods[T](timeLineMoments)
    }

    def intersectPeriods: ITimePeriodCollection = {
        log.debug("ITimePeriod 컬렉션의 교집합을 계산합니다...")
        if (_periods.size == 0)
            return new TimePeriodCollection()

        val timeLineMoments = getTimeLineMoments
        if (timeLineMoments.size == 0)
            TimePeriodCollection()
        else
            TimeLines.combinePeriods[T](timeLineMoments)
    }

    def calculateGaps: ITimePeriodCollection = {
        log.debug("ITimePeriod 사이의 Gap 들을 계산하여, ITimePeriodCollection 으로 빌드합니다...")

        val gapPeriods = new TimePeriodCollection()

        // gapPeriods 에 gap 을 추가한다.
        _periods.filter(p => _limits.intersectsWith(p)).foreach(p => gapPeriods.add(TimeRange(p)))

        val timeLineLimits = getTimeLineMoments(gapPeriods)
        if (timeLineLimits.size == 0)
            return TimePeriodCollection(_limits)


        val range = _periodClass.newInstance().asInstanceOf[T]
        range.setup(mapPeriodStart(_limits.getStart), mapPeriodEnd(_limits.getEnd))

        TimeLines.calculateCaps(timeLineLimits, range)
    }

    private def getTimeLineMoments: ITimeLineMomentCollection = {
        getTimeLineMoments(_periods)
    }

    private def getTimeLineMoments(momentPeriods: Iterable[ITimePeriod]): ITimeLineMomentCollection = {
        log.debug("기간 컬렉션으로부터 ITimeLineMoment 컬렉션을 빌드합니다...")

        val timeLineMoments = new TimeLineMomentCollection()

        if (momentPeriods.size == 0)
            return timeLineMoments

        val intersections = new TimePeriodCollection()

        for (momentPeriod <- momentPeriods if !momentPeriod.isMoment) {
            val intersection = getLimits.getIntersection(momentPeriod)
            if (intersection != null && !intersection.isMoment) {
                if (_periodMapper != null) {
                    intersection.setup(mapPeriodStart(intersection.getStart),
                                       mapPeriodEnd(intersection.getEnd))
                }
                intersections.add(intersection)
            }
        }

        timeLineMoments
    }

    private def mapPeriodStart(start: DateTime): DateTime =
        if (_periodMapper != null) _periodMapper.unmapStart(start) else start

    private def mapPeriodEnd(end: DateTime): DateTime =
        if (_periodMapper != null) _periodMapper.unmapEnd(end) else end
}

object TimeLine {

    def apply[T <: ITimePeriod: ClassTag](periods: ITimePeriodContainer,
                                alimits: ITimePeriod = null,
                                periodMapper: ITimePeriodMapper = null): TimeLine[T] = {
        new TimeLine[T](periods, alimits, periodMapper)
    }

}

trait ITimeLine extends Serializable {

    def getPeriods: ITimePeriodContainer

    def getLimits: ITimePeriod

    def getPeriodMapper: ITimePeriodMapper

    def combinePeriods: ITimePeriodCollection

    def intersectPeriods: ITimePeriodCollection

    def calculateGaps: ITimePeriodCollection
}