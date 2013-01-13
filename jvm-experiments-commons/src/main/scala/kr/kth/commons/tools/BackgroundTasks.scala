package kr.kth.commons.tools

import kr.kth.commons.slf4j.Logging
import java.util.concurrent.atomic.AtomicInteger

/**
 * Background 작업과 관련된 Object 입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 13.
 */
object BackgroundTasks extends Logging {

  val threadId: AtomicInteger = new AtomicInteger(0)

  /**
   * 지정된 action을 수행하는 스레드를 생성하고 시작시킵니다.
   * @param threadName 스레드 명
   * @param daemon 백그라운드 스레드로 수행할 것인가 여부
   * @param action 스레드에서 수행할 코드 블럭
   * @return 생성된 스레드
   */
  def spawn(threadName: String, daemon: Boolean = false)(action: => Unit): Thread = {
    val thread = new Thread(threadName) {
      override def run() {
        try {
          action
        } catch {
          case e: Throwable => log.error("스레드[{}-{}] 실행중에 예외가 발생했습니다.", threadName, Thread.currentThread())
        }
      }
    }
    thread.setDaemon(daemon)
    thread.start()
    thread
  }

  /**
   * 지정된 action을 수행하는 백그라운드 스레드를 생성하고 시작시킵니다.
   * @param threadName 스레드 명
   * @param action 스레드에서 수행할 코드 블럭
   * @return 생성된 스레드
   */
  def spawnDaemon(threadName: String)(action: => Unit) = spawn(threadName, daemon = true)(action)

  /**
   * 지정된 action을 수행할 백그라운드 스레드를 생성하고 시작합니다.
   * @param action 스레드에서 수행할 코드 블럭
   */
  def apply(action: => Unit) {
    spawnDaemon("background-" + threadId.incrementAndGet())(action)
  }
}
