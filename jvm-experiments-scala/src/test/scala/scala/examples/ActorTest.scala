package scala.examples

import kr.kth.commons.slf4j.Logging
import org.junit.Test
import scala.actors._

/**
 * scala.examples.ActorTest
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 6.
 */
class ActorTest extends Logging {

    case object Start

    case object Finish

    class Reader extends Actor with Logging {
        def act() {
            loop {
                react {
                    case (Start, book) => log.debug(s"Beginng to read $book")
                    case (Finish, book) => log.debug(s"Done readoing $book")
                }
            }
        }
    }

    @Test
    def readerTest() {
        val educated = new Reader()
        educated.start()

        educated !(Start, "Seven Language in Seven Weeks")
        educated !(Finish, "Less is More")

        Thread.sleep(100)
    }
}
