package kr.kth.timeperiod

/**
 * 검색 방향 (전진, 후진)
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 13.
 */
object SeekDirection extends Enumeration {
  type ScalaSeekDirection = Value

  /**
   * 시간 값을 증가시키는 방향 (미래로)
   */
  val Forward = Value("Forward")

  /**
   * 시간 값을 감소시키는 방향 (과거로)
   */
  val Backward = Value("Backward")

  // NOTE: apply, withName 메소드를 사용하세요
  //  def valueOf(value: String) = {
  //    value.toUpperCase match {
  //      case "FORWARD" => SeekDirection.Forward
  //      case "BACKWARD" => SeekDirection.Backward
  //      case _ => throw new IllegalArgumentException("알 수 없는 값입니다. value=" + value)
  //    }
  //  }
}
