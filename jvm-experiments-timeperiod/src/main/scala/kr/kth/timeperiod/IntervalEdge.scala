package kr.kth.timeperiod

/**
 * 구간의 끝의 열림/닫힘을 구분합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 13.
 */
object IntervalEdge extends Enumeration {

  type IntervalEdge = Value

  // NOTE: withName 메소드를 사용하세요

  /**
   * 페구간
   */
  val Closed = Value("Closed")
  /**
   * 개구간
   */
  val Open = Value("Open")
}
