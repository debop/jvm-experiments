package kr.kth.commons.parallelism;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ForkJoinPool;

/**
 * pudding.pudding.commons.parallelism.forkjoin.FibonacciTest
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 28.
 */
@Slf4j
public class FibonacciTest {

    private static final int N = 32;

    @Test
    public void sillyWorkerTest() {

        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();

        FibonacciProblem bigProblem = new FibonacciProblem(N);
        long result = bigProblem.solve();

        stopwatch.stop();

        if (FibonacciTest.log.isDebugEnabled()) {
            FibonacciTest.log.debug("Computing Fibonacci number=[{}]", N);
            FibonacciTest.log.debug("Computed Result=[{}]", result);
            FibonacciTest.log.debug(stopwatch.toString());
        }
    }

    @Test
    public void forkJoinWorkerTest() {
        int processors = Runtime.getRuntime().availableProcessors();

        if (FibonacciTest.log.isDebugEnabled())
            FibonacciTest.log.debug("process count=[{}]", processors);

        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();

        FibonacciProblem bigProblem = new FibonacciProblem(N);
        FibonacciTask task = new FibonacciTask(bigProblem);
        ForkJoinPool pool = new ForkJoinPool(processors);

        pool.invoke(task);

        long result = task.getResult();

        stopwatch.stop();
        if (FibonacciTest.log.isDebugEnabled()) {
            FibonacciTest.log.debug("Computed result=[{}]", result);
            FibonacciTest.log.debug(stopwatch.toString());
        }
    }
}
