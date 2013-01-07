package kr.kth.commons.conversions

import java.util.concurrent.Callable

/**
 * Thread 관련 변환 메소드 추가
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 2
 */
object thread {

  /**
   * 지정된 Procedure를 Runnable 인스턴스로 만듭니다.
   * @param f  스레드에서 실행할 메소드
   * @return
   */
  implicit def makeRunnable(f: => Unit): Runnable = new Runnable {
    def run() {
      f
    }
  }

  /**
   * 지정한 함수를 Callable[T] 인스턴스로 만듭니다.
   * @param f  스레드에서 실행할 메소드
   * @tparam T 스레드 완료 후 결과
   * @return 스레드 완료 결과
   */
  implicit def makeCallable[T](f: => T): Callable[T] = new Callable[T] {
    def call() = f
  }

}
