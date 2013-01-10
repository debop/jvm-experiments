package scala.impatient

import kr.kth.commons.slf4j.Logging
import org.junit.{Assert, Test}

/**
 * scala.impatient.MatchingTest
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 6.
 */
class MatchingTest extends Logging {


    object Email {
        def unapply(str: String): Option[(String, String)] = {
            val elems = str split "@"
            if (elems.length == 2)
                Some(elems(0), elems(1))
            else
                None
        }
    }

    @Test
    def extractorTest() {
        val email = "abc@gmail.com"
        val msg = email match {
            case Email(name, host) => name + " at " + host
            case _ => "no mail account"
        }

        log.debug(email)
        Assert.assertTrue(msg.contains("at"))
    }

}
