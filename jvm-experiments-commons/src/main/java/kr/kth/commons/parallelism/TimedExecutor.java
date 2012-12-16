package kr.kth.commons.parallelism;

import kr.kth.commons.base.Executable;
import kr.kth.commons.base.ExecutableAdapter;
import kr.kth.commons.base.Guard;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeoutException;

/**
 * kr.kth.commons.parallelism.TimedExecutor
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 16.
 */
@Slf4j
public class TimedExecutor {

	private final long timeout;
	private final int checkMilliSeconds;

	private TimedExecutor(long timeout) {
		this(timeout, 1000);
	}

	private TimedExecutor(long timeout, int checkMilliSeconds) {
		if (log.isDebugEnabled())
			log.debug("TimedExecutor 생성. timeout=[{}], checkMilliSeconds=[{}]", timeout, checkMilliSeconds);

		this.timeout = timeout;
		this.checkMilliSeconds = checkMilliSeconds;
	}

	public void execute(Executable executable) throws TimeoutException {
		Guard.shouldNotBeNull(executable, "executable");

		if (log.isDebugEnabled())
			log.debug("Executable 인스턴스를 수행합니다.");

		final ExecutableAdapter adapter = new ExecutableAdapter(executable);
		final Thread separateThread = new Thread(adapter);
		separateThread.start();

		int runningTime = 0;
		do {
			if (runningTime > timeout) {
				try {
					executable.timedOut();
				} catch (Exception ignored) {}
				throw new TimeoutException();
			}
			try {
				Thread.sleep(checkMilliSeconds);
				runningTime += checkMilliSeconds;
			} catch (InterruptedException ignored) {}
		} while (!adapter.isDone());

		adapter.reThrowAnyErrrors();
	}
}
