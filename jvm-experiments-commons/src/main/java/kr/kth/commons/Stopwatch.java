package kr.kth.commons;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 실행 시간을 측정하는 Stopwatch 입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 12
 */
@Slf4j
public class Stopwatch {

    private static final double NANO_TO_MILLISECOND = 1000_000.0;

    private long startTime;
    private long endTime;
    @Getter
    private long elapsedTime;

    private boolean runGC = false;

    public Stopwatch() {
        this(false);
    }

    public Stopwatch(boolean runGC) {
        this.runGC = runGC;
    }

    private void cleanUp() {
        System.gc();
    }

    public void reset() {
        startTime = 0;
        endTime = 0;
        elapsedTime = 0;
    }

    public void start() {
        if (startTime != 0)
            reset();

        if (this.runGC)
            cleanUp();

        startTime = System.nanoTime();
    }

    public double end() throws IllegalStateException {
        if (startTime == 0)
            throw new IllegalStateException("call start() method at first.");

        endTime = System.nanoTime();
        elapsedTime = endTime - startTime;

        if (Stopwatch.log.isInfoEnabled())
            Stopwatch.log.info("Elapsed timePart = [{}] msecs", nanoToMillis(elapsedTime));

        if (this.runGC)
            cleanUp();

        return elapsedTime / 1000000.0;
    }

    @Override
    public String toString() {
        return "Elapsed timePart = " + nanoToMillis(elapsedTime) + " msecs";
    }

    private static double nanoToMillis(double nano) {
        return nano / NANO_TO_MILLISECOND;
    }
}