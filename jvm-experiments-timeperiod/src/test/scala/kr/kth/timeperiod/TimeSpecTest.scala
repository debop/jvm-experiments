package kr.kth.timeperiod

import kr.kth.commons.slf4j.Logging
import org.junit.{Assert, Test}

/**
 * {@link TimeSpec} 단위 테스트
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 13.
 */
class TimeSpecTest extends Logging {
  @Test
  def testTimeSpec() {
    Assert.assertEquals(0, TimeSpec.ZeroTick)
  }
}
