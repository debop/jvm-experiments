package kr.kth.timeperiod

import kr.kth.commons.slf4j.Logging
import org.junit.{After, Before}

/**
 * kr.kth.timeperiod.AbstractTest
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 23
 */
abstract class AbstractTest extends Logging {

    @Before
    def before() {
        onBefore()
    }

    @After
    def after() {
        onAfter()
    }


    def onBefore() {}

    def onAfter() {}
}
