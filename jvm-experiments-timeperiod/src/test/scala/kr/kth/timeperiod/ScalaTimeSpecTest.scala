package kr.kth.timeperiod

import kr.kth.commons.slf4j.Logging
import org.junit.{Assert, Test}

/**
 * {@link TimeSpec} 단위 테스트
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 13.
 */
class ScalaTimeSpecTest extends Logging {

  @Test
  def testTimeSpec() {
    Assert.assertEquals(0L, TimeSpec.MinMillis)
    Assert.assertEquals(0L, TimeSpec.ZeroMillis)
  }
}
