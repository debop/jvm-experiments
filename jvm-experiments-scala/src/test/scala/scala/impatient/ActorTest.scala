package scala.impatient

import actors.Actor
import kr.kth.commons.slf4j.Logging
import org.junit.Test


/**
 * scala.impatient.ActorTest
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 5.
 */
class ActorTest extends Logging {

    class HiActor extends Actor {
        def act() {
            while (true) {
                receive {
                    // 작업 후, sender에게 응답한다.
                    case "Hi" => log.debug("Hello"); reply("Hi") // sender ! "Hi"
                    case _ => sender ! "What?"
                }
            }
        }
    }

    @Test
    def actorSimpleTest() {
        val actor1 = new HiActor
        actor1.start()

        val reply = actor1 !? "Hi" // send and wait
        reply match {
            case "Hi" => log.debug("Reply Hi")
            case "What?" => log.debug("What?")
        }
    }

    @Test
    def futureActorReply() {
        val actor1 = new HiActor
        actor1.start()

        val replyFuture = actor1 !! "Hi" // send and future

        val reply = replyFuture()
        reply match {
            case "Hi" => log.debug("Reply Hi")
            case "What?" => log.debug("What?")
        }
    }

    class HiActor2 extends Actor {
        def act() {
            loop {
                react {
                    case "Hi" => log.debug("Hi"); sender ! "How are you?"
                }
            }
        }

        override def exceptionHandler = {
            case e: RuntimeException => log.error("런타임에러", e)
            case e: Exception => log.error("일반 예외", e)
            case _ => log.error("unknown error")
        }
    }

    @Test
    def actorReact() {
        val actor = new HiActor2
        actor.start()

        val replyFuture = actor !! "Hi" // send and future
        val reply = replyFuture()

    }

    class Pinger extends Actor {
        def act() {
            loop {
                react {
                    case _ => log.debug("Pinging!"); sender ! "Ping!"
                }
            }
        }
    }

    class Ponger extends Actor {
        def act() {
            loop {
                react {
                    case _ => log.debug("Ponging!"); sender ! "Pong!"
                }
            }
        }
    }

    @Test
    def reactTest() {
        val pinger = new Pinger().start()
        val ponger = new Ponger().start()

        pinger.send("Ping!", ponger)

        Thread.sleep(1000)
    }

    @Test
    def actorObject() {
        val actor = Actor.actor {log.debug("Hi")}

        actor ! "Hi"
        Thread.sleep(10)
    }
}


