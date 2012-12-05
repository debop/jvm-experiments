package kr.kth.commons.caching.repository;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import kr.kth.commons.caching.CacheRepositoryBase;
import kr.kth.commons.tools.StreamTool;
import kr.kth.commons.tools.StringTool;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.DefaultHttpAsyncClient;
import org.apache.http.nio.client.HttpAsyncClient;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * kr.kth.commons.caching.repository.FutureWebCacheRepository
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 5.
 */
@Slf4j
public class FutureWebCacheRepository extends CacheRepositoryBase {

	private final LoadingCache<String, String> cache;

	public FutureWebCacheRepository() {

		cache =
			CacheBuilder.newBuilder().build(new CacheLoader<String, String>() {
				@Override
				public String load(String key) throws Exception {

					if (FutureWebCacheRepository.log.isDebugEnabled())
						FutureWebCacheRepository.log.debug("URI=[{}] 의 웹 컨텐츠를 비동기 방식으로 다운로드 받아 캐시합니다.", key);

					String responseStr = "";
					HttpAsyncClient httpClient = new DefaultHttpAsyncClient();
					try {
						httpClient.start();
						HttpGet request = new HttpGet(key);
						Future<HttpResponse> future = httpClient.execute(request, null);

						HttpResponse response = future.get();

						Header encodingHeader = response.getEntity().getContentEncoding();
						String encoding = encodingHeader != null ? encodingHeader.getValue() : "";
						responseStr = StringTool.getString(StreamTool.toByteArray(response.getEntity().getContent()),
						                                   encoding);
					} finally {
						httpClient.shutdown();
					}
					return responseStr;
				}
			});
	}

	@Override
	public Object get(String key) {
		try {
			return cache.get(key);
		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void set(String key, Object value, long validFor) {
		String str = (value != null) ? value.toString() : "";
		cache.put(key, str);
	}

	@Override
	public void remove(String key) {
		cache.invalidate(key);
	}

	@Override
	public void removes(String... keys) {
		cache.invalidateAll(Arrays.asList(keys));
	}

	@Override
	public boolean exists(String key) {
		return cache.getIfPresent(key) != null;
	}

	@Override
	public void clear() {
		cache.cleanUp();
	}
}
