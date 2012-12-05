package kr.kth.commons.unitTesting;

import com.google.common.collect.Lists;
import kr.kth.commons.base.Action1;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.*;

/**
 * kr.kth.commons.unitTesting.TestTool
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 3.
 */
@Slf4j
public class TestTool {

	private TestTool() {}

	public static void runTasks(int count, final Runnable runnable) {

		ExecutorService executor =
			Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

		try {
			final CountDownLatch latch = new CountDownLatch(count);
			for (int i = 0; i < count; i++) {
				executor.submit(new Runnable() {
					@Override
					public void run() {
						runnable.run();
						latch.countDown();
					}
				});
			}
			latch.await();
		} catch (InterruptedException e) {
			if (log.isErrorEnabled())
				log.error("예외가 발생했습니다.", e);
			throw new RuntimeException(e);
		} finally {
			executor.shutdown();
		}
	}

	public static <T> void runTasks(int count, Callable<T>... callables) {

		ExecutorService executor =
			Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

		try {
			List<Future<T>> futures = Lists.newArrayList();

			for (int i = 0; i < count; i++) {
				List<Future<T>> results = executor.invokeAll(Lists.newArrayList(callables));
				futures.addAll(results);
			}
			for (Future<T> future : futures) {
				future.get();
			}

		} catch (ExecutionException | InterruptedException e) {
			if (log.isErrorEnabled())
				log.error("예외가 발생했습니다.", e);
			throw new RuntimeException(e);
		} finally {
			executor.shutdown();
		}
	}

	public static void runTasks(int count, final Action1<Integer> action) {
		ExecutorService executor =
			Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

		final CountDownLatch latch = new CountDownLatch(count);
		try {

			for (int i = 0; i < count; i++) {
				final int index = i;
				executor.execute(new Runnable() {
					@Override
					public void run() {
						action.perform(index);
						latch.countDown();
					}
				});
			}
			latch.await();

		} catch (InterruptedException e) {
			if (log.isErrorEnabled())
				log.error("예외가 발생했습니다.", e);
			throw new RuntimeException(e);
		} finally {
			executor.shutdown();
		}
	}
}