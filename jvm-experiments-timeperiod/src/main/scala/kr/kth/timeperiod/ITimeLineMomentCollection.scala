package kr.kth.timeperiod

import com.google.common.collect.{Lists, TreeMultiset}
import kr.kth.commons.slf4j.Logging
import org.joda.time.DateTime
import scala.collection.JavaConversions._
import java.util
import scala.collection.JavaConversions

/**
 * @{link TimeLineMoment} 의 컬렉션을 나타냅니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 14
 */
class TimeLineMomentCollection extends ITimeLineMomentCollection with Logging {

}

trait ITimeLineMomentCollection extends collection.Iterable[ITimeLineMoment] with Serializable {

    protected val _timeLineMoments =
        TreeMultiset.create[ITimeLineMoment](new java.util.Comparator[ITimeLineMoment] {
            def compare(o1: ITimeLineMoment, o2: ITimeLineMoment): Int =
                o1.getMoment.compareTo(o2.getMoment)
        })

    override def size: Int = _timeLineMoments.size()

    override def isEmpty: Boolean = _timeLineMoments.isEmpty

    def getMin: ITimeLineMoment = if (isEmpty) null else _timeLineMoments.elementSet().first

    def getMax: ITimeLineMoment = if (isEmpty) null else _timeLineMoments.elementSet().last

    def get(index: Int): ITimeLineMoment = {
        var i = 0
        for (element:ITimeLineMoment <- _timeLineMoments.elementSet()) {
            if (i == index)
                return element
            i += 1
        }
        null
    }

    def addTimePeriod(period: ITimePeriod) {
        if (period != null) {
            addPeriod(period.getStart, period)
            addPeriod(period.getEnd, period)
        }
    }

    def addTimePeriods(periods: ITimePeriod*) {
        periods.foreach(p => addTimePeriod(p))
    }

    def removeTimePeriod(period: ITimePeriod) {
        if (period != null) {
            removePeriod(period.getStart, period)
            removePeriod(period.getEnd, period)
        }
    }

    def find(moment: DateTime): ITimeLineMoment = {
        if (moment == null)
            null

        for (element: ITimeLineMoment <- _timeLineMoments.elementSet()) {
            if (moment.equals(element.getMoment))
                return element
        }
        null
    }

    def contains(moment: DateTime): Boolean = {
        moment != null && find(moment) != null
    }

    override def iterator: collection.Iterator[ITimeLineMoment] = {
        _timeLineMoments.iterator()
    }

    protected def addPeriod(moment: DateTime, period: ITimePeriod) {
        log.debug(s"ITimeLineMoment를 추가합니다... moment=$moment, period=$period")

        _timeLineMoments.synchronized {
            _timeLineMoments.wait()
            var timeLineMoment = find(moment)
            if (timeLineMoment == null) {
                timeLineMoment = new TimeLineMoment(moment)
                _timeLineMoments.add(timeLineMoment)
            }
            timeLineMoment.getPeriods.add(period)
            _timeLineMoments.notifyAll()
        }

        log.debug("ITimeLineMoment를 추가했습니다.")
    }

    protected def removePeriod(moment: DateTime, period: ITimePeriod) {

        log.debug(s"ITimeLineMoment를 제거합니다... moment=[$moment], period=[$period]")

        _timeLineMoments.synchronized {
            _timeLineMoments.wait()
            val timeLineMoment = find(moment)

            if(timeLineMoment!=null && timeLineMoment.getPeriods.contains(period)) {
                timeLineMoment.getPeriods.remove(period)

                log.debug(s"ITimeLineMoment를 제거했습니다. timeLineMoment=$timeLineMoment")

                if(timeLineMoment.getPeriods.size == 0) {
                    _timeLineMoments.remove(timeLineMoment)

                    log.debug(s"ITimeLineMoment 를 제거했습니다. timeLineMoment=[$timeLineMoment]")
                }
            }
            _timeLineMoments.notifyAll()
        }
    }
}
