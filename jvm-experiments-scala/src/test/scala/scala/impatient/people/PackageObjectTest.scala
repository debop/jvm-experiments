package scala.impatient.people

import kr.kth.commons.slf4j.Logging
import org.junit.Test

/**
 * scala.impatient.people.PackageObjectTest
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 8.
 */
class PackageObjectTest extends Logging {

    @Test
    def testPackageObject() {
        val john = new scala.impatient.people.Person
        log.debug(john)
    }

    @Test
    def methodScope() {
        val john = new scala.impatient.people.Person
        log.debug(john.description)
    }
}

class Person {
    var name = defaultName

    // 이 메소드는 scala.impatent namespace 에서 호출 가능합니다.
    private[impatient] def description = s"A person with name $name"

    override def toString = s"A person with name $name"
}