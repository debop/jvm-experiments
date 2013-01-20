package scala.impatient

import concurrent.ExecutionContext.Implicits.global
import concurrent._
import kr.kth.commons.slf4j.Logging
import org.junit.Test
import scala.util.Failure
import scala.util.Success


/**
 * scala.impatient.FutureAndPromisesTest
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 6.
 */
class FutureAndPromisesTest extends Logging {

    @Test
    def futureTest() {
        val f: Future[List[String]] =
            future {
                log.debug("start Future...")
                List("first", "second", "third")
            }
        f onComplete {
            x => log.debug("onComplete ... return [{}]", x.get)
        }

        while (!f.isCompleted)
            Thread.sleep(10)
        log.debug("futureTest is completed")
    }

    @Test
    def futureAndFor() {
        val f = future {
            2 / 0
        }

        f onComplete {
            case Success(x) => log.debug("Success")
            case Failure(e) => log.debug("예외정보", e)
        }
        //        for (exc <- f.failed)
        //            log.debug("예외 정보:", exc)

        val f2 = future {
            4 / 2
        }
        f2 onComplete {
            case x => log.debug("Result value: [{}]", x.get)
        }

        while (!(f.isCompleted && f2.isCompleted))
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

        while (!p.isCompleted)
            Thread.sleep(10)

        log.debug("futureAndPromise test is done.")
    }

    @Test
    def combiningTryAndFutures() {

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

        while (!(avgAge.isCompleted && avgAge.future.isCompleted))
            Thread.sleep(10)

        log.debug("combiningTryAndFutures test is done.")
    }
}


