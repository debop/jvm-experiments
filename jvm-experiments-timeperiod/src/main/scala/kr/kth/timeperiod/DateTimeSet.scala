package kr.kth.timeperiod

import org.joda.time.DateTime

/**
 * kr.kth.timeperiod.DateTimeSet
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 13.
 */
class DateTimeSet extends collection.mutable.TreeSet[DateTime] with IDateTimeSet {

  def this(moments: Iterable[DateTime]) {
    this()
    this ++= moments
  }

  /**
   * 지정된 인텍스에 해당하는 요소를 반환합니다.
   */
  def get(index: Int) = ???

  /**
   * 요소 중 최소값을 반환한다. 없다면 null을 반환한다.
   */
  def getMin() = ???

  /**
   * 요소 중 최대 값을 반환합니다. 없다면 null을 반환한다.
   */
  def getMax() = ???

  /**
   * {@link #getMax()} - {@link #getMin()} 값을 나타낸다. 둘 중 하나라도 null이면, null을 반환한다.
   */
  def getDuration = ???

  /**
   * 모둔 요소가 같은 값을 가졌는가?
   */
  def isMoment = ???

  /**
   * 요소가 모든 시각을 나타내는가? 최소값, 최소값 모두 null일 때 any timePart 입니다.
   */
  def isAnyTime = ???

  /**
   * 지정한 {@link Date} 컬렉션의 모든 요소들을 추가합니다.
   *
   * @param moments 추가할 { @link DateTime} 컬렉션
   */
  def addAll(moments: Iterable[DateTime]) = ???

  /**
   * 지정한 인덱스와 갯수에 해당하는 시각들의 Duration을 구합니다.
   */
  def getDuration(startIndex: Int, count: Int) = ???

  /**
   * 지정된 시각 바로 전 시각을 찾습니다. 없으면 null을 반환합니다.
   */
  def findPrevious(moment: DateTime) = ???

  /**
   * 지정된 시각 바로 다음 시각을 찾습니다. 없으면 null을 반환합니다.
   */
  def findNext(moment: DateTime) = ???

  implicit def ordering = ???
}


trait IDateTimeSet extends collection.mutable.Set[DateTime] {

  /**
   * 지정된 인텍스에 해당하는 요소를 반환합니다.
   */
  def get(index: Int): DateTime

  /**
   * 요소 중 최소값을 반환한다. 없다면 null을 반환한다.
   */
  def getMin(): DateTime

  /**
   * 요소 중 최대 값을 반환합니다. 없다면 null을 반환한다.
   */
  def getMax(): DateTime

  /**
   * {@link #getMax()} - {@link #getMin()} 값을 나타낸다. 둘 중 하나라도 null이면, null을 반환한다.
   */
  def getDuration: Long

  /**
   * 요소가 없는 컬렉션인가?
   */
  def isEmpty: Boolean

  /**
   * 모둔 요소가 같은 값을 가졌는가?
   */
  def isMoment: Boolean

  /**
   * 요소가 모든 시각을 나타내는가? 최소값, 최소값 모두 null일 때 any timePart 입니다.
   */
  def isAnyTime: Boolean

  /**
   * 지정한 {@link Date} 컬렉션의 모든 요소들을 추가합니다.
   *
   * @param moments 추가할 { @link DateTime} 컬렉션
   */
  def addAll(moments: Iterable[DateTime]): Boolean

  /**
   * 지정한 인덱스와 갯수에 해당하는 시각들의 Duration을 구합니다.
   */
  def getDuration(startIndex: Int, count: Int): List[Long]

  /**
   * 지정된 시각 바로 전 시각을 찾습니다. 없으면 null을 반환합니다.
   */
  def findPrevious(moment: DateTime): DateTime

  /**
   * 지정된 시각 바로 다음 시각을 찾습니다. 없으면 null을 반환합니다.
   */
  def findNext(moment: DateTime): DateTime
}
