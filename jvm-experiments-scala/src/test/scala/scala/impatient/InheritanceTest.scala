package scala.impatient

import kr.kth.commons.slf4j.Logging
import org.junit.Test

/**
 * scala.impatient.InheritanceTest
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 8.
 */
class InheritanceTest extends Logging {

    case class Person(var name: String)

    def meet(p: Person {def greeting: String}) {
        log.debug(p.greeting)
    }

    @Test
    def anonymousSubclass() {

        val alien = new Person("Fred") {
            def greeting = s"Greeting, Earthling! My name is $name"
        }

        log.debug(alien.greeting)
        log.debug(alien)

        meet(alien)
    }
}
