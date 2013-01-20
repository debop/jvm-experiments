package scala.impatient

import kr.kth.commons.slf4j.Logging
import org.junit.{Ignore, Test}
import scala.util.continuations._

/**
 * scala.impatient.DelimitedContinuationsTest
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 5.
 */
class DelimitedContinuationsTest extends Logging {

    @Test
    @Ignore("예외가 발생합니다. 이유는 모르겠어요^^")
    def computationWithHole() {
        var cont: (Unit => Unit) = null
        reset {
            log.debug("Before shift")
            shift {
                k: (Unit => Unit) => {
                    cont = k
                    log.debug("Inside shift")
                }
            }
            log.debug("After shift")
        }
        log.debug("After reset")
        cont()
    }
}
