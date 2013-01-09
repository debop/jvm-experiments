package kr.kth.commons

import base.ValueObjectBase
import tools.ScalaHash

/**
 * Company 정보
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 26
 */
class Company(var name: String, var email: String, var address: String) extends ValueObjectBase {

    override def hashCode(): Int = {
        ScalaHash.compute(name)
    }

    override def buildStringHelper() =
        super.buildStringHelper()
            .add("name", name)
            .add("email", email)
            .add("address", address)
}
