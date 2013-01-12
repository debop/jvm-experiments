package scala.impatient

import kr.kth.commons.slf4j.Logging
import org.junit.Test

/**
 * scala.impatient.OperatorsTest
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 8.
 */
class OperatorsTest extends Logging {

  class Fraction(var n: Int, var d: Int) {
    private val num: Int = if (d == 0) 1 else n * sign(d) / gcd(n, d);
    private val den: Int = if (d == 0) 0 else d * sign(d) / gcd(n, d);

    override def toString = num + "/" + den

    def sign(a: Int) = if (a > 0) 1 else if (a < 0) -1 else 0

    def gcd(a: Int, b: Int): Int = if (b == 0) math.abs(a) else gcd(b, a % b)

    def *(other: Fraction) = new Fraction(n * other.n, d * other.d)
  }

  object Fraction {
    def apply(n: Int, d: Int) = new Fraction(n, d)

    def unapply(input: Fraction) =
      if (input.den == 0) None else Some(input.num, input.den)
  }

  @Test
  def extractors() {
    val Fraction(a, b) = Fraction(3, 4) * Fraction(2, 5)
    log.debug(s"Fraction a=[$a], b=[$b]")
  }


  case class Currency(value: Double, unit: String = "USD")

  @Test
  def extractorMatch() {
    val amt = new Currency(29.95, "EUR")

    amt match {
      case Currency(amount, "USD") => log.debug("$$" + amount)
      case Currency(amount, "EUR") => log.debug("€" + amount)
      case _ => log.debug(amt)
    }
  }

}
