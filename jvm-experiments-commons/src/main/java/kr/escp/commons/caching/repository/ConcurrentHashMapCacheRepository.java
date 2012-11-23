package kr.escp.commons.caching.repository;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import kr.escp.commons.Guard;
import kr.escp.commons.caching.CacheRepositoryBase;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 설명을 추가하세요.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 12
 */
@Slf4j
@SuppressWarnings("unchecked")
public class ConcurrentHashMapCacheRepository extends CacheRepositoryBase {

	private final Cache<String, Object> cache;


	public ConcurrentHashMapCacheRepository(long validFor) {
		if (validFor > 0)
			setExpiry(validFor);

		CacheBuilder builder = CacheBuilder.newBuilder().concurrencyLevel(4);

		if (validFor > 0)
			builder.expireAfterAccess(validFor, TimeUnit.MINUTES);

		cache = builder.build();
	}

	/**
	 * 캐시 저장소
	 *
	 * @return 캐시 저장소를 반환합니다.
	 */
	public Map<String, Object> getCache() {
		return this.cache.asMap();
	}

	/**
	 * 캐시에서 해당 키의 항목을 가져옵니다.
	 *
	 * @param key 캐시 키
	 * @return 캐시 항목, 없으면 null 반환
	 */
	@Override
	public Object get(final String key) {
		Guard.shouldNotBeWhiteSpace(key, "key");
		return cache.getIfPresent(key);
	}

	public Future<Object> getAsync(final String key) {
		return
			Executors.newCachedThreadPool().submit(new Callable() {
				public Object call() {
					return cache.getIfPresent(key);
				}
			});
	}

	/**
	 * 캐시에 항목을 저장합니다.
	 *
	 * @param key      캐시 키
	 * @param value    캐시 항목
	 * @param validFor 캐시 유효 기간 (단위 : minutes), 0 이하인 경우는 유효기간이 없다.
	 */
	@Override
	public void set(final String key, final Object value, final long validFor) {
		Guard.shouldNotBeWhiteSpace(key, "key");
		cache.put(key, value);
	}

	/**
	 * 해당 키의 캐시 항목을 삭제합니다.
	 *
	 * @param key 캐시 키
	 */
	@Override
	public void remove(final String key) {
		Guard.shouldNotBeWhiteSpace(key, "key");
		cache.invalidate(key);
	}

	/**
	 * 여러개의 키를 모두 삭제합니다.
	 *
	 * @param keys 캐시 키 시퀀스
	 */
	@Override
	public void removes(String... keys) {
		cache.invalidateAll(Arrays.asList(keys));
	}

	/**
	 * 해당 키의 캐시 항목의 존재 여부를 알아봅니다.
	 *
	 * @param key 캐시 키
	 * @return 캐시 항목 존재 여부
	 */
	@Override
	public boolean exists(final String key) {
		Guard.shouldNotBeWhiteSpace(key, "key");
		return cache.getIfPresent(key) != null;
	}

	/**
	 * 캐시의 모든 항목을 삭제합니다.
	 */
	@Override
	public void clear() {
		cache.cleanUp();
	}
}