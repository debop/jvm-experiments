package kr.kth.timeperiod

import kr.kth.commons.slf4j.Logging
import org.junit.{Assert, Test}
import org.joda.time.DateTimeConstants

/**
 * kr.kth.timeperiod.ScalaDayOfWeekTest
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 13.
 */
class ScalaDayOfWeekTest extends Logging {

  @Test
  def dayOfWeekEquals() {
    val sunday = ScalaDayOfWeek.Sunday
    val parsed = ScalaDayOfWeek.withName("Sunday")

    Assert.assertEquals(sunday, parsed)
    Assert.assertEquals("Sunday", sunday.toString)

    Assert.assertNotEquals(ScalaDayOfWeek.Friday, ScalaDayOfWeek.Monday)

    Assert.assertEquals(ScalaDayOfWeek.Monday, ScalaDayOfWeek(DateTimeConstants.MONDAY))
    log.debug(ScalaDayOfWeek.values)
  }
}
