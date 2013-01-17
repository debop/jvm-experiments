package kr.kth.timeperiod

import java.util.concurrent.locks.ReentrantLock
import kr.kth.commons.slf4j.Logging
import org.joda.time.DateTime
import scala.collection.mutable
import scala.util.Try

/**
 * @{link TimeLineMoment} 의 컬렉션을 나타냅니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 14
 */
class TimeLineMomentCollection extends ITimeLineMomentCollection {

    // 생성자를 제작해야 합니다.
}

trait ITimeLineMomentCollection extends collection.Iterable[ITimeLineMoment] with Logging with Serializable {

    private val _lock = new ReentrantLock()

    private var _timeLineMoments = mutable.ArrayBuffer[ITimeLineMoment]()

    def getTimeLineMoments: mutable.ArrayBuffer[ITimeLineMoment] = _timeLineMoments

    override def size: Int = _timeLineMoments.size

    override def isEmpty: Boolean = _timeLineMoments.isEmpty

    def getMin: ITimeLineMoment = if (isEmpty) null else _timeLineMoments(0)

    def getMax: ITimeLineMoment = if (isEmpty) null else _timeLineMoments(size - 1)

    def get(index: Int): ITimeLineMoment = {
        _timeLineMoments(index)
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

    def find(moment: DateTime): Option[ITimeLineMoment] = {
        if (moment == null) None
        else _timeLineMoments.find(m => moment.equals(m.getMoment))
    }

    def contains(moment: DateTime): Boolean = {
        find(moment) != None
    }

    override def iterator: collection.Iterator[ITimeLineMoment] = {
        _timeLineMoments.iterator
    }

    protected def addPeriod(moment: DateTime, period: ITimePeriod) {
        log.debug(s"ITimeLineMoment를 추가합니다... moment=$moment, period=$period")

        _lock.lock()
        Try {
            var timeLineMoment = find(moment).orNull
            if (timeLineMoment != null) {
                timeLineMoment = new TimeLineMoment(moment)
                _timeLineMoments += timeLineMoment
                _timeLineMoments = _timeLineMoments.sorted(kr.kth.timeperiod.TimeLimeMomentOrdering)
                //.sortWith(_.getMoment.getMillis < _.getMoment.getMillis)
            }
            timeLineMoment.getPeriods.add(period)
            log.debug("ITimeLineMoment를 추가했습니다.")
        }
        _lock.unlock()
    }

    protected def removePeriod(moment: DateTime, period: ITimePeriod) {

        log.debug(s"ITimeLineMoment를 제거합니다... moment=[$moment], period=[$period]")

        _lock.lock()
        Try {
            val timeLineMoment: ITimeLineMoment = find(moment).orNull

            if (timeLineMoment != null && timeLineMoment.getPeriods.contains(period)) {
                timeLineMoment.getPeriods.remove(period)

                log.debug(s"ITimeLineMoment를 제거했습니다. timeLineMoment=$timeLineMoment")

                if (timeLineMoment.getPeriods.size == 0) {
                    _timeLineMoments -= timeLineMoment
                    log.debug(s"ITimeLineMoment 를 제거했습니다. timeLineMoment=[$timeLineMoment]")
                }
            }
        }
        _lock.unlock()
    }
}
