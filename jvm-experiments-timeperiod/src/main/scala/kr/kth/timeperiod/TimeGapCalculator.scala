package kr.kth.timeperiod

import kr.kth.commons._
import kr.kth.commons.slf4j.Logging
import scala.reflect.ClassTag

/**
 * 기간 컬렉션에서 빈 부분 (Gap) 을 계산합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 18
 */
class TimeGapCalculator[T <: ITimePeriod : ClassTag](mapper: ITimePeriodMapper)
    extends Logging {

    private val _mapper: ITimePeriodMapper = mapper

    def this() { this(null) }

    def getPeriodMapper: ITimePeriodMapper = _mapper

    def getGaps(excludedPeriods: ITimePeriodContainer, limits: ITimePeriod = null): ITimePeriodCollection = {
        Guard.shouldNotBeNull(excludedPeriods, "excludedPeriods")
        log.debug(s"Period들의 Gap들을 계산합니다... excludedPeriods=[$excludedPeriods], limits=[$limits]")

        val timeLine = TimeLine[T](excludedPeriods, limits, _mapper)
        timeLine.calculateGaps
    }
}
