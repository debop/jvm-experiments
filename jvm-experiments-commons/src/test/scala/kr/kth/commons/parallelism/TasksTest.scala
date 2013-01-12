package kr.kth.commons.parallelism

import kr.kth.commons.slf4j.Logging
import org.junit.{Assert, Test}
import scala.UnsupportedOperationException

/**
 * kr.kth.commons.parallelism.TasksTest
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 12.
 */
class TasksTest extends Logging {

  @Test
  def retryActionShouldBeFail() {
    try {
      Tasks.retryAction(3) { throw new UnsupportedOperationException() }
    } catch {
      case x: UnsupportedOperationException => log.debug("success")
      case _: Throwable => Assert.fail("재시도 시에 예외가 발생하지 않았습니다.")
    }
  }

  @Test
  def retryActionShouldBeSuccess() {
    try {
      Tasks.retryAction(3) { log.debug("run action") }
    } catch {
      case _: Throwable => Assert.fail("재시도 시에 예외가 발생하지 않았습니다.")
    }
  }

  @Test
  def retryFunctionShouldBeFail() {
    try {
      Tasks.retryFunction(3) { throw new UnsupportedOperationException() }
    } catch {
      case x: UnsupportedOperationException => log.debug("success")
      case _: Throwable => Assert.fail("재시도 시에 예외가 발생하지 않았습니다.")
    }
  }

  @Test
  def retryFunctionShouldBeSuccess() {
    try {
      val x = Tasks.retryFunction(3) { 4 }
      Assert.assertEquals(4, x)
    } catch {
      case _: Throwable => Assert.fail("재시도 시에 예외가 발생하지 않았습니다.")
    }
  }
}
