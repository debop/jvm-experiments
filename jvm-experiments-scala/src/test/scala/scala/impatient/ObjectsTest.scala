package scala.impatient

import org.junit.{Assert, Test}
import kr.kth.commons.slf4j.Logging

/**
 * scala.impatient.ObjectsTest
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 7.
 */
class ObjectsTest extends Logging {

  class Account {
    val id = Account.newUniqueNumber()

    private var balance = 0.0

    def getBalance = balance

    def deposit(amount: Double) {
      balance += amount
    }

    override def toString = s"Account $id with balance $balance"
  }

  object Account {
    private var lastNumber = 0

    private def newUniqueNumber() = {
      lastNumber += 1
      log.debug("lastNumber = [{}]", lastNumber)
      lastNumber
    }
  }

  @Test
  def companionObjects() {
    val a1 = new Account
    a1.deposit(1000)
    val a2 = new Account
    a2.deposit(2000)

    Assert.assertEquals(1000, a1.getBalance, 1e-8)
    Assert.assertEquals(2000, a2.getBalance, 1e-8)

    log.debug("a1=[{}], a2=[{}]", a1, a2)
  }

  class Account4(val id: Int, initialBalance: Double) {
    private var balance = initialBalance

    def getBalance = balance

    def deposit(amount: Double) {
      balance += amount
    }

    override def toString = s"Account $id with balance $balance"
  }

  object Account4 {
    private var lastNumber: Int = 0

    private def newUniqueNumber() = {
      lastNumber += 1;
      lastNumber
    }

    def apply(initialBalance: Double) = new Account4(newUniqueNumber(), initialBalance)
  }

  @Test
  def objectApply() {
    val a1 = Account4(1000)
    Assert.assertEquals(1000.0, a1.getBalance, 1e-8)
  }


  object TrafficLightColor extends Enumeration {
    type TrafficLightColor = Value
    val Red, Yellow, Green = Value
  }

  @Test
  def enumerationTest() {
    for (c <- TrafficLightColor.values)
      log.debug("[{}]: [{}]", c.id, c)

    val color = TrafficLightColor(0)
    val color2 = TrafficLightColor.withName("Red")

    Assert.assertEquals(TrafficLightColor.Red.id, color2.id)
  }
}
