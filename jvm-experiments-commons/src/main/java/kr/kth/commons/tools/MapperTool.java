package kr.kth.commons.tools;

import com.google.common.collect.Lists;
import kr.kth.commons.parallelism.AsyncTaskTool;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import static kr.kth.commons.base.Guard.shouldNotBeNull;


/**
 * {@link org.modelmapper.ModelMapper} 를 이용하여, 객체간의 정보를 매핑합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 20.
 */
@Slf4j
public final class MapperTool {

	private MapperTool() {}

	private static final ModelMapper mapper;

	static {
		if (log.isDebugEnabled())
			log.debug("ModelMapper를 초기화합니다.");

		mapper = new ModelMapper();
		mapper.getConfiguration()
		      .enableFieldMatching(true)
		      .setMatchingStrategy(MatchingStrategies.LOOSE)
		      .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);

		if (log.isInfoEnabled())
			log.info("ModelMapper를 초기화했습니다.");
	}

	public static <T> T map(Object source, Class<T> destinationClass) {
		shouldNotBeNull(source, "source");
		shouldNotBeNull(destinationClass, "destinationClass");

		return mapper.map(source, destinationClass);
	}

	public static void map(Object source, Object destination) {
		shouldNotBeNull(source, "source");
		shouldNotBeNull(destination, "destination");

		mapper.map(source, destination);
	}

	public static <T> List<T> mapList(Iterable<T> sources, Class<T> destinationClass) {
		List<T> destinations = Lists.newArrayList();

		for (T source : sources) {
			destinations.add(mapper.map(source, destinationClass));
		}
		return destinations;
	}

	public static <T> Future<T> mapAsync(final Object source, final Class<T> destinationClass) {
		return AsyncTaskTool.startNew(new Callable<T>() {
			@Override
			public T call() throws Exception {
				return mapper.map(source, destinationClass);
			}
		});
	}

	public static <T> Future<List<T>> mapListAsync(final Iterable<T> sources, final Class<T> destinationClass) {

		return AsyncTaskTool.startNew(new Callable<List<T>>() {
			@Override
			public List<T> call() throws Exception {
				List<T> destinations = Lists.newArrayList();
				for (T source : sources) {
					destinations.add(mapper.map(source, destinationClass));
				}
				return destinations;
			}
		});
	}
}
