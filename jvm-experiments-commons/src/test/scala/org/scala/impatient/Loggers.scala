package org.scala.impatient

/**
 * 설명을 추가하세요.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 2
 */
trait Logged {
    def log(msg: String)
}

trait LoggedExceptin extends Logged {
    // this: Type =>
    // 이런 형식은 현재 Trait 와 지정된 Type 의 mix-in 을 제공한다.
    this: Exception =>
    def log() {
        log(getMessage())
    }
}
