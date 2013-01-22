package kr.kth.commons.tools

import kr.kth.commons.slf4j.Logging

/**
 * 특정 작업에 대한 여러가지 변형 작업을 제공합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 12.
 */
object Tasks extends Logging {
    /**
     * 지정한 코드 블럭을 성공할 때까지 지정한 횟수만큼 시도합니다.
     * @param attempts 재시도 횟수
     * @param idleMillis 재시도 전에 대기 시간 (milliseconds)
     * @param block 실행할 코드 블럭
     */
    def retryAction(attempts: Int, idleMillis: Long = 0L)(block: => Unit) {
        assert(attempts > 0)
        try {
            block
        } catch {
            case x: Throwable => if (attempts == 1) throw x
            if (isWarnEnabled)
                log.warn("에외가 발생하여 재시도를 합니다. attempts=[{}]", attempts - 1)
            if (idleMillis > 0L)
                Thread.sleep(idleMillis)
            retryAction(attempts - 1, idleMillis) {block}
        }
    }

    /**
     * 지정한 블럭을 성공할 때까지 지정한 횟수만큼 시도합니다.
     * @param attempts 재시도 횟수
     * @param idleMillis 재시도 전에 대기 시간 (milliseconds)
     * @param block 실행할 함수 코드 블럭
     * @tparam T 함수의 반환 수형
     * @return 함수 실행 결과
     */
    def retryFunction[T](attempts: Int, idleMillis: Long = 0L)(block: => T): T = {
        assert(attempts > 0)
        try {
            block
        } catch {
            case x: Throwable => if (attempts == 1) throw x
            if (isWarnEnabled)
                log.warn("에외가 발생하여 재시도를 합니다. attempts=[{}]", attempts - 1)
            if (idleMillis > 0L)
                Thread.sleep(idleMillis)
            retryFunction[T](attempts - 1, idleMillis) {block}
        }
    }
}
