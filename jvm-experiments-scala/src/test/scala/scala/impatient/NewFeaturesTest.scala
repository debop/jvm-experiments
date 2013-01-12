package scala.impatient

import kr.kth.commons.slf4j.Logging
import org.junit.{Test, Assert}
import util.Try


/**
 * scala.impatient.NewFeaturesTest
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 6.
 */
class NewFeaturesTest extends Logging {

  @Test
  def tryTest() {
    val v = Try(100 / 0) getOrElse 100
    Assert.assertEquals(100, v)
  }
}
