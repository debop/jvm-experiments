package kr.kth.timeperiod

import kr.kth.commons.Guard
import kr.kth.commons.slf4j.Logging
import org.joda.time.DateTime
import tools.TimeTool

/**
 * 기준 일자의 시간 간격을 이용하여 기간을 표현합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 14
 */
class TimeBlock(start: DateTime, end: DateTime, readonly: Boolean = true)
  extends TimePeriod(start, end, readonly) with ITimeBlock {

  _duration = super.getDuration

}

object TimeBlock extends Logging {

  lazy val Anytime = apply(readonly = true)

  def apply(readonly: Boolean) = new TimeBlock(TimeSpec.MinPeriodTime, TimeSpec.MaxPeriodTime, readonly)

  def apply(moment: DateTime, readonly: Boolean = false) = new TimeBlock(moment, moment, readonly)

  def apply(start: DateTime, end: DateTime, readonly: Boolean = false) = new TimeBlock(start, end, readonly)

  def apply(start: DateTime, duration: Long, readonly: Boolean = false) =
    new TimeBlock(start, start.plus(duration), readonly)

  def apply(duration: Long, end: DateTime, readonly: Boolean = false) =
    new TimeBlock(end.minus(duration), end, readonly)

  def apply(source: ITimePeriod): TimeBlock = {
    Guard.shouldNotBeNull(source, "source")
    new TimeBlock(source.getStart, source.getEnd, source.isReadonly)
  }

  def apply(source: ITimePeriod, readonly: Boolean) {
    Guard.shouldNotBeNull(source, "source")
    new TimeBlock(source.getStart, source.getEnd, readonly)
  }
}

/**
 * Time Block 을 표현하는 인터페이스입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 13.
 */
trait ITimeBlock extends ITimePeriod {

  protected var _duration: Long = 0

  /**
   * 시작 시각을 설정합니다.
   */
  def setStart(nv: DateTime) { assertMutable; _start = nv }

  /**
   * 완료 시각을 설정합니다.
   */
  def setEnd(nv: DateTime) { assertMutable; _end = nv }


  override def getDuration = _duration

  /**
   * 기간을 설정합니다.
   */
  override def setDuration(nv: Long) {
    assertMutable
    assertValidDuration(nv)
    durationFromStart(nv)
  }

  def copy(): ITimeBlock = {
    copy(TimeSpec.ZeroMillis)
  }

  override def copy(offset: Long): ITimeBlock = {
    log.debug("[{}] 를 offset[{}]을 주어 복사한 객체를 반환합니다.", this.getClass.getName, offset)
    if (offset == TimeSpec.ZeroMillis)
      return TimeBlock(this)

    val start = if (hasStart) _start.plus(offset) else _start
    val end = if (hasEnd) _end.plus(offset) else _end
    TimeBlock(start, end)
  }

  /**
   * 기간을 설정합니다.
   *
   * @param newStart    시작 시각
   * @param newDuration 기간
   */
  def setup(newStart: DateTime, newDuration: Long) {
    assertValidDuration(newDuration)
    log.debug("새로운 기간으로 TimeBlock을 설정합니다. newStart=[{}], newDuration=[{}]", newStart, newDuration)

    _start = newStart
    _duration = newDuration
    _end = _start.plus(_duration)
  }

  /**
   * 시작 시각으로부터 지정된 getDuration 만큼을 기간으로 설정합니다.
   *
   * @param duration 간격
   */
  def durationFromStart(duration: Long) {
    assertValidDuration(duration)

    _duration = duration
    _end = _start.plus(_duration)
  }

  /**
   * 완료 시각 기준으로 getDuration 만큼을 기간으로 설정합니다.
   *
   * @param duration 간격
   */
  def durationFromEnd(duration: Long) {
    assertValidDuration(duration)

    _duration = duration
    _start = _end.minus(_duration)
  }

  /**
   * 지정된 offset 이전의 {@link ITimeBlock} 을 찾습니다.
   */
  def getPreviousBlock(offset: Long = TimeSpec.ZeroMillis): ITimeBlock = {
    // val endOff = if(offset > TimeSpec.ZeroMillis) -offset else offset
    val result = TimeBlock(getDuration, getStart.minus(Math.abs(offset)), isReadonly)
    log.debug("직전 Block을 구합니다. offset=[{}], previousBlock=[{}]", offset, result)
    result
  }

  /**
   * 지정된 offset 이후의 {@link ITimeBlock} 을 찾습니다.
   */
  def getNextBlock(offset: Long = TimeSpec.ZeroMillis): ITimeBlock = {
    // var startOff= if (offset > TimeSpec.ZeroMillis) offset else -offset
    val result = TimeBlock(_end.plus(Math.abs(offset)), getDuration, isReadonly)
    log.debug("직후 Block을 구합니다. offset=[{}], nextBlock=[{}]", offset, result)
    result
  }

  override def getIntersection(other: ITimePeriod): ITimeBlock = {
    TimeTool.getIntersectionBlock(this, other)
  }

  override def getUnion(other: ITimePeriod): ITimeBlock = {
    TimeTool.getUnionBlock(this, other)
  }

  protected def assertValidDuration(duration: Long) {
    assert(duration >= TimeSpec.MinPeriodDuration,
           "duration should be 0 or positive number! duration=" + duration)
  }
}
