package kr.kth.timeperiod

import kr.kth.commons.Guard
import kr.kth.commons.slf4j.Logging
import kr.kth.commons.tools.ScalaHash
import kr.kth.timeperiod.IntervalEdge._
import org.joda.time.DateTime

/**
 * kr.kth.timeperiod.TimeInterval
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 14
 */
class TimeInterval(start: DateTime,
                   end: DateTime,
                   startEdge: IntervalEdge = IntervalEdge.Closed,
                   endEdge: IntervalEdge = IntervalEdge.Closed,
                   intervalEnabled: Boolean = true,
                   readonly: Boolean = false)
  extends TimePeriod(start, end, readonly) with ITimeInterval {

  _startEdge = startEdge
  _endEdge = endEdge
  _intervalEnabled = intervalEnabled

}

object TimeInterval extends Logging {

  lazy val Anytime = apply(readonly = true)

  def apply(readonly: Boolean) =
    apply(start = TimeSpec.MinPeriodTime,
          end = TimeSpec.MaxPeriodTime,
          readonly = readonly)

  def apply(moment: DateTime,
            startEdge: IntervalEdge = IntervalEdge.Closed,
            endEdge: IntervalEdge = IntervalEdge.Closed,
            intervalEnabled: Boolean = true,
            readonly: Boolean = true) =
    apply(moment,
          moment,
          startEdge,
          endEdge,
          intervalEnabled,
          readonly)

  def apply(start: DateTime,
            end: DateTime,
            startEdge: IntervalEdge = IntervalEdge.Closed,
            endEdge: IntervalEdge = IntervalEdge.Closed,
            intervalEnabled: Boolean = true,
            readonly: Boolean = true) =
    new TimeInterval(start, end, startEdge, endEdge, intervalEnabled, readonly)

  def apply(start: DateTime,
            duration: Long,
            startEdge: IntervalEdge = IntervalEdge.Closed,
            endEdge: IntervalEdge = IntervalEdge.Closed,
            intervalEnabled: Boolean = true,
            readonly: Boolean = true) =
    apply(start, start.plus(duration), startEdge, endEdge, intervalEnabled, readonly)

  def apply(source: ITimePeriod) = {
    Guard.shouldNotBeNull(source, "source")
    if (source == Anytime) Anytime
    else if (source.isInstanceOf[ITimeInterval]) {
      val interval = source.asInstanceOf[ITimeInterval]
      apply(interval.getStart,
            interval.getEnd,
            interval.getStartEdge,
            interval.getEndEdge,
            interval.isIntervalEnabled,
            interval.isReadonly)
    } else {
      apply(source.getStart, source.getEnd, readonly = source.isReadonly)
    }
  }

  def apply(source: ITimePeriod, readonly: Boolean) = {
    Guard.shouldNotBeNull(source, "source")

    if (source.isInstanceOf[ITimeInterval]) {
      val interval = source.asInstanceOf[ITimeInterval]
      apply(interval.getStart,
            interval.getEnd,
            interval.getStartEdge,
            interval.getEndEdge,
            interval.isIntervalEnabled,
            readonly)
    } else {
      apply(source.getStart, source.getEnd, readonly = readonly)
    }
  }

  def unapply(range: ITimeInterval) =
    (range.getStart, range.getEnd, range.getStartEdge, range.getEndEdge, range.isIntervalEnabled, range.isReadonly)
}

trait ITimeInterval extends ITimePeriod {

  protected var _intervalEnabled = false
  protected var _startEdge = IntervalEdge.Closed
  protected var _endEdge = IntervalEdge.Closed

  /**
   * 시작 시각이 개구간인지 여부
   */
  def isStartOpen: Boolean = _startEdge == IntervalEdge.Open

  /**
   * 완료 시각이 개구간인지 여부
   */
  def isEndOpen: Boolean = _endEdge == IntervalEdge.Open

  /**
   * 시작시각이 폐구간인지 여부
   *
   * @return 시작시각이 폐구간이면 true, 개구간이면 false를 반환
   */
  def isStartClosed: Boolean = _start == IntervalEdge.Closed

  /**
   * 완료시각이 폐구간인지 여부
   *
   * @return 완료시각이 폐구간이면 true, 개구간이면 false를 반환
   */
  def isEndClosed: Boolean = _end == IntervalEdge.Closed

  /**
   * 기간이 폐구간인지 여부
   */
  def isClosed: Boolean = isStartClosed && isEndClosed

  /**
   * 기간이 비었는가 여부 (시작시각과 완료시각이 같으면 기간은 비었다)
   */
  def isEmpty: Boolean = isMoment && !isClosed

  /**
   * 기간(Interval)로 쓸 수 없는 경우 (isMoment() 가 true 면서, isClosed() 도 true인 경우)
   */
  def isDegenerate: Boolean = isMoment && isClosed

  /**
   * 사용가능한 Interval 인가?
   */
  def isIntervalEnabled: Boolean = _intervalEnabled

  /**
   * 사용가능한 Interval 인지를 설정합니다.
   */
  def setIntervalEnabled(enabled: Boolean = true) { assertMutable; _intervalEnabled = enabled }

  override def hasStart =
    super.hasStart || !isStartClosed

  /**
   * 시작 시각
   */
  def getStartInterval: DateTime = _start

  /**
   * 시작 시각을 설정합니다.
   */
  def setStartInterval(newStart: DateTime) {
    assertMutable;
    assert(newStart.compareTo(_end) <= 0, "새로운 start가 end 보다 작아야 합니다.")
    _start = newStart
  }

  override def getStart: DateTime = if (_intervalEnabled && isStartOpen) _start.plus(1) else _start

  /**
   * 시작 시각의 개/폐구간 종류
   */
  def getStartEdge: IntervalEdge = _startEdge

  /**
   * 시작 시각의 걔/페구간 종류를 설정합니다.
   */
  def setStartEdge(edge: IntervalEdge) { assertMutable; _startEdge = edge }

  override def hasEnd = _end != TimeSpec.MaxPeriodTime || !isEndClosed

  /**
   * 완료 시각
   */
  def getEndInterval: DateTime = _end

  /**
   * 완료 시각을 설정합니다.
   */
  def setEndInterval(newEnd: DateTime) {
    assertMutable;
    assert(_start.compareTo(newEnd) <= 0, "end 는 start보다 크거나 같아야 합니다.")
    _end = newEnd
  }

  override def getEnd = if (_intervalEnabled && isEndOpen) _end.minus(1) else _end

  /**
   * 완료 시각의 개/폐구간 종류
   */
  def getEndEdge: IntervalEdge = _endEdge

  /**
   * 완료 시각의 개/폐구간 종류를 설정합니다.
   */
  def setEndEdge(edge: IntervalEdge) { assertMutable; _endEdge = edge }

  override def getDuration: Long = _end.getMillis - _start.getMillis

  override def setup(newStart: DateTime, newEnd: DateTime) {
    super.setup(newStart, newEnd)
    val (start, end) = Times.adjustPeriod(newStart, newEnd)
    _start = if (start != null) start else TimeSpec.MinPeriodTime
    _end = if (end != null) end else TimeSpec.MaxPeriodTime
  }

  def expandStartTo(moment: DateTime) {
    assertMutable
    if (_start.compareTo(moment) > 0) _start = moment
  }

  def expandEndTo(moment: DateTime) {
    assertMutable
    if (_end.compareTo(moment) < 0) _end = moment
  }

  def expandTo(moment: DateTime) {
    assertMutable
    expandStartTo(moment)
    expandEndTo(moment)
  }

  def expandTo(period: ITimePeriod) {
    assertMutable
    expandStartTo(period.getStart)
    expandEndTo(period.getEnd)
  }


  def shrinkStartTo(moment: DateTime) {
    assertMutable
    if (_start.compareTo(moment) < 0) _start = moment
  }

  def shrinkEndTo(moment: DateTime) {
    assertMutable
    if (_end.compareTo(moment) > 0) _end = moment
  }

  def shrinkTo(moment: DateTime) {
    assertMutable
    shrinkStartTo(moment)
    shrinkEndTo(moment)
  }

  def shrinkTo(period: ITimePeriod) {
    assertMutable
    shrinkStartTo(period.getStart)
    shrinkEndTo(period.getEnd)
  }

  def copy(): ITimeInterval = copy(TimeSpec.ZeroMillis)

  override def copy(offset: Long): ITimeInterval =
    TimeInterval(getStartInterval.plus(offset),
                 getEndInterval.plus(offset),
                 getStartEdge,
                 getEndEdge,
                 isIntervalEnabled,
                 isReadonly)


  override def reset() {
    super.reset()

    _intervalEnabled = true
    _startEdge = IntervalEdge.Closed
    _endEdge = IntervalEdge.Closed
  }

  override def getIntersection(other: ITimePeriod): ITimeInterval = {
    Guard.shouldNotBeNull(other, "other")
    val range = super.getIntersection(other)
    if (range == null) null
    else TimeInterval(range.getStart,
                      range.getEnd,
                      intervalEnabled = _intervalEnabled,
                      readonly = isReadonly)
  }

  override def getUnion(other: ITimePeriod): ITimeInterval = {
    Guard.shouldNotBeNull(other, "other")
    Times.getUnionRange(this, other)
  }

  override def format(formatter: ITimeFormatter): String =
    formatter.getInterval(_start, _end, _startEdge, _endEdge, getDuration)

  override def hashCode: Int =
    ScalaHash.compute(super.hashCode, _startEdge, _endEdge, _intervalEnabled)
}
