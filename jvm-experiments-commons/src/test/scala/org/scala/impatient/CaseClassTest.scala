package org.scala.impatient

import org.junit.{Assert, Test}

/**
 * org.scala.impatient.CaseClassTest
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 4.
 */
class CaseClassTest {


    abstract class Amount

    case class Dollar(value: Double) extends Amount

    case class Currency(value: Double, unit: String) extends Amount

    case object Nothing extends Amount

    @Test
    def matchCaseClassTest() {

        val dollar: Amount = Dollar(100)

        val result =
            dollar match {
                case Dollar(v) => "$" + v.toInt
                case Currency(_, u) => "이런 .. " + u
                case Nothing => ""
                case _ => "Unknown"
            }

        Assert.assertEquals("$100", result)
    }

    @Test
    def caseCopyTest() {
        val amt = Currency(29.95, "EUR")

        Assert.assertEquals(amt, amt.copy())
        Assert.assertEquals(Currency(10.0, "EUR"), amt.copy(10.0))
        Assert.assertEquals(Currency(10.0, "USD"), amt.copy(10.0, "USD"))
    }

    abstract class Item

    case class Article(description: String, price: Double) extends Item

    case class Bundle(description: String, discount: Double, items: Item*) extends Item

    @Test
    def matchingNestedStructures() {

    }
}
