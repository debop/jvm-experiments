package kr.nsoft.commons.parallelism;

import lombok.extern.slf4j.Slf4j;

/**
 * pudding.pudding.commons.parallelism.forkjoin.FibonacciProblem
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 28.
 */
@Slf4j
public class FibonacciProblem {

    public int n;

    public FibonacciProblem(int n) {
        this.n = n;
    }

    public long solve() {
        return fibonacci(n);
    }

    private long fibonacci(int n) {
        if (log.isTraceEnabled())
            log.trace("Fibonacci calculates... n=[{}]", n);

        if (n <= 1)
            return n;
        else
            return fibonacci(n - 1) + fibonacci(n - 2);
    }
}
