package kr.kth.commons.parallelism;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import kr.kth.commons.base.Action1;
import kr.kth.commons.base.Func1;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.*;

import static java.lang.String.format;
import static kr.kth.commons.base.Guard.shouldNotBeNull;

/**
 * 비동기 작업 관련 Utility Class
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 14
 */
@Slf4j
public class AsyncTaskTool {

	private AsyncTaskTool() {}

	private static ExecutorService executor =
		Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() * 2);

	public static final Runnable emptyRunnable =
		new Runnable() {
			@Override
			public void run() {
				// nothing to do.
			}
		};

	public static ExecutorService getExecutor() {
		return executor;
	}

	/**
	 * 새로운 작업을 생성합니다.
	 */
	public static <T> FutureTask<T> newTask(Callable<T> callable) {
		return new FutureTask<T>(callable);
	}

	/**
	 * 새로운 작업을 생성합니다.
	 */
	public static <T> FutureTask<T> newTask(Runnable runnable, @Nullable T result) {
		return new FutureTask<T>(runnable, result);
	}

	/**
	 * 새로운 작업을 생성하고, 자동으로 시작합니다.
	 */
	public static <T> Future<T> startNew(Callable<T> callable) {
		return executor.submit(callable);
	}

	/**
	 * 새로운 작업을 생성하고, 작업을 실행합니다.
	 */
	public static <T> Future<T> startNew(Runnable runnable, @Nullable T result) {
		return executor.submit(runnable, result);
	}

	public static <T, V> Future<V> continueTask(final FutureTask<T> prevTask,
	                                            final Action1<T> action,
	                                            final @Nullable V result) {
		Callable<V> chainTask = new Callable<V>() {
			@Override
			public V call() throws Exception {
				T prev = prevTask.get();
				action.perform(prev);
				return result;
			}
		};

		return startNew(chainTask);
	}

	public static <T, V> Future<V> continueTask(final FutureTask<T> prevTask,
	                                            final Func1<T, V> function) {
		return startNew(new Callable<V>() {
			@Override
			public V call() throws Exception {
				return function.execute(prevTask.get());
			}
		});
	}


	public static <T> FutureTask<T> getTaskHasResult(T result) {
		return newTask(emptyRunnable, result);
	}

	/**
	 * 지정한 시퀀스를 인자로 하는 함수를 수행하고, 결과를 반환하는 {@link java.util.concurrent.FutureTask} 의 리스트를 반환한다.
	 */
	public static <T, R> List<Future<R>> runAsync(final Iterable<T> elements,
	                                              final Func1<T, R> function) throws InterruptedException {
		shouldNotBeNull(function, "function");

		List<Callable<R>> tasks = Lists.newArrayList();
		for (final T element : elements) {
			Callable<R> task = new Callable<R>() {
				@Override
				public R call() throws Exception {
					return function.execute(element);
				}
			};

			tasks.add(task);
		}
		return executor.invokeAll(tasks);
	}

	/**
	 * 비동기 작업들을 실행하고, 작업이 완료되거나 취소될때까지 기다립니다.
	 */
	public static <T> void runAll(Iterable<FutureTask<T>> tasks) throws Exception {

		if (log.isDebugEnabled())
			log.debug("비동기 작업들을 실행시킵니다...");

		for (final FutureTask<T> task : tasks) {
			if (!task.isCancelled() && !task.isDone())
				task.run();
		}

		if (log.isDebugEnabled())
			log.debug("비동기 작업들이 완료될 때까지 기다립니다...");

		waitAllTasks(tasks);

		if (log.isDebugEnabled())
			log.debug("비동기 작업들이 모두 완료 되었습니다!!!");
	}

	/**
	 * 비동기 작업 목록들의 결과값을 모두 취합하여 반환합니다. (동시에 모든 작업을 수행하여, 성능 상 이익입니다.)
	 */
	public static <T> List<T> getAll(Iterable<? extends Future<T>> tasks) throws Exception {

		if (log.isDebugEnabled())
			log.debug("비동기 작업의 결과를 취합합니다...");

		List<T> results = new CopyOnWriteArrayList<T>();

		for (final Future<T> task : tasks) {
			results.add(task.get());
		}
		return results;
	}

	/**
	 * 비동기 작업 목록들의 결과값을 모두 취합하여 반환합니다. (동시에 모든 작업을 수행하여, 성능 상 이익입니다.)
	 */
	public static <T> List<T> getAll(Iterable<? extends Future<T>> tasks,
	                                 long timeout,
	                                 TimeUnit unit) throws Exception {
		if (log.isDebugEnabled())
			log.debug(format("비동기 작업의 결과를 취합합니다... timeout=[%d], unit=[%s]", timeout, unit));

		List<T> results = new CopyOnWriteArrayList<T>();

		for (final Future<T> task : tasks) {
			results.add(task.get(timeout, unit));
		}
		return results;
	}

	/**
	 * 모든 {@link java.util.concurrent.Future}의 {@link java.util.concurrent.Future#isDone()}
	 * 또는 {@link java.util.concurrent.Future#isCancelled()} 가 될 때까지 기다립니다.
	 */
	public static <T> void waitAll(Iterable<Future<T>> futures) {
		boolean allCompleted = false;

		while (!allCompleted) {
			allCompleted = Iterables.all(futures, new Predicate<Future<T>>() {
				@Override
				public boolean apply(@Nullable Future<T> input) {
					if (input == null)
						return true;
					return (input.isDone() || input.isCancelled());
				}
			});
		}
	}

	/**
	 * 모든 {@link java.util.concurrent.FutureTask}의 {@link java.util.concurrent.Future#isDone()}
	 * 또는 {@link java.util.concurrent.Future#isCancelled()} 가 될 때까지 기다립니다.
	 */
	public static <T> void waitAllTasks(Iterable<FutureTask<T>> futureTasks) {
		boolean allCompleted = false;
		while (!allCompleted) {
			allCompleted = Iterables.all(futureTasks, new Predicate<FutureTask<T>>() {
				@Override
				public boolean apply(@Nullable FutureTask<T> input) {
					assert input != null;
					return (input.isDone() || input.isCancelled());
				}
			});
		}
	}
}
