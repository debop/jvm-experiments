package kr.kth.timeperiod

import org.joda.time.DateTime
import kr.kth.timeperiod.PeriodRelation._

/**
 * 시간 간격을 표현합니다. 시작~완료 구간과 기간을 표현하는 인터페이스입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 13.
 */
trait ITimePeriod extends Comparable[ITimePeriod] with Serializable {

  private var _start: DateTime = _
  private var _end: DateTime = _
  private val _readonly: Boolean = false

  def getStart: DateTime = _start

  def getEnd: DateTime = _end

  def getDuration: Long

  def getDurationDescription: String

  def hasStart: Boolean

  def hasEnd: Boolean

  def hasPeriod: Boolean

  def isMoment: Boolean = (_start == _end)

  def isAnyTime: Boolean

  def isReadonly: Boolean = _readonly

  def setup(newStart: DateTime, newEnd: DateTime)

  def copy(offset: Long): ITimePeriod

  def move(offset: Long)

  def isSamePeriod(that: ITimePeriod): Boolean

  def hasInside(moment: DateTime): Boolean

  def hasInside(that: ITimePeriod): Boolean

  def intersectsWith(that: ITimePeriod): Boolean

  def overlapWith(that: ITimePeriod): Boolean

  def reset()

  def getRelation(that: ITimePeriod): PeriodRelation

  def getDescription(formatter: ITimeFormatter): String

  def getIntersection(that: ITimePeriod): ITimePeriod

  def getUnion(that: ITimePeriod): ITimePeriod
}
