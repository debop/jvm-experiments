package kr.kth.commons.unitTesting

import collection.parallel.ThreadPoolTaskSupport
import java.util.concurrent.CountDownLatch
import kr.kth.commons.slf4j.Logging

/**
 * Scala 용 멀티 스레드용 테스트
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 9
 */
object ParallelTestTool extends Logging {

  // def newExecutor = Executors.newFixedThreadPool(Runtime.getRuntime.availableProcessors() * 2)

  /**
   * 지정된 메소드들을 멀티스레드 방식으로 지정된 횟수만큼 실행시킵니다.
   * @param count 실행할 횟수
   * @param block 실행할 코드 블럭
   */
  def runUnitAsParallel(count: Int)(block: => Unit) {
    log.debug("멀티스레드 환경에서 메소드들을 [{}] 번 실행합니다.", count)
    try {
      val latch = new CountDownLatch(count)
      val pc = collection.parallel.mutable.ParArray.iterate(0, count)(x => x)
      pc.tasksupport = new ThreadPoolTaskSupport()
      pc map {
        _ => {
          block
          latch.countDown()
        }
      }
      latch.await()
    } catch {
      case e: InterruptedException => log.warn("작업 중 interrupted 되었습니다.")
      case e: Exception => log.error("작업 중에 예외가 발생했습니다.", e)
    }
    log.debug("멀티스레드 환경에서 메소드들을 [{}] 번 실행했습니다.", count)
  }

  /**
   * 지정된 메소드들을 멀티스레드 방식으로 지정된 횟수만큼 실행시킵니다.
   * @param count 실행할 횟수
   * @param block 실행할 코드 블럭
   */
  def runUnitAsParallelWithIndex(count: Int)(block: => (Int) => Unit) {
    log.debug("멀티스레드 환경에서 메소드들을 [{}] 번 실행합니다.", count)
    try {
      val latch = new CountDownLatch(count)
      val pc = collection.parallel.mutable.ParArray.iterate(0, count)(x => x)
      pc.tasksupport = new ThreadPoolTaskSupport()
      pc map {
        x => {
          block(x)
          latch.countDown()
        }
      }
      latch.await()
    } catch {
      case e: InterruptedException => log.warn("작업 중 interrupted 되었습니다.")
      case e: Exception => log.error("작업 중에 예외가 발생했습니다.", e)
    }
    log.debug("멀티스레드 환경에서 메소드들을 [{}] 번 실행했습니다.", count)
  }

  /**
   * 지정된 메소드들을 멀티스레드 방식으로 지정된 횟수만큼 실행시킵니다.
   * @param count  실행할 횟수
   * @param func  실행할 함수
   * @tparam R 함수의 반환 값
   * @return 전체 함수 실행 반환 값
   */
  def runFuncAsParallel[R](count: Int)(func: => R): scala.collection.Seq[R] = {
    log.debug("멀티스레드 환경에서 함수들을 [{}] 번 실행합니다.", count)
    try {
      val pc = collection.parallel.mutable.ParArray.iterate(0, count)(x => x)
      pc.tasksupport = new ThreadPoolTaskSupport()
      val results = pc.map {
        _ => func
      }.to[Seq]

      log.debug("멀티스레드 환경에서 메소드들을 [{}] 번 실행했습니다.", count)
      return results
    } catch {
      case e: InterruptedException => log.warn("작업 중 interrupted 되었습니다.")
      case e: Exception => log.error("작업 중에 예외가 발생했습니다.", e)
    }
    collection.immutable.Vector[R]()
  }

  def runFuncAsParallelWithIndex[R](count: Int)(func: (Int) => R): scala.collection.Map[Int, R] = {
    log.debug("멀티스레드 환경에서 함수들을 [{}] 번 실행합니다.", count)
    try {
      val pc = collection.parallel.mutable.ParArray.iterate(0, count)(x => x)
      pc.tasksupport = new ThreadPoolTaskSupport()
      val results = pc.map {
        x => (x, func(x))
      }.toMap[Int, R].toBuffer.toMap[Int, R]

      log.debug("멀티스레드 환경에서 메소드들을 [{}] 번 실행했습니다.", count)
      return results
    } catch {
      case e: InterruptedException => log.warn("작업 중 interrupted 되었습니다.")
      case e: Exception => log.error("작업 중에 예외가 발생했습니다.", e)
    }
    collection.immutable.HashMap[Int, R]()
  }
}
