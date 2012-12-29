package kr.kth.commons.tools

/**
 * kr.kth.commons.tools.HashTools
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 29.
 */
object HashTools {

	val NullValue: Int = 0
	val OneValue = 1
	val Factor = 31

	private def computeInternal(x: Any): Int =
		if (x != None) x.hashCode() else NullValue

	/**
	 * 지정된 객체들의 Hash Code를 조합한 Hash Code를 생성합니다.
	 */
	def compute(objs: Any*): Int = {
		var hash = NullValue
		for (x <- objs) {
			hash = hash * Factor + computeInternal(x)
		}
		hash
	}
}
