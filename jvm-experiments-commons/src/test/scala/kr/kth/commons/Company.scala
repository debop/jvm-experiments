package kr.kth.commons

import base.ValueObjectBase
import tools.HashTool
import com.google.common.base.Objects

/**
 * Company 정보
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 26
 */
class Company(var name: String, var email: String, var address: String) extends ValueObjectBase {

	override def hashCode(): Int = {
		HashTool.compute(name)
	}

	override def buildStringHelper(): Objects.ToStringHelper = {
		super.buildStringHelper()
			.add("name", name)
			.add("email", email)
			.add("address", address)
	}
}
