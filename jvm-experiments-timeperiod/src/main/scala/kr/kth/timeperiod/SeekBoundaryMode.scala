package kr.kth.timeperiod

/**
 * kr.kth.timeperiod.SeekBoundaryMode
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 14
 */
object SeekBoundaryMode extends Enumeration {

    type SeekBoundaryMode = Value

    val Seek = Value("Seek")
    // 0
    val Next = Value("Next") // 1
}
