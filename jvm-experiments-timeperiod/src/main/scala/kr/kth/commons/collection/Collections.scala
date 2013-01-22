package kr.kth.commons.collection

import collection.{JavaConversions, mutable}

/**
 * Java collection과 Scala collection 을 변환합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 27.
 */
object Collections {

    implicit def convertIterator[T](iterator: java.util.Iterator[T]) = JavaConversions.asScalaIterator(iterator)

    implicit def convertList[T](list: java.util.List[T]) = JavaConversions.asScalaBuffer(list)

    implicit def convertSet[T](set: java.util.Set[T]) = JavaConversions.asScalaSet(set)

    implicit def convertMap[K, V](map: java.util.Map[K, V]) = JavaConversions.mapAsScalaMap(map)

    implicit def convertJavaIterable[T](iterable: mutable.Iterable[T]) = JavaConversions.asJavaIterable(iterable)

    implicit def convertJavaList[T](buffer: mutable.Buffer[T]) = JavaConversions.bufferAsJavaList(buffer)

    implicit def convertJavaSet[T](set: mutable.Set[T]) = JavaConversions.setAsJavaSet(set)

    implicit def convertJavaMap[K, V](map: Map[K, V]) = JavaConversions.mapAsJavaMap(map)
}
