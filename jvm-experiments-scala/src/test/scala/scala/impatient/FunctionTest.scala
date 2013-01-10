package scala.impatient

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

        def runInThread(block: () => Unit) {
            new Thread {
                override def run() {
                    block()
                }
            }.start()
        }

        runInThread {
            () => println("Hi"); Thread.sleep(100); println("Bye")
        }

        def runInThread2(block: => Unit) {
            new Thread() {
                override def run() {
                    block
                }
            }.start()
        }

        val unit: Unit = {
            println("Hi")
            Thread.sleep(100)
            println("Bye")
        }

        runInThread2 {
            unit
        }

        Thread.sleep(500)
    }
}
