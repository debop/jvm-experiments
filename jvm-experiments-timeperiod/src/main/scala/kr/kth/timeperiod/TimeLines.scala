package kr.kth.timeperiod

import kr.kth.commons.Guard
import kr.kth.commons.slf4j.Logging
import kr.kth.commons.tools.ScalaReflects
import kr.kth.commons.tools.StringTool._
import scala.reflect.{ClassTag, classTag}

/**
 * TimeLine 관련 Object
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 16
 */
object TimeLines extends Logging {
    /**
     * {@link ITimeLineMome ntCollection}의 모든 기간의 합집합을 구합니다.
     */
    def combinePeriods[T <: ITimePeriod : ClassTag](timeLineMoments: ITimeLineMomentCollection)
    : ITimePeriodCollection = {
        log.debug("ITimeLineMoment 컬렉션의 모든 기간의 합집합을 구합니다...")

        val periods: TimePeriodCollection = TimePeriodCollection()
        if (timeLineMoments.isEmpty)
            return periods

        var itemIndex: Int = 0
        while (itemIndex < timeLineMoments.size) {
            val periodStart = timeLineMoments.get(itemIndex)
            Guard.shouldNotBeNull(periodStart, "periodStart")
            Guard.shouldBe(periodStart.getStartCount != 0,
                           s"getStartCount() 값은 [0] 이 아니여야 합니다. periodStart.getStartCount()=[${periodStart.getStartCount}]")

            var balance: Int = periodStart.getStartCount
            var periodEnd: ITimeLineMoment = null

            while (itemIndex < timeLineMoments.size - 1 && balance > 0) {
                itemIndex += 1
                periodEnd = timeLineMoments.get(itemIndex)
                balance += periodEnd.getStartCount
                balance -= periodEnd.getEndCount
            }
            Guard.shouldNotBeNull(periodEnd, "periodEnd")
            if (periodEnd.getStartCount == 0) {
                if (itemIndex < timeLineMoments.size) {
                    val period: ITimePeriod = ScalaReflects.newInstance[T] //classTag[T].runtimeClass.newInstance.asInstanceOf[T]  // periodClass.newInstance()
                    period.setup(periodStart.getMoment, periodEnd.getMoment)
                    if (log.isDebugEnabled) log.debug("Combine period를 추가합니다. period=[{}]", period)
                    periods.add(period)
                }
            }
            itemIndex += 1
        }
        log.debug(s"기간들을 결합했습니다. periods=[${listToString(periods)}]")
        periods
    }

    /**
         * {@link ITimeLineMomentCollection} 으로부터 교집합에 해당하는 기간들을 구합니다.
         *
         * @param periodClass
         * @param timeLineMoments
         * @param <T>
         * @return
         */
    def intersectPeriods[T <: ITimePeriod : ClassTag](timeLineMoments: ITimeLineMomentCollection)
    : ITimePeriodCollection = {
        log.debug("ItimeLineMomentCollection으로부터 교집합에 해당하는 기간들을 구합니다.")

        val periods: TimePeriodCollection = new TimePeriodCollection
        if (timeLineMoments.isEmpty) return periods
        var intersectionStart: Int = -1
        var balance: Int = 0

        var i: Int = 0
        for (moment: ITimeLineMoment <- timeLineMoments) {
            balance += moment.getStartCount
            balance -= moment.getEndCount
            if (moment.getStartCount > 0 && balance > 1 && intersectionStart < 0) {
                intersectionStart = i
            } else if (moment.getEndCount > 0 && balance <= 1 && intersectionStart >= 0) {
                val period: ITimePeriod = ScalaReflects.newInstance[T] //classTag[T].runtimeClass.newInstance().asInstanceOf[T] //periodClass.newInstance() //newInstance[T](classOf[T])
                period.setup(timeLineMoments.get(intersectionStart).getMoment, moment.getMoment)

                log.debug("Intersection period를 추가합니다. period=[{}]", period)
                periods.add(period)
                intersectionStart = -1
            }
            i += 1
        }
        log.debug("ITimeLineMomentCollection으로부터 교집합에 해당하는 기간을 구했습니다. periods=[{}]", listToString(periods))
        periods
    }

    /**
         * {@link ITimeLineMomentCollection}의 모든 기간에 속하지 않는 Gap 들을 찾아냅니다.
         */
    def calculateCaps[T <: ITimePeriod : ClassTag](timeLineMoments: ITimeLineMomentCollection,
                                                   range: ITimePeriod)
    : ITimePeriodCollection = {
        log.debug("ITimeLineMomentCollection의 Gap을 계산합니다...")
        Guard.shouldNotBeNull(timeLineMoments, "timeLineMoments")

        val gaps: TimePeriodCollection = TimePeriodCollection()

        if (timeLineMoments.isEmpty)
            return gaps

        val periodStart: ITimeLineMoment = timeLineMoments.getMin
        if (periodStart != null && range.getStart.compareTo(periodStart.getMoment) < 0) {
            val startingGap = ScalaReflects.newInstance[T] //periodClass.newInstance()  // newInstance[T]()
            startingGap.setup(range.getStart, periodStart.getMoment)
            log.debug(s"Starting Gap을 추가합니다... startingGap=[$startingGap]")
            gaps.add(startingGap)
        }

        var itemIndex: Int = 0
        while (itemIndex < timeLineMoments.size) {
            val moment: ITimeLineMoment = timeLineMoments.get(itemIndex)
            Guard.shouldBe(moment.getStartCount != 0,
                           s"moment.getStartCount() 값은 [0] 이 아니어야 합니다. moment=[$moment]")

            var balance: Int = moment.getStartCount
            var gapStart: ITimeLineMoment = null
            while (itemIndex < timeLineMoments.size - 1 && balance > 0) {
                itemIndex += 1
                gapStart = timeLineMoments.get(itemIndex)
                balance += gapStart.getStartCount
                balance -= gapStart.getEndCount
            }

            Guard.shouldNotBeNull(gapStart, "gapStart")
            if (gapStart != null && gapStart.getStartCount == 0) {
                if (itemIndex < timeLineMoments.size - 1) {
                    val gap: ITimePeriod = ScalaReflects.newInstance[T] // periodClass.newInstance() //newInstance[T](classOf[T])
                    gap.setup(gapStart.getMoment, timeLineMoments.get(itemIndex + 1).getMoment)

                    log.debug(s"intermediated gap 을 추가합니다. gap=[$gap]")
                    gaps.add(gap)
                }
            }
            itemIndex += 1
        }

        val periodEnd: ITimeLineMoment = timeLineMoments.getMax
        if (periodEnd != null && range.getEnd.compareTo(periodEnd.getMoment) > 0) {
            val endingGap: ITimePeriod = ScalaReflects.newInstance[T] //periodClass.newInstance// newInstance[T](classOf[T])
            endingGap.setup(periodEnd.getMoment, range.getEnd)

            log.debug("Ending Gap을 추가합니다. endingGap=[{}]", endingGap)
            gaps.add(endingGap)
        }
        log.debug(s"기간들의 gap에 해당하는 부분을 계산했습니다!!! gaps=[${listToString(gaps)}]")
        gaps
    }

    //    @inline
    //    def newInstance[T](periodClass: Class[T]): T = periodClass.newInstance()
}
