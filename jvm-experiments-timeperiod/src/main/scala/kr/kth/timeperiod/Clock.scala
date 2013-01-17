package kr.kth.timeperiod

import java.util.concurrent.atomic.AtomicReference
import org.joda.time.DateTime

/**
 * 시각을 나타내는 Trait
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 13.
 */
trait Clock {

    /**
     * 현재 시각
     */
    def now: DateTime

    /**
     * 오늘 (현재시각의 날짜 파트만)
     */
    def today: DateTime

    /**
     * 현재 시각의 시간 파트만
     */
    def timeOfDay: Long
}

abstract class ClockBase(val datetime:DateTime) extends Clock with Serializable {

    override def now: DateTime = {
        datetime
    }

    override def today: DateTime = now.withTimeAtStartOfDay

    override def timeOfDay: Long = now.getMillisOfDay

}

@SerialVersionUID(-2846191313649276854L)
class SystemClock extends ClockBase(new DateTime()) {
    override def now: DateTime = datetime
}


@SerialVersionUID(3634515685313254258L)
class StaticClock(datetime: DateTime) extends ClockBase(datetime) {}

/**
 * Clock 을 제공합니다.
 */
object Clock {
    val clockReferece = new AtomicReference[Clock]()

    def getClock: Clock = {
        clockReferece.compareAndSet(null, new SystemClock())
        clockReferece.get()
    }

    def setClock(clock: Clock) {
        clockReferece.set(clock)
    }
}