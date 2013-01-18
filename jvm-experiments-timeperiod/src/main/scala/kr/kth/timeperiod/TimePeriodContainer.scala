package kr.kth.timeperiod

import java.util
import kr.kth.commons.slf4j.Logging
import kr.kth.commons.tools.ScalaHash
import kr.kth.commons.{ValueObjectBase, Guard, SortDirection}
import org.joda.time.DateTime
import scala.collection.JavaConversions._
import scala.collection.{JavaConversions, TraversableOnce}

/**
 * kr.kth.timeperiod.TimePeriodContainer
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 14
 */
class TimePeriodContainer extends ITimePeriodContainer {

  def this(periods: Iterable[ITimePeriod]) {
    this()
    _periods ++= periods
  }
}

object TimePeriodContainer extends Logging {

  def apply(): TimePeriodContainer = new TimePeriodContainer()

  def apply(periods: Iterable[ITimePeriod]): TimePeriodContainer = new TimePeriodContainer(periods)

  def apply(periods: ITimePeriod*): TimePeriodContainer = apply(periods.toIterable)
}

/**
 * {@link ITimePeriod} 를 요소로 가지는 컨테이너를 나타냅니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 13.
 */
trait ITimePeriodContainer extends ValueObjectBase with scala.collection.Iterable[ITimePeriod] with ITimePeriod {

  protected var _periods = collection.mutable.ArrayBuffer[ITimePeriod]()

  protected def getPeriods: collection.mutable.ArrayBuffer[ITimePeriod] = _periods

  def iterator: Iterator[ITimePeriod] = _periods.iterator

  def reverseIterator: Iterator[ITimePeriod] = _periods.reverseIterator

  def add(elem: ITimePeriod) { append(elem) }

  def append(elems: ITimePeriod*) { appendAll(elems) }

  /**
   * 지정한 {@link ITimePeriod} 들을 컨테이너에 모두 추가한다.
   */
  def appendAll(items: TraversableOnce[ITimePeriod]) {
    _periods.appendAll(items)
  }

  def clear() { _periods.clear() }

  def contains(elem: ITimePeriod): Boolean = _periods.contains(elem)

  /**
   * 지정한 기간을 포함하고 있는지 검사한다.
   */
  def containsPeriod(target: ITimePeriod): Boolean = _periods.contains(target)


  def remove(elem: ITimePeriod*) { _periods --= elem; }

  // def remove(n: Int) { _periods.remove(n) }

  override def size = _periods.size

  override def isReadonly = false

  override def isEmpty: Boolean = (size == 0)

  def get(index: Int): ITimePeriod = _periods.get(index)

  def set(index: Int, element: ITimePeriod): ITimePeriod = {
    _periods.set(index, element)
    _periods(index)
  }

  def indexOf(o: Any): Int = if (o.isInstanceOf[ITimePeriod]) _periods.indexOf(o) else -1

  def lastIndexOf(o: Any): Int = if (o.isInstanceOf[ITimePeriod]) _periods.lastIndexOf(o) else -1

  def listIterator(): java.util.ListIterator[ITimePeriod] =
    JavaConversions.bufferAsJavaList(_periods).listIterator()

  def listIterator(index: Int): util.ListIterator[ITimePeriod] =
    JavaConversions.bufferAsJavaList(_periods).listIterator(index)

  def subList(fromIndex: Int, toIndex: Int): java.util.List[ITimePeriod] =
    _periods.subList(fromIndex, toIndex)

  /**
   * 모든 항목을 {@link ITimePeriod#getStart()} 를 기준으로 정렬합니다.
   */
  def sortByStart(direction: SortDirection = SortDirection.ASC) {
    direction match {
      case SortDirection.ASC =>
        _periods = _periods.sortWith(_ < _) // _.getStart.getMillis < _.getStart.getMillis)
      case SortDirection.DESC =>
        _periods = _periods.sortWith(_ > _) //_.getStart.getMillis > _.getStart.getMillis)
    }
  }

  /**
   * 모든 항목을 {@link ITimePeriod#getEnd()} 를 기준으로 정렬합니다.
   */
  def sortByEnd(direction: SortDirection = SortDirection.ASC) {
    direction match {
      case SortDirection.ASC =>
        _periods = _periods.sortWith(_.getEnd.getMillis < _.getEnd.getMillis)
      case SortDirection.DESC =>
        _periods = _periods.sortWith(_.getEnd.getMillis > _.getEnd.getMillis)
    }
  }

  /**
   * 모든 항목을 {@link ITimePeriod#getDuration()} 를 기준으로 정렬합니다.
   */
  def sortByDuration(direction: SortDirection = SortDirection.ASC) {
    direction match {
      case SortDirection.ASC =>
        _periods = _periods.sortWith(_.getDuration < _.getDuration)
      case SortDirection.DESC =>
        _periods = _periods.sortWith(_.getDuration > _.getDuration)
    }
  }

  override def getStart: DateTime =
    if (size > 0) _periods.minBy(x => x.getStart.getMillis).getStart
    else TimeSpec.MinPeriodTime

  def setStart(newStart: DateTime) {
    if (size > 0)
      move(newStart.getMillis - getStart.getMillis)
  }

  override def getEnd: DateTime =
    if (size > 0) _periods.maxBy(x => x.getEnd.getMillis).getEnd
    else TimeSpec.MaxPeriodTime

  def setEnd(newEnd: DateTime) {
    if (size > 0)
      move(newEnd.getMillis - getStart.getMillis)
  }

  override def getDuration: Long =
    if (hasPeriod) getEnd.getMillis - getStart.getMillis
    else TimeSpec.MaxPeriodDuration

  override def getDurationDescription =
    TimeFormatter.instance.getDuration(this.getDuration, DurationFormatKind.Detailed)

  override def hasStart = (size > 0) && (getStart != TimeSpec.MinPeriodTime)

  override def hasEnd = (size > 0) && (getEnd != TimeSpec.MaxPeriodTime)

  override def hasPeriod = (hasStart && hasEnd)

  override def isMoment = (hasPeriod) && (getStart == getEnd)

  override def isAnytime = (!hasStart && !hasEnd)

  override def setup(newStart: DateTime, newEnd: DateTime) {
    throw new UnsupportedOperationException("ITimePeriodContainer에서는 setup() 메소드를 지원하지 않습니다.")
  }

  override def copy(offset: Long) = {
    throw new UnsupportedOperationException("ITimePeriodContainer에서는 copy() 메소드를 지원하지 않습니다.")
  }

  override def move(offset: Long) {
    if (offset != 0L)
      _periods.foreach(_.move(offset))
  }

  def isSampePeriod(other: ITimePeriod): Boolean = {
    Guard.shouldNotBeNull(other, "other")
    (getStart == other.getStart) && (getEnd == other.getEnd)
  }

  override def hasInside(moment: DateTime): Boolean =
    Times.hasInside(this, moment)

  override def hasInside(other: ITimePeriod): Boolean =
    Times.hasInside(this, other)

  override def intersectsWith(other: ITimePeriod): Boolean =
    Times.intersectsWith(this, other)

  override def overlapsWith(other: ITimePeriod): Boolean =
    Times.overlapsWith(this, other)

  override def reset() {
    _periods.clear()
  }

  override def getRelation(other: ITimePeriod) =
    Times.getRelation(this, other)

  override def getDescription(formatter: Option[ITimeFormatter] = None) =
    format(formatter)

  override protected def format(formatter: Option[ITimeFormatter]) =
    formatter.getOrElse(TimeFormatter.instance)
      .getCollectionPeriod(size, getStart, getEnd, getDuration)

  override def getIntersection(other: ITimePeriod): ITimePeriod =
    Times.getIntersectionRange(this, other)

  override def getUnion(other: ITimePeriod): ITimePeriod =
    Times.getUnionRange(this, other)

  def indexOf(elem: ITimePeriod): Int =
    _periods.indexOf(elem)

  def removeAt(index: Int) {
    _periods.remove(index)
  }

  override def compareTo(other: ITimePeriod): Int = getStart compareTo other.getStart

  override def compare(other: ITimePeriod): Int = getStart compareTo other.getStart

  override def equals(other: Any): Boolean = {
    (other != null) &&
      (other.isInstanceOf[ITimePeriod]) &&
      isSampePeriod(other.asInstanceOf[ITimePeriod])
  }

  override def hashCode =
    ScalaHash.compute(getStart, getEnd, isReadonly)

  override def toString: String =
    getDescription()


}

