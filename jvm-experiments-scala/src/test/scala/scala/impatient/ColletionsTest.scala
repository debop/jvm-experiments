package scala.impatient

import kr.kth.commons.slf4j.Logging
import org.junit.{Assert, Test}

/**
 * 컬렉션 관련 예제 코드
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 10
 */
class ColletionsTest extends Logging {

  def numberFrom(n: Int): Stream[Int] = n #:: numberFrom(n + 1)

  @Test
  def testStreams() {
    val tenOrMore = numberFrom(10)

    Assert.assertEquals(11, tenOrMore.tail.head)
    Assert.assertEquals(19, tenOrMore.take(10).last)
    Assert.assertEquals(20, tenOrMore.drop(10).head)

    val squares = numberFrom(1).map(x => x * x)

    Assert.assertEquals(4, squares.drop(1).head) // 2 * 2
    Assert.assertEquals(Stream(1, 4, 9, 16, 25), squares.take(5).force)
  }

  @Test
  def lazyViewTest() {
    // Lazy View 는 Stream 과는 달리 계산 값을 캐시하지 않습니다.
    // 다만 powers(100) 을 호출할 때, math.power(10, 100) 을 계산합니다.
    // !!! 그렇지만, powers(1) ~ powers(99) 는 계산하지 않습니다^^
    val powers = (0 until 1000).view.map(math.pow(10, _))

    Assert.assertEquals(math.pow(10, 10), powers(10), 1e-8)

    // 0~9 까지는 계산하지 않고, 다음 10개만 계산합니다.
    log.debug(powers.drop(10).take(10).map(x => {log.debug(x); 1 / x }).force)
  }

  @Test
  def parallelCollectionTest() {
    val range = (0 to 1000)
    val x = range.par.sum

    Assert.assertEquals(500500, x)
  }
}
