package kr.kth.commons.slf4j

import org.junit.Test

/**
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 8
 */
class LoggingTest extends Logging {

    @Test
    def printString() {
        log.trace("Trace Simple String")
        log.debug("Debug Simple String")
        log.info("Info Simple String")
        log.warn("Warn Simple String")
        log.error("Error Simple String")
    }

    @Test
    def printAnyVal() {
        log.debug("print 1")
        log.debug(1)
    }

    case class Card(name: String, year: Int)

    @Test
    def printAnyRef() {
        log.debug("print Card object (name=Credit, year=2006)")
        log.debug(new Card("Credit", 2006))
    }

    @Test
    def printFormatString() {
        log.trace("Format String. Card=[{}]", new Card("Format", 2013))
        log.debug("Format String. Card=[{}]", new Card("Format", 2013))
        log.info("Format String. Card=[{}]", new Card("Format", 2013))
        log.warn("Format String. Card=[{}]", new Card("Format", 2013))
        log.error("Format String. Card=[{}]", new Card("Format", 2013))
    }

    @Test
    def printException() {
        log.debug("Print exception", new RuntimeException("for test"))
    }

}
