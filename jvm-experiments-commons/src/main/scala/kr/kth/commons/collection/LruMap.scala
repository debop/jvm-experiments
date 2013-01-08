package kr.kth.commons.collection

import org.apache.commons.collections.map.LRUMap
import collection.convert.Wrappers.JMapWrapper
import collection.mutable

/**
 * LRU Map 을 표현합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 2
 */
class LruMap[K, V](val maxSize: Int, underlying: java.util.Map[K, V]) extends JMapWrapper[K, V](underlying) {
  def this(maxSize: Int) = this(maxSize, LruMap.makeUnderlying(maxSize))
}

class SynchronizedLruMap[K, V](maxSize: Int, underlying: java.util.Map[K, V])
  extends LruMap(maxSize, java.util.Collections.synchronizedMap(underlying)) with mutable.SynchronizedMap[K, V] {
  def this(maxSize: Int) = this(maxSize, LruMap.makeUnderlying(maxSize))
}

object LruMap {
  def makeUnderlying[K, V](maxSize: Int) = new LRUMap(maxSize).asInstanceOf[java.util.Map[K, V]]
}

