package scala.impatient

import org.junit.Test

/**
 * scala.impatient.TraitTest
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 8.
 */
class TraitTest {

    trait Logged {
        def log(msg: String) {}
    }

    trait ConsoleLogger extends Logged {
        override def log(msg: String) {
            println(msg)
        }
    }

    trait LoggedException extends Logged {
        this: Exception =>
        // or this: { def getMessage(): String } =>
        def log() {
            log(getMessage())
        }
    }

    class Account {
        protected var balance = 0.0
    }

    class SavingsAccount extends Account {
        def withdraw(amount: Double) {
            if (amount > balance) throw new IllegalStateException("Insufficient funds") with LoggedException with ConsoleLogger
            else balance -= amount
        }
    }

    @Test
    def selfTypeTrait() {
        try {
            val acct = new SavingsAccount
            acct.withdraw(100)
        } catch {
            case e: LoggedException => e.log()
        }
    }
}
