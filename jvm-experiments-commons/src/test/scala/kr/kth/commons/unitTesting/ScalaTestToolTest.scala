package kr.kth.commons.unitTesting

import kr.kth.commons.base.AutoStopwatch
import kr.kth.commons.slf4j.Logging
import org.junit.Test

/**
 * Scala 코드를 멀티쓰레드에서 테스트 합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 9
 */
class ScalaTestToolTest extends Logging {

    private final val LowerBound: Int = 0
    private final val UpperBound: Int = 99999

    @Test
    def runUnitsWithControlAbstraction() {
        def runnable = {
            (LowerBound to UpperBound).foreach {
                i => Hero.findRoot(i)
            }
            log.debug("Unit: FindRoot({}) returns [{}]", UpperBound, Hero.findRoot(UpperBound))
            Hero.findRoot(UpperBound)
        }

        val stopwatch: AutoStopwatch = new AutoStopwatch()
        ScalaTestTool.runUnit(100) {
            runnable
            runnable
        }
        stopwatch.close()
    }

    @Test def runFuncTest {
        def callable = {
            for (i: Int <- LowerBound to UpperBound) {
                Hero.findRoot(i)
            }
            log.debug("Function: FindRoot({}) returns [{}]", UpperBound, Hero.findRoot(UpperBound))
            Hero.findRoot(UpperBound)
        }

        val stopwatch: AutoStopwatch = new AutoStopwatch()
        val results = ScalaTestTool.runFunc[Double](100) {
            val a1 = callable
            val a2 = callable
            (a1 + a2) / 2.0
        }
        stopwatch.close()
    }

    object Hero {
        def findRoot(number: Double): Double = {
            var guess: Double = 1.0
            var error: Double = Math.abs(guess * guess - number)
            while (error > Tolerance) {
                guess = (number / guess + guess) / 2.0
                error = Math.abs(guess * guess - number)
            }
            guess
        }

        private final val Tolerance: Double = 1.0e-10
    }

}
