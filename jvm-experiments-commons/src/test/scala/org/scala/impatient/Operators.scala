package org.scala.impatient

/**
 * 설명을 추가하세요.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 2
 */
object Operators {
	val 루트: (Double) => Double = scala.math.sqrt _

	val result = Fraction(3, 4) * Fraction(5, 2)

	val Number(n) = "123455"
}

class Fraction(var n: Int, var d: Int) {

	def *(other: Fraction) = new Fraction(n * other.n, d * other.d)
}

object Fraction {
	def apply(n: Int, d: Int) = new Fraction(n, d)

	def unapply(input: Fraction) = if (input.d == 0) None else Some((input.n, input.d))

}

object Number {

	def apply(n: Int): Fraction = Fraction(0, 1)

	def unapply(input: String): Option[Int] = {
		try {
			Some(Integer.parseInt(input.trim))
		} catch {
			case ex: NumberFormatException => None
		}
	}

	def unapplySeq(input: String): Option[Seq[String]] = {
		if (input.trim == "") None else Some(input.trim.split("\\s+"))
	}
}
