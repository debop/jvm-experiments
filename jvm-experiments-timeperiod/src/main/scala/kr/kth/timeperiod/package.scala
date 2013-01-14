package kr.kth

import commons.slf4j.Logger


/**
 * kr.kth.commons.timeperiod package object
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 9.
 */
package object timeperiod {

  lazy val log = Logger("kr.kth.timeperiod")

  /**
   * 기본 주차 규칙 (ISO 8601)
   */
  val DefaultWeekOfYearRule = WeekOfYearRuleKind.Iso8601


  implicit def rangeToTimeBlock(range: ITimeRange): TimeBlock = {
    log.debug("TimeRange[{}] 를 TimeBlock으로 변환합니다.", range)
    if (range == null) null
    else {
      if (range.isAnyTime) TimeBlock.Anytime
      else TimeBlock(range.getStart, range.getEnd, range.isReadonly)
    }
  }

  implicit def blockToTimeRange(block: ITimeBlock): TimeRange = {
    log.debug("TimeBlock[{}] 을 TimeRange로 변환합니다.", block)
    if (block == null) null
    else if (block.isAnyTime) TimeRange.Anytime
    else TimeRange(block.getStart, block.getEnd, block.isReadonly)
  }

  implicit def rangeToTimeInterval(range: ITimeRange): TimeInterval = {
    log.debug("TimeRange[{}]를 TimeInterval로 변환합니다.", range)
    if (range == null) null
    else if (range.isAnyTime) TimeInterval.Anytime
    else TimeInterval(range.getStart, range.getEnd, readonly = range.isReadonly)
  }
}
