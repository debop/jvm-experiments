package kr.kth.commons.collection

import collection.mutable

/**
 * kr.kth.commons.collection.Collections
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 27.
 */
object Collections {

	implicit def convertSet[T](set: java.util.Set[T]) = Set(set)

	implicit def convertList[T](list: java.util.List[T]) = mutable.Buffer(set)

}
