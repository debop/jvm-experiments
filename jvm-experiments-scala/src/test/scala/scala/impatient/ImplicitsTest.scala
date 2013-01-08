package scala.impatient

import scala.math._
import org.junit.{Ignore, Assert, Test}
import java.io.File
import io.Source
import java.nio.file.Paths
import kr.kth.commons.slf4j.Logging

/**
 * scala.impatient.ImplicitsTest
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 6.
 */
class ImplicitsTest extends Logging {

  class Fraction(n: Int, d: Int) {
    val num: Int = if (d == 0) 1 else n * sign(d) / gcd(n, d)
    val den: Int = if (d == 0) 0 else d * sign(d) / gcd(n, d)

    override def toString = num + "/" + den

    def sign(a: Int) = if (a > 0) 1 else if (a < 0) -1 else 0

    def gcd(a: Int, b: Int): Int = if (b == 0) abs(a) else gcd(b, a % b)

    def *(other: Fraction) = new Fraction(num * other.num, den * other.den)

    def /(other: Fraction) = new Fraction(num * other.den, den * other.num)
  }

  object Fraction {
    def apply(n: Int, d: Int) = new Fraction(n, d)
  }

  @Test
  def fractionTest() {

    implicit def int2Fraction(n: Int) = Fraction(n, 1)
    implicit def fraction2Double(f: Fraction) = f.num * 1.0 / f.den

    val result = Fraction(4, 5) * 3

    Assert.assertEquals(12, result.num)
    Assert.assertEquals(5, result.den)

    val result2 = Fraction(5, 4) / 5

    Assert.assertEquals(1, result2.num)
    Assert.assertEquals(4, result2.den)
  }

  class RichFile(val from: File) {
    def read = Source.fromFile(from.getPath).mkString
  }

  @Test
  @Ignore("테스트 실행 방법에 따라 위치가 달라져서 수동으로 테스트하세요.")
  def FileToRichFileTest() {
    implicit def file2RichFile(from: File) = new RichFile(from)
    println(Paths.get(".").toAbsolutePath)
    val contents = new File("./jvm-experiments-scala/src/test/scala/scala/impatient/ImplicitsTest.scala").read
    log.debug(contents)
  }
}
