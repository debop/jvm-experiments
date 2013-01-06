package scala.examples

import scala.actors._
import org.junit.Test
import kr.kth.commons.slf4j.Logging

/**
 * scala.examples.ActorTest
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 6.
 */
class ActorTest extends Logging {

	case object Start

	case object Finish

	class Reader() extends Actor {
		def act() {
			loop {
				     react {
					           case (Start, book) => println(s"Beginng to read $book")
					           case (Finish, book) => println(s"Done readoing $book")
				           }
			     }
		}
	}

	@Test
	def readerTest() {
		val educated = new Reader().start()

		educated !(Start, "Seven Language in Seven Weeks")
		educated !(Finish, "Less is More")

		Thread.sleep(10)
	}
}
