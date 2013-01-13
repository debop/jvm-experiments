package kr.kth.timeperiod

import org.joda.time.DateTime
import kr.kth.commons.SortDirection

/**
 * {@link ITimePeriod} 를 요소로 가지는 컨테이너를 나타냅니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 13.
 */
trait ITimePeriodContainer extends collection.mutable.ArrayBuffer[ITimePeriod] with ITimePeriod {

  /**
   * 컨테이너의 시작시각을 설정합니다.
   */
  def setStart(newStart: DateTime)

  /**
   * 컨테이너의 완료시각을 설정합니다.
   */
  def setEnd(newEnd: DateTime)

  /**
   * 지정한 기간을 포함하고 있는지 검사한다.
   */
  def containsPeriod(target: ITimePeriod): Boolean

  /**
   * 지정한 {@link ITimePeriod} 들을 컨테이너에 모두 추가한다.
   */
  def addAll(periods: Iterable[ITimePeriod])

  /**
   * 모든 항목을 {@link ITimePeriod#getStart()} 를 기준으로 정렬합니다.
   */
  def sortByStart(direction: SortDirection = SortDirection.ASC)

  /**
   * 모든 항목을 {@link ITimePeriod#getEnd()} 를 기준으로 정렬합니다.
   */
  def sortByEnd(direction: SortDirection = SortDirection.ASC)

  /**
   * 모든 항목을 {@link ITimePeriod#getDuration()} 를 기준으로 정렬합니다.
   */
  def sortByDuration(direction: SortDirection = SortDirection.ASC)
}
