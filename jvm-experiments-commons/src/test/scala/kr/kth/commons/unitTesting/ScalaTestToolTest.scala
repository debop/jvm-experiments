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
    private final val UpperBound: Int = 100000

    @Test
    def runUnitAsParallelTest() {
        def runnable = {
            (LowerBound to UpperBound).foreach {
                i => Hero.findRoot(i)
            }
            log.debug("Unit: FindRoot({}) returns [{}]", UpperBound, Hero.findRoot(UpperBound))
            Hero.findRoot(UpperBound)
        }

        val stopwatch: AutoStopwatch = new AutoStopwatch()
        ParallelTestTool.runUnitAsParallel(100) {runnable; runnable}
        stopwatch.close()
    }

    @Test
    def runUnitAsParallelWithIndexTest() {
        def runnable(x: Int) = {
            (x to UpperBound).foreach {
                i => Hero.findRoot(i)
            }
            log.debug("Unit: FindRoot({}) returns [{}]", UpperBound, Hero.findRoot(UpperBound))
            Hero.findRoot(UpperBound)
        }

        val stopwatch: AutoStopwatch = new AutoStopwatch()
        ParallelTestTool.runUnitAsParallelWithIndex(100) {x => {runnable(x); runnable(x)}}
        stopwatch.close()
    }

    @Test
    def runFuncAsParallelTest() {
        def callable = {
            for (i: Int <- LowerBound to UpperBound) {
                Hero.findRoot(i)
            }
            log.debug("Function: FindRoot({}) returns [{}]", UpperBound, Hero.findRoot(UpperBound))
            val root = Hero.findRoot(UpperBound)
            //println( root )
            root
        }

        val stopwatch: AutoStopwatch = new AutoStopwatch()
        val results = ParallelTestTool.runFuncAsParallel[Double](100) {(callable + callable) / 4.0}
        stopwatch.close()
    }

    @Test
    def runFuncAsParallelWithIndexTest() {
        def callable(x: Int) = {
            for (i: Int <- x to UpperBound) {
                Hero.findRoot(i)
            }
            log.debug("Function: FindRoot({}) returns [{}]", UpperBound, Hero.findRoot(UpperBound))
            val root = Hero.findRoot(UpperBound)
            //println( root )
            root
        }

        val stopwatch: AutoStopwatch = new AutoStopwatch()
        val results = ParallelTestTool.runFuncAsParallelWithIndex(100) {
            x: Int => {
                (callable(x) + callable(x)) / 4.0
            }
        }
        stopwatch.close()
    }

    object Hero {
        private final val Tolerance: Double = 1.0e-10

        def findRoot(number: Double): Double = {
            var guess: Double = 1.0
            var error: Double = Math.abs(guess * guess - number)
            while (error > Tolerance) {
                guess = (number / guess + guess) / 2.0
                error = Math.abs(guess * guess - number)
            }
            guess
        }
    }

}
