package org.scala.impatient

import org.junit.Test
import concurrent._
import ExecutionContext.Implicits.global
import kr.kth.commons.slf4j.Logging


/**
 * org.scala.impatient.FutureAndPromisesTest
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 6.
 */
class FutureAndPromisesTest extends Logging {

	@Test
	def futureTest() {
		val f: Future[List[String]] =
			future {
				       log.debug("start Future")
				       List("first", "second", "third")
			       }
		f onComplete {
			x => log.debug(s"onComplete: ${x.get}")
		}

		Thread.sleep(10)
	}

	@Test
	def futureAndFor() {
		val f = future {
			               2 / 0
		               }

		for (exc <- f.failed)
			log.debug("예외 정보:", exc)

		val f2 = future {
			                4 / 2
		                }
		f2 onComplete {
			case x => log.debug("Result value: [{}]", x.get)
		}

		Thread.sleep(10)
	}


	@Test
	def futureAndPromiseTest() {
		val f = future {
			               1
		               }
		val p = promise[Int]()

		p completeWith f

		p.future onComplete {
			x => log.debug(s"Result value: ${x.get}")
		}
		Thread.sleep(10)
	}

	@Test
	def combiningTryAdnFutures() {

		case class Friend(name: String, age: Int)

		val avgAge = Promise[Int]()
		val fut = future {
			                 List(Friend("Zoe", 25), Friend("Jean", 27), Friend("Paul", 30))
		                 }

		fut onComplete {
			tr => {
				val result = tr map {
					friends => friends.map(_.age).reduce(_ + _) / friends.length
				}
				avgAge.complete(result)
			}
		}
		avgAge.future onComplete {
			x => log.debug(s"result=${x.get}")
		}

		Thread.sleep(100)
	}
}


