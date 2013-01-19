package kr.kth.timeperiod

import kr.kth.commons.slf4j.Logging
import org.joda.time.DateTime

/**
 * Date 를 요소로 가지는 {@link TreeSet[Date]} 입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 13.
 */
@SerialVersionUID(6897988915122604105L)
class DateTimeSet {

}
//@SerialVersionUID(6897988915122604105L)
//class DateTimeSet extends IDateTimeSet {
//  override implicit def ordering(): Ordering[DateTime] = DateTimeOrdering
//}
//
//object DateTimeSet extends Logging {
//
//  def apply(): DateTimeSet = {
//    new DateTimeSet()(DateTimeOrdering)
//  }
//
//  def apply(moments: DateTime*): DateTimeSet = {
//    val set = new DateTimeSet()(DateTimeOrdering)
//    set.addAll(moments)
//    set
//  }
//}
//
//trait IDateTimeSet extends collection.mutable.SortedSet[DateTime] with Logging {
//
//  // override implicit def ordering: Ordering[DateTime] = DateTimeOrdering
//
//  /**
//    * 지정된 인텍스에 해당하는 요소를 반환합니다.
//    */
//  def get(index:Int):DateTime = drop(index).head
////  def get(index: Int): DateTime = {
////    if (isEmpty)
////      return null
////    if (index < 0 || index >= size)
////      return null
////
////    var result: DateTime = null
////    var i = 0
////    for (x <- this) {
////      result = x
////      if (i == index)
////        return result
////      i += 1
////    }
////    result
////  }
//
//  /**
//   * 요소 중 최소값을 반환한다. 없다면 null을 반환한다.
//   */
//  def getMin: DateTime = if (isEmpty) null else firstKey
//
//  /**
//   * 요소 중 최대 값을 반환합니다. 없다면 null을 반환한다.
//   */
//  def getMax: DateTime = if (isEmpty) null else lastKey
//
//  /**
//   * {@link #getMax()} - {@link #getMin()} 값을 나타낸다. 둘 중 하나라도 null이면, null을 반환한다.
//   */
//  def getDuration: Option[Long] = {
//    if (isEmpty)
//      return None
//
//    val minTime = getMin
//    val maxTime = getMax
//
//    if (minTime != null && maxTime != null) Some(maxTime.getMillis - minTime.getMillis) else None
//  }
//
//  /**
//   * 모둔 요소가 같은 값을 가졌는가?
//   */
//  def isMoment: Boolean = {
//    getDuration.getOrElse(TimeSpec.ZeroMillis) == TimeSpec.ZeroMillis
//  }
//
//  /**
//   * 요소가 모든 시각을 나타내는가? 최소값, 최소값 모두 null일 때 any timePart 입니다.
//   */
//  def isAnyTime: Boolean = {
//    val minTime = getMin
//    val maxTime = getMax
//
//    (min != null) && (min.getMillis == TimeSpec.MinMillis) &&
//    (max != null) && (max.getMillis == TimeSpec.MaxMillis)
//  }
//
//  /**
//   * 지정한 {@link Date} 컬렉션의 모든 요소들을 추가합니다.
//   *
//   * @param moments 추가할 { @link DateTime} 컬렉션
//   */
//  def addAll(moments: Iterable[DateTime]): Boolean = {
//    this ++= moments
//    true
//  }
//
//  /**
//   * 지정한 인덱스와 갯수에 해당하는 시각들의 Duration을 구합니다.
//   */
//  def getDurations(startIndex: Int, count: Int): Iterable[Long] = {
//    log.debug("복수개의 Duration들을 구합니다. startIndex=[{}], count=[{}]", startIndex, count)
//
//    val endIndex = Math.min(startIndex + count, size - 1)
//    val durations = new collection.mutable.ArrayBuffer[Long]()
//
//    for (i <- startIndex until endIndex)
//      durations.append(get(i + 1).getMillis - get(i).getMillis)
//
//    durations
//  }
//
//  /**
//   * 지정된 시각 바로 전 시각을 찾습니다. 없으면 null을 반환합니다.
//   */
//  def findPrevious(moment: DateTime): Option[DateTime] = {
//    log.debug("지정된 시각[{}] 직전 시각을 찾습니다. 없으면 null을 반환합니다.", moment)
//
//    if (isEmpty) None
//    else {
//      val millis = moment.getMillis
//      reversed.find(x => x.getMillis < millis)
//    }
//  }
//
//  /**
//   * 지정된 시각 바로 다음 시각을 찾습니다. 없으면 null을 반환합니다.
//   */
//  def findNext(moment: DateTime): Option[DateTime] = {
//    log.debug("지정된 시각[{}] 직후 시각을 찾습니다. 없으면 null을 반환합니다.", moment)
//    if (isEmpty) None
//    else {
//      val millis = moment.getMillis
//      find(x => x.getMillis > millis)
//    }
//  }
//}