package kr.kth.commons.parallelism;

import com.google.common.collect.Lists;
import kr.kth.commons.base.Action1;
import kr.kth.commons.base.Func1;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.util.NamedThreadFactory;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static kr.kth.commons.base.Guard.shouldNotBeNull;


/**
 * 대량 데이터에 대한 병렬 실행을 수행할 수 있도록 해주는 Class 입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 26.
 */
@Slf4j
public class Parallels {

	private Parallels() {}

	private static final int PROCESS_COUNT = Runtime.getRuntime().availableProcessors();
	private static final ExecutorService defaultExecutor =
		Executors.newFixedThreadPool(PROCESS_COUNT * 2, new NamedThreadFactory("Parallels"));

	public static ExecutorService getDefaultExecutor() {
		return defaultExecutor;
	}

	public static ExecutorService createExecutor(int threadCount) {
		return Executors.newFixedThreadPool(threadCount);
	}

	public static <T> void run(final Iterable<T> elements, final Action1<T> action) {
		shouldNotBeNull(elements, "elements");
		shouldNotBeNull(action, "action");

		ExecutorService executor = Executors.newFixedThreadPool(PROCESS_COUNT);

		if (log.isDebugEnabled())
			log.debug("작업을 병렬로 수행합니다. 작업 스레드 수=" + PROCESS_COUNT);

		try {
			List<T> elemList = Lists.newArrayList(elements);
			List<List<T>> partitions = Lists.partition(elemList, PROCESS_COUNT);
			List<Callable<Void>> tasks = new LinkedList<Callable<Void>>();

			for (final List<T> partition : partitions) {
				Callable<Void> task =
					new Callable<Void>() {
						@Override
						public Void call() throws Exception {
							for (final T element : partition)
								action.perform(element);
							return null;
						}
					};
				tasks.add(task);
			}
			executor.invokeAll(tasks);

			if (log.isDebugEnabled())
				log.debug("모든 작업을 병렬로 수행하였습니다. partitions=" + partitions.size());

		} catch (Exception e) {
			log.error("데이터에 대한 병렬 작업 중 예외가 발생했습니다.", e);
		} finally {
			executor.shutdown();
		}
	}

	public static <T, V> List<V> run(final Iterable<T> elements, final Func1<T, V> function) {
		shouldNotBeNull(elements, "elements");
		shouldNotBeNull(function, "function");

		ExecutorService executor = Executors.newFixedThreadPool(PROCESS_COUNT);
		final List<V> results = new ArrayList<V>();

		if (log.isDebugEnabled())
			log.debug("작업을 병렬로 수행합니다. 작업 스레드 수=" + PROCESS_COUNT);

		try {
			List<T> elemList = Lists.newArrayList(elements);
			List<List<T>> partitions = Lists.partition(elemList, PROCESS_COUNT);
			final Map<Integer, List<V>> localResults = new LinkedHashMap<Integer, List<V>>();

			List<Callable<List<V>>> tasks = new LinkedList<Callable<List<V>>>();

			for (int p = 0; p < partitions.size(); p++) {
				final List<T> partition = partitions.get(p);
				final List<V> localResult = new ArrayList<V>();
				localResults.put(p, localResult);

				Callable<List<V>> task = new Callable<List<V>>() {
					@Override
					public List<V> call() throws Exception {
						for (final T element : partition)
							localResult.add(function.execute(element));
						return localResult;
					}
				};
				tasks.add(task);
			}

			executor.invokeAll(tasks);

			for (int i = 0; i < partitions.size(); i++) {
				results.addAll(localResults.get(i));
			}

			if (log.isDebugEnabled())
				log.debug("모든 작업을 병렬로 완료했습니다. partitions=" + partitions.size());

		} catch (Exception e) {
			log.error("데이터에 대한 병렬 작업 중 예외가 발생했습니다.", e);
		} finally {
			executor.shutdown();
		}

		return results;
	}
}
