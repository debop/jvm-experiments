package kr.kth.commons.unitTesting

import collection.mutable
import java.util.concurrent.{Callable, Future, Executors, CountDownLatch}
import kr.kth.commons.slf4j.Logging
import scala.collection.JavaConversions._

/**
 * Scala 용 멀티 스레드용 테스트
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 9
 */
object ScalaTestTool extends Logging {

    def newExecutor = Executors.newFixedThreadPool(Runtime.getRuntime.availableProcessors() * 2)

    /**
     * 지정된 메소드들을 멀티스레드 방식으로 지정된 횟수만큼 실행시킵니다.
     * @param count 실행할 횟수
     * @param block 실행할 코드 블럭
     */
    def runUnit(count: Int)(block: => Unit) {
        log.debug("멀티스레드 환경에서 메소드들을 [{}] 번 실행합니다.", count)
        val executor = newExecutor
        try {
            val latch = new CountDownLatch(count)
            for (i <- 0 to count) {
                executor.submit(new Runnable {
                    def run() {
                        block
                        latch.countDown()
                    }
                })
            }
            latch.await()
        } catch {
            case e: InterruptedException => log.warn("작업 중 interrupted 되었습니다.")
            case e: Exception => log.error("작업 중에 예외가 발생했습니다.", e)
        } finally {
            executor.shutdown()
            log.debug("멀티스레드 환경에서 메소드들을 [{}] 번 실행했습니다.", count)
        }
    }

    /**
     * 지정된 메소드들을 멀티스레드 방식으로 지정된 횟수만큼 실행시킵니다.
     * @param count  실행할 횟수
     * @param func  실행할 함수
     * @tparam R 함수의 반환 값
     * @return 전체 함수 실행 반환 값
     */
    def runFunc[R](count: Int)(func: => R): mutable.Buffer[R] = {
        log.debug("멀티스레드 환경에서 함수들을 [{}] 번 실행합니다.", count)
        val executor = newExecutor
        var results: mutable.Buffer[R] = mutable.ArrayBuffer[R]()
        val blocks = new mutable.ArrayBuffer[Callable[R]]()
        try {
            for (i <- 0 to count) {
                val block = new Callable[R] {
                    def call(): R = {
                        func
                    }
                }
                blocks += block
            }
            val futures = executor.invokeAll(bufferAsJavaList(blocks)).asInstanceOf[java.util.List[Future[R]]]
            results = futures.map(f => f.get()).toBuffer

        } catch {
            case e: InterruptedException => log.warn("작업 중 interrupted 되었습니다.")
            case e: Exception => log.error("작업 중에 예외가 발생했습니다.", e)
        } finally {
            executor.shutdown()
            log.debug("멀티스레드 환경에서 함수들을 [{}] 번 실행했습니다.", count)
        }
        results
    }
}
