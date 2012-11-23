package kr.escp.commons.caching.repository;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableMap;
import kr.escp.commons.caching.CacheRepositoryBase;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static kr.escp.commons.Guard.shouldNotBeNull;
import static kr.escp.commons.Guard.shouldNotBeWhiteSpace;


/**
 * 설명을 추가하세요.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 12
 */
@Slf4j
@SuppressWarnings("unchecked")
public class HashMapCacheRepository extends CacheRepositoryBase {

	@Inject
	private final Cache cache;

	public HashMapCacheRepository(Cache<String, Object> cache) {

		this.cache = shouldNotBeNull(cache, "cache");
	}

	public HashMapCacheRepository(long validFor) {

		if (validFor > 0)
			setExpiry(validFor);

		CacheBuilder builder = CacheBuilder.newBuilder();

		if (validFor > 0)
			builder.expireAfterAccess(validFor, TimeUnit.MINUTES);

		cache = builder.build();
	}

	/**
	 * {@inheritDoc}
	 */
	public ConcurrentMap getCache() {
		return this.cache.asMap();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object get(final String key) {
		shouldNotBeWhiteSpace(key, "key");
		return cache.getIfPresent(key);
	}

	public Object get(final String key, Callable<?> valueLoader) throws ExecutionException {
		shouldNotBeWhiteSpace(key, "key");
		return cache.get(key, valueLoader);
	}

	public ImmutableMap getAllPresent(Iterable<?> keys) {
		return cache.getAllPresent(keys);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void set(final String key, final Object value, final long validFor) {
		shouldNotBeWhiteSpace(key, "key");
		cache.put(key, value);
	}

	public void setAll(Map m) {
		cache.putAll(m);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remove(final String key) {
		shouldNotBeWhiteSpace(key, "key");
		cache.invalidate(key);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removes(String... keys) {
		cache.invalidateAll(Arrays.asList(keys));
	}

	public void removes(Iterable<?> keys) {
		cache.invalidateAll(keys);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean exists(final String key) {
		shouldNotBeWhiteSpace(key, "key");
		return cache.getIfPresent(key) != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		cache.invalidateAll();
	}
}