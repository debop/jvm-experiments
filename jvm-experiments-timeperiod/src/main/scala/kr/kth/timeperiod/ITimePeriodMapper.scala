package kr.kth.timeperiod

import org.joda.time.DateTime

/**
 * 기간의 시작시각, 완료시각에 대해 영역의 포함여부를 조절할 수 있도록 Offset에 대한 처리를 제공합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 13.
 */
trait ITimePeriodMapper extends Serializable {
  /**
   * 지정된 Date를 StartOffset을 적용하여 매핑합니다.
   */
  def mapStart(moment: DateTime): DateTime

  /**
   * 지정된 Date를 EndOffset을 적용하여 매핑합니다.
   */
  def mapEnd(moment: DateTime): DateTime

  /**
   * 지정된 Date를 StartOffset 적용을 해제합니다.
   */
  def unmapStart(moment: DateTime): DateTime

  /**
   * 지정된 Date를 EndOffset 적용을 해제합니다.
   */
  def unmapEnd(moment: DateTime): DateTime
}
