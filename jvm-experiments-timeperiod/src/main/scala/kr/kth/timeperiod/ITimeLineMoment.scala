package kr.kth.timeperiod

import com.google.common.base.Objects
import java.util.concurrent.atomic.AtomicReference
import kr.kth.commons.tools.{StringTool, ScalaHash}
import org.joda.time.DateTime

/**
 * 특정 기준 시각에 대한 필터링을 수행합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 14
 */
class TimeLineMoment(moment: DateTime) extends ITimeLineMoment {

    private val _moment: DateTime = moment
    private val _periods: AtomicReference[ITimePeriodCollection] = new AtomicReference[ITimePeriodCollection]()

    /**
     * 기준 시각
     */
    def getMoment: DateTime = _moment

    /**
     * 기간의 컬렉션을 반환합니다.
     */
    def getPeriods: ITimePeriodCollection = {
        _periods.compareAndSet(null, TimePeriodCollection())
        _periods.get()
    }

    /**
     * 기간의 컬렉션을 반환합니다.
     */
    protected def setPeriods(periods: ITimePeriodCollection) {
        _periods.set(periods)
    }

    /**
     * 시작 시각 갯수
     */
    def getStartCount: Int = getPeriods.count(p => p.getStart == _moment)

    /**
     * 종료 시각 갯수
     */
    def getEndCount: Int = getPeriods.count(p => p.getEnd == _moment)

    override def hashCode: Int = ScalaHash.compute(_moment, getPeriods)

    override def toString: String = {
        Objects.toStringHelper(this)
        .add("_moment", moment)
        .add("_periods", StringTool.listToString(getPeriods))
        .toString
    }
}


trait ITimeLineMoment extends Serializable {

    /**
     * 기준 시각
     */
    def getMoment: DateTime

    /**
     * 기간의 컬렉션을 반환합니다.
     */
    def getPeriods: ITimePeriodCollection

    /**
     * 시작 시각 갯수
     */
    def getStartCount: Int

    /**
     * 종료 시각 갯수
     */
    def getEndCount: Int

}
