package kr.kth.timeperiod

import org.joda.time.DateTime
import java.util.concurrent.atomic.AtomicReference

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

abstract class ClockBase extends Clock with Serializable {

  var datetime: DateTime = _

  def this(dateTime: DateTime) {
    this()
    this.datetime = dateTime
  }

  override def now: DateTime = {
    if (datetime == null)
      datetime = DateTime.now()
    datetime
  }

  override def today: DateTime = now.withTimeAtStartOfDay

  override def timeOfDay: Long = now.getMillisOfDay

}

@SerialVersionUID(-2846191313649276854L)
class SystemClock extends ClockBase {
  override def now: DateTime = new DateTime()
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