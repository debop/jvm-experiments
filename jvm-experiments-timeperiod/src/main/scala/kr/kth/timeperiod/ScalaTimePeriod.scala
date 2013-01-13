package kr.kth.timeperiod

import org.joda.time.DateTime
import kr.kth.timeperiod.ScalaPeriodRelation._

/**
 * 시간 간격을 표현합니다. 시작~완료 구간과 기간을 표현하는 인터페이스입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 13.
 */
trait ScalaTimePeriod extends Comparable[ScalaTimePeriod] with Serializable {

  private var _start: DateTime = _
  private var _end: DateTime = _
  private val _readonly: Boolean = false

  def start: DateTime = _start

  def end: DateTime = _end

  def duration: Long

  def getDurationString: String

  def hasStart: Boolean

  def hasEnd: Boolean

  def hasPeriod: Boolean

  def isMoment: Boolean = (start == end)

  def isAnyTime: Boolean

  def isReadonly: Boolean = _readonly

  def setup(newStart: DateTime, newEnd: DateTime)

  def copy(offset: Long): ScalaTimePeriod

  def move(offset: Long)

  def isSamePeriod(that: ScalaTimePeriod): Boolean

  def hasInside(moment: DateTime): Boolean

  def hasInside(that: ScalaTimePeriod): Boolean

  def intersectsWith(that: ScalaTimePeriod): Boolean

  def overlapWith(that: ScalaTimePeriod): Boolean

  def reset()

  def getRelation(that: ScalaTimePeriod): ScalaPeriodRelation

  def getDescription(formatter: ScalaTimeFormatter): String

  def getIntersection(that: ScalaTimePeriod): ScalaTimePeriod

  def getUnion(that: ScalaTimePeriod): ScalaTimePeriod
}
