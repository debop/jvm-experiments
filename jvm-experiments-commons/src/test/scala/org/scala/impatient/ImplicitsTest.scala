package org.scala.impatient

import kr.kth.commons.slf4j.Logging
import org.junit.{Assert, Test}

/**
 * org.scala.impatient.ImplicitsTest
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 5.
 */
class ImplicitsTest extends Logging {

  case class Delimiters(left: String, right: String)

  def quote(what: String)(implicit delims: Delimiters) = delims.left + what + delims.right

  implicit val quoteDelimiters = Delimiters("<<", ">>")

  @Test
  def implicitParamters() {
    val q = quote("Category")
    Assert.assertEquals("<<Category>>", q)

    log.debug(s"q=[$q]")
  }
}
