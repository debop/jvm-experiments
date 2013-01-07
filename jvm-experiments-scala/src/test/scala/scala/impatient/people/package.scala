package scala.impatient

/**
 * scala.impatient.people.package
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 7.
 */
package object people {
	val defaultName = "Sunghyouk Bae"
}

package people {

import org.junit.Test
import kr.kth.commons.slf4j.Logging

class Person {
	var name = defaultName
	override def toString = s"A person with name $name"
}


class PackageObjectTest extends Logging {

	@Test
	def testPackageObject() {
		val john = new scala.impatient.people.Person
		log.debug(john)
	}
}

}


