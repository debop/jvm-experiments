package kr.kth.commons.tools;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;

import static kr.kth.commons.Guard.shouldNotBeNull;


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
}
