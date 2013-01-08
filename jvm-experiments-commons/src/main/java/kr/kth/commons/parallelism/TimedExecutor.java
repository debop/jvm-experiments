package kr.kth.commons.parallelism;

import kr.kth.commons.base.Executable;
import kr.kth.commons.base.ExecutableAdapter;
import kr.kth.commons.base.Guard;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 시간 제약이 있는 Executor 를 구현했습니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 16.
 */
@Slf4j
public class TimedExecutor {

    private final long timeout;
    private final long checkMilliSeconds;

    private TimedExecutor(long timeout) {
        this(timeout, 1000);
    }

    private TimedExecutor(long timeout, long checkMilliSeconds) {
        if (log.isDebugEnabled())
            log.debug("TimedExecutor 생성. timeout=[{}], checkMilliSeconds=[{}]", timeout, checkMilliSeconds);

        this.timeout = timeout;
        this.checkMilliSeconds = checkMilliSeconds;
    }

    public void execute(Executable executable) throws TimeoutException {
        Guard.shouldNotBeNull(executable, "executable");

        if (log.isDebugEnabled())
            log.debug("제한된 시간[{}] (sec)에 Executable 인스턴스를 수행합니다.", TimeUnit.SECONDS.toSeconds(timeout));

        final ExecutableAdapter adapter = new ExecutableAdapter(executable);
        final Thread separateThread = new Thread(adapter);
        separateThread.start();

        long runningTime = 0L;
        do {
            if (runningTime > timeout) {
                try {
                    executable.timedOut();
                } catch (Exception ignored) {
                }
                throw new TimeoutException();
            }
            try {
                Thread.sleep(checkMilliSeconds);
                runningTime += checkMilliSeconds;
            } catch (InterruptedException ignored) {
            }
        } while (!adapter.isDone());

        adapter.reThrowAnyErrrors();
    }
}
