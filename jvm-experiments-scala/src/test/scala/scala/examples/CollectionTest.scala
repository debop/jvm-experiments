package scala.examples

import kr.kth.commons.slf4j.Logging
import org.junit.Test

/**
 * scala.examples.CollectionTest
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 13.
 */
class CollectionTest extends Logging {

    def yieldNumber(fromInclude: Int, toExclude: Int): Iterable[Int] = {
        for {
            x <- (fromInclude until toExclude)
        }
        yield {
            log.debug("yield [{}]", x)
            x
        }
    }

    def fromNumber(from: Int): Stream[Int] = from #:: fromNumber(from + 1)

    def viewNumber(fromInclude: Int, toExclude: Int) = {
        (fromInclude until toExclude).view
    }

    @Test
    def yieldTest() {
        val x = yieldNumber(0, 100).iterator
        while (x.hasNext) {
            log.debug("next [{}]", x.next())
        }
    }

    @Test
    def streamTest() {
        val stream = fromNumber(0).take(100)
        for (x <- stream) {
            log.debug("stream [{}]", x)
        }
    }

    @Test
    def viewNumber() {
        val view = viewNumber(0, 100)
        view.map {x => log.debug("view [{}]", x)}.force
    }

}
