package scala.impatient

import java.util.concurrent.CountDownLatch
import kr.kth.commons.slf4j.Logging
import org.junit.Test

/**
 * scala.impatient.FunctionTest
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 9.
 */
class FunctionTest extends Logging {

    @Test
    def controlAbtractionTest {

        val latch = new CountDownLatch(2)

        def runInThread(block: () => Unit) {
            new Thread {
                override def run() {
                    block()
                }
            }.start()
        }

        runInThread {
            () => {
                log.debug("Hi")
                Thread.sleep(100)
                log.debug("Bye")

                latch.countDown()
            }
        }

        def runInThread2(block: => Unit) {
            new Thread() {
                override def run() {
                    block
                }
            }.start()
        }

        runInThread2 {
            log.debug("Hi2")
            Thread.sleep(100)
            log.debug("Bye2")

            latch.countDown()
        }

        latch.await()
    }
}
