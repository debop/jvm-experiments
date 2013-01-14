package kr.kth.timeperiod

import kr.kth.commons.Guard
import kr.kth.commons.slf4j.Logging
import org.joda.time.DateTime

/**
 * kr.kth.timeperiod.TimeRange
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 14
 */
class TimeRange(start: DateTime, end: DateTime, readonly: Boolean = true)
  extends TimePeriod(start, end, readonly) with ITimeRange {

}

object TimeRange extends Logging {

  lazy val Anytime = apply(readonly = true)

  def apply(readonly: Boolean) =
    new TimeRange(start = TimeSpec.MinPeriodTime, end = TimeSpec.MaxPeriodTime, readonly = readonly)

  def apply(moment: DateTime, readonly: Boolean = true) =
    new TimeRange(moment, moment, readonly)

  def apply(start: DateTime, end: DateTime, readonly: Boolean = true) =
    new TimeRange(start, end, readonly)

  def apply(start: DateTime, duration: Long, readonly: Boolean = true) =
    new TimeRange(start, start.plus(duration), readonly)

  def apply(source: ITimePeriod) = {
    Guard.shouldNotBeNull(source, "source")
    if (source == Anytime) Anytime
    else apply(source.getStart, source.getEnd, source.isReadonly)
  }

  def apply(source: ITimePeriod, readonly: Boolean) = {
    Guard.shouldNotBeNull(source, "source")
    apply(source.getStart, source.getEnd, readonly)
  }

  def unapply(range: ITimePeriod) = (range.getStart, range.getEnd, range.isReadonly)
}

trait ITimeRange extends ITimePeriod {

  /**
   * 시작시각을 설정합니다.
   */
  def setStart(newStart: DateTime) {
    assertMutable
    _start = newStart
  }

  /**
   * 완료시각을 설정합니다.
   */
  def setEnd(newEnd: DateTime) {
    assertMutable
    _end = newEnd
  }

  /**
   * 시작시각을 지정된 시각으로 변경합니다. 기존 시작시각보다 작은 값 (과거) 이여야 합니다.
   */
  def expandStartTo(moment: DateTime) {
    assertMutable

    if (_start.compareTo(moment) > 0) {
      log.debug("expand start[{}] to [{}]", _start, moment)
      _start = moment
    }
  }

  /**
   * 완료시각을 지정된 시각으로 변경합니다. 기존 완료시각보다 큰 값 (미래) 이어야 합니다.
   */
  def expandEndTo(moment: DateTime) {
    assertMutable

    if (_end.compareTo(moment) < 0) {
      log.debug("expand end[{}] to [{}]", _end, moment)
      _end = moment
    }
  }

  /**
   * 시작시각과 완료시각을 지정된 시각으로 설정합니다.
   */
  def expandTo(moment: DateTime) {
    assertMutable
    expandStartTo(moment)
    expandEndTo(moment)
  }

  /**
   * 시작시각과 완료시각을 지정된 기간 정보를 기준으로 변경합니다.
   */
  def expandTo(period: ITimePeriod) {
    assertMutable
    Guard.shouldNotBeNull(period, "period")
    if (period.hasStart) expandStartTo(period.getStart)
    if (period.hasEnd) expandEndTo(period.getEnd)
  }

  /**
   * 시작시각을 지정된 시각으로 변경합합니다. 기존 시작시각보다 큰 값(미래) 이어야 합니다.
   */
  def shrinkStartTo(moment: DateTime) {
    assertMutable
    if (_start.compareTo(moment) < 0) {
      log.debug("shrink start=[{}] to [{}]", _start, moment)
      _start = moment
    }
  }

  /**
   * 완료시각을 지정된 시각으로 변경합니다. 기존 완료시각보다 작은 값(과거) 이어야 하고, 시작시각보다는 같거나 커야 합니다.
   */
  def shrinkEndTo(moment: DateTime) {
    assertMutable
    if (_start.compareTo(moment) < 0) {
      log.debug("shrink end=[{}] to [{}]", _end, moment)
      _end = moment
    }
  }

  /**
   * 기간을 지정된 기간으로 축소시킵니다.
   */
  def shrinkTo(period: ITimePeriod) {
    assertMutable
    Guard.shouldNotBeNull(period, "period")

    if (period.hasStart) shrinkStartTo(period.getStart)
    if (period.hasEnd) shrinkEndTo(period.getEnd)
  }
}
