package kr.kth.commons.base;

import lombok.extern.slf4j.Slf4j;

/**
 * 성능 측정을 자동으로 수행하는 {@link Stopwatch} 입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 12
 */
@Slf4j
public class AutoStopwatch implements AutoCloseable {

    private final Stopwatch stopwatch;

    public AutoStopwatch() {
        stopwatch = new Stopwatch();
        stopwatch.start();
    }

    @Override
    public void close() {
        try {
            stopwatch.end();
        } catch (Exception ignored) {
        }
    }
}
