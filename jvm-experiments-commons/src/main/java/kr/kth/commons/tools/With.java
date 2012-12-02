package kr.kth.commons.tools;

import kr.kth.commons.Action0;
import kr.kth.commons.Action1;
import kr.kth.commons.Func0;
import kr.kth.commons.Guard;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * kr.kth.commons.tools.With
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 30.
 */
@Slf4j
public class With {

	public static void tryAction(Action0 action) {
		tryAction(action, null, null);
	}

	public static void tryAction(Action0 action, Action1<Exception> exceptionAction) {
		tryAction(action, exceptionAction, null);
	}

	public static void tryAction(Action0 action, Action0 finallyAction) {
		tryAction(action, null, finallyAction);
	}

	public static void tryAction(Action0 action, Action1<Exception> exceptionAction, Action0 finallyAction) {
		Guard.shouldNotBeNull(action, "action");
		try {
			action.perform();
		} catch (Exception e) {
			if (exceptionAction != null) {
				exceptionAction.perform(e);
			} else {
				if (log.isWarnEnabled())
					log.warn("예외가 발생했지만, 무시합니다.", e);
			}
		} finally {
			if (finallyAction != null)
				finallyAction.perform();
		}
	}

	public static <T> void tryAction(Action1<T> action, final T arg) {
		tryAction(action, arg, null, null);
	}

	public static <T> void tryAction(Action1<T> action, final T arg, Action1<Exception> exceptionAction) {
		tryAction(action, arg, exceptionAction, null);
	}

	public static <T> void tryAction(Action1<T> action, final T arg, Action0 finallyAction) {
		tryAction(action, arg, null, finallyAction);
	}

	public static <T> void tryAction(Action1<T> action, final T arg, Action1<Exception> exceptionAction, Action0 finallyAction) {
		Guard.shouldNotBeNull(action, "action");
		try {
			action.perform(arg);
		} catch (Exception e) {
			if (exceptionAction != null) {
				exceptionAction.perform(e);
			} else {
				if (log.isWarnEnabled())
					log.warn("예외가 발생했지만, 무시합니다.", e);
			}
		} finally {
			if (finallyAction != null)
				finallyAction.perform();
		}
	}

	public static <R> R tryFunction(Func0<R> func) {
		return tryFunction(func, null, null, null);
	}

	public static <R> R tryFunction(Func0<R> func, Func0<R> valueFactory) {
		return tryFunction(func, valueFactory, null, null);
	}

	public static <R> R tryFunction(Func0<R> func,
	                                Func0<R> valueFactory,
	                                Action1<Exception> exceptionAction,
	                                Action0 finallyAction) {
		Guard.shouldNotBeNull(func, "func");
		try {
			return func.execute();
		} catch (Exception e) {
			if (exceptionAction != null) {
				exceptionAction.perform(e);
			} else {
				if (log.isWarnEnabled())
					log.warn("작업 중에 예외가 발생했습니다만 무시합니다.", e);
			}
		} finally {
			if (finallyAction != null)
				finallyAction.perform();
		}
		return (valueFactory != null) ? valueFactory.execute() : (R) null;
	}


	public static void TryActionAsync(final Action0 action, Action1<Exception> exceptionAction, Action0 finallyAction) {

		Guard.shouldNotBeNull(action, "action");
		try {
			FutureTask<Void> futureTask =
				new FutureTask<Void>(new Callable<Void>() {
					@Override
					public Void call() throws Exception {
						action.perform();
						return null;
					}
				});
			futureTask.run();
			Thread.sleep(1);
			futureTask.get();
		} catch (Exception e) {
			if (exceptionAction != null) {
				exceptionAction.perform(e);
			} else {
				if (log.isWarnEnabled())
					log.warn("작업 중에 예외가 발생했습니다만 무시합니다.", e);
			}
		} finally {
			if (finallyAction != null)
				finallyAction.perform();
		}
	}
}
