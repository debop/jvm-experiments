package kr.kth.timeperiod

import kr.kth.commons.ValueObjectBase
import org.joda.time.DateTime

/**
 * kr.kth.timeperiod.TimePeriod
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 14
 */
@SerialVersionUID(7093665366996191218L)
abstract class TimePeriod extends ValueObjectBase with ITimePeriod {

  def this(start: DateTime, end: DateTime, readonly: Boolean) {
    this()
    _start = if (start != null) start else TimeSpec.MinPeriodTime
    _end = if (end != null) end else TimeSpec.MaxPeriodTime
    _readonly = readonly
  }

  def this(start: DateTime, duration: Long, readonly: Boolean) {
    this()
    _start = if (start != null) start else TimeSpec.MinPeriodTime
    _end = TimeSpec.MaxPeriodTime
    super.setDuration(duration)
    _readonly = readonly
  }

  def this(source: ITimePeriod, readonly: Option[Boolean] = None) {
    this()
    _start = source.getStart
    _end = source.getEnd
    _readonly = readonly.getOrElse(source.isReadonly)
  }

  def this(moment: DateTime, readonly: Boolean) {
    this(moment, moment, readonly)
  }

  def this(readonly: Boolean) {
    this(TimeSpec.MinPeriodTime, TimeSpec.MaxPeriodTime, readonly)
  }
}
