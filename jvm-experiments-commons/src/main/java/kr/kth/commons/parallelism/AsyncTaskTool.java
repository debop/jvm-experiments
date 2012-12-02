package kr.kth.commons.parallelism;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ListenableFutureTask;
import kr.kth.commons.Func1;
import kr.kth.commons.Guard;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.*;

import static java.lang.String.format;

/**
 * 비동기 작업 관련 Utility Class
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 14
 */
@Slf4j
public class AsyncTaskTool {

	private AsyncTaskTool() {}

	/**
	 * 새로운 작업을 생성합니다.
	 */
	public static <T> ListenableFutureTask<T> New(Callable<T> callable) {
		return ListenableFutureTask.create(callable);
	}

	/**
	 * 새로운 작업을 생성합니다.
	 */
	public static <T> ListenableFutureTask<T> New(Runnable runnable, @Nullable T result) {
		return ListenableFutureTask.create(runnable, result);
	}

	/**
	 * 새로운 작업을 생성하고, 자동으로 시작합니다.
	 */
	public static <T> ListenableFutureTask<T> StartNew(Callable<T> callable) {
		return ListenableFutureTask.create(callable);
	}

	/**
	 * 새로운 작업을 생성하고, 작업을 실행합니다.
	 */
	public static <T> ListenableFutureTask<T> StartNew(Runnable runnable, @Nullable T result) {
		ListenableFutureTask<T> task = ListenableFutureTask.create(runnable, result);
		task.run();
		return task;
	}

	public static <T> ListenableFutureTask<T> Continue(ListenableFutureTask<T> antecedent,
	                                                   Runnable runnable,
	                                                   @Nullable T result) {
		// TODO: antencedent Task가 완료되는 시점에 runnable 작업이 실행되도록 하여 마지막 작업을 반환한다.
		// 이러한 작업은 작업들을 Chain 방식으로 연속으로 수행하기 위해 합니다. (성공/실패/취소 에 따른 분기도 가능합니다)

		return antecedent;
	}

	/**
	 * 지정한 시퀀스를 인자로 하는 함수를 수행하고, 결과를 반환하는 {@link java.util.concurrent.FutureTask} 의 리스트를 반환한다.
	 */
	public static <T, R> List<FutureTask<R>> runAsync(final Iterable<T> elements,
	                                                  final Func1<T, R> function) {
		Guard.shouldNotBeNull(function, "function");

		List<FutureTask<R>> tasks = Lists.newLinkedList();
		for (final T element : elements) {
			FutureTask<R> task =
				new FutureTask<R>(new Callable<R>() {
					@Override
					public R call() throws Exception {
						return function.execute(element);
					}
				});
			tasks.add(task);
		}
		return tasks;
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
	public static <T> List<T> getAll(Iterable<? extends FutureTask<T>> tasks) throws Exception {

		if (log.isDebugEnabled())
			log.debug("비동기 작업의 결과를 취합합니다...");

		List<T> results = new CopyOnWriteArrayList<T>();

		for (final FutureTask<T> task : tasks) {
			results.add(task.get());
		}
		return results;
	}

	/**
	 * 비동기 작업 목록들의 결과값을 모두 취합하여 반환합니다. (동시에 모든 작업을 수행하여, 성능 상 이익입니다.)
	 */
	public static <T> List<T> getAll(Iterable<? extends FutureTask<T>> tasks,
	                                 long timeout,
	                                 TimeUnit unit) throws Exception {
		if (log.isDebugEnabled())
			log.debug(format("비동기 작업의 결과를 취합합니다... timeout=[%d], unit=[%s]", timeout, unit));

		List<T> results = new CopyOnWriteArrayList<T>();

		for (final FutureTask<T> task : tasks) {
			results.add(task.get(timeout, unit));
		}
		return results;
	}

	/**
	 * 모든 {@link java.util.concurrent.Future}의 {@link java.util.concurrent.Future#isDone()} 또는 {@link java.util.concurrent.Future#isCancelled()} 가 될 때까지 기다립니다.
	 */
	public static <T> void waitAll(Iterable<Future<T>> futures) {

		boolean allCompleted = false;
		while (!allCompleted) {
			allCompleted = Iterables.all(futures, new Predicate<Future<T>>() {
				@Override
				public boolean apply(@Nullable Future<T> input) {
					assert input != null;
					return (input.isDone() || input.isCancelled());
				}
			});
		}
	}

	/**
	 * 모든 {@link java.util.concurrent.FutureTask}의 {@link java.util.concurrent.Future#isDone()} 또는 {@link java.util.concurrent.Future#isCancelled()} 가 될 때까지 기다립니다.
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
