package kr.kth.timeperiod.clock;

import kr.kth.timeperiod.IClock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * kr.kth.timeperiod.clock.ClockProxy
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 19.
 */
public class ClockProxy {

    private static AtomicReference<IClock> clockReference = new AtomicReference<>();

    public static IClock getClock() {
        clockReference.compareAndSet(null, new SystemClock());
        return clockReference.get();
    }

    public static void setClock(IClock clock) {
        clockReference.set(clock);
    }
}
