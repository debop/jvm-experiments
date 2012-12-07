package kr.kth.commons.base;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * kr.kth.commons.base.AutoCloseableAction
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 30.
 */
@Slf4j
public class AutoCloseableAction implements AutoCloseable {

	@Getter private final Action0 action;
	@Getter protected boolean closed;

	public AutoCloseableAction(Action0 action) {
		Guard.shouldNotBeNull(action, "action");
		this.action = action;
		this.closed = false;
	}

	@Override
	public void close() {
		if (closed)
			return;

		try {
			if (log.isDebugEnabled())
				log.debug("AutoCloseable의 close 작업을 수행합니다...");

			if (action != null)
				action.perform();

			if (log.isDebugEnabled())
				log.debug("AutoCloseable의 close 작업을 완료했습니다.");

		} catch (Exception e) {
			if (log.isWarnEnabled())
				log.warn("AutoClosesable의 close 작업에 예외가 발생했습니다. 예외는 무시됩니다.", e);
		} finally {
			closed = true;
		}
	}
}
