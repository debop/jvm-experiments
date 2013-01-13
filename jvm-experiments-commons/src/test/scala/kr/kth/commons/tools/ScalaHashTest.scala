package kr.kth.commons.tools

import kr.kth.commons.slf4j.Logging
import org.junit.{Assert, Test}
import kr.kth.commons.YearWeek

class ScalaHashTest extends Logging {

  @Test
  def hashCodeTest() {
    val a = ScalaHash.compute(1, 2)
    val b = ScalaHash.compute(2, 1)

    Assert.assertNotEquals(a, b)
    Assert.assertEquals(a, ScalaHash.compute(1, 2))


    val withNull1 = ScalaHash.compute(new YearWeek(2013, 1), null)
    val withNull2 = ScalaHash.compute(null, new YearWeek(2013, 1))
    val withNull3 = ScalaHash.compute(new YearWeek(2013, 1), null)

    Assert.assertNotEquals(withNull1, withNull2)
    Assert.assertNotEquals(withNull2, withNull3)
    Assert.assertEquals(withNull1, withNull3)
  }
}
