package kr.kth.commons.caching.repository;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import java.util.Arrays;
import java.util.Map;

import static kr.kth.commons.Guard.shouldNotBeNull;
import static kr.kth.commons.Guard.shouldNotBeWhiteSpace;


/**
 * EhCache 용 Cache Repository
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 12.
 */
@Slf4j
public class EhCacheRepository extends CacheRepositoryBase {

    @Getter
    private final Ehcache ehcache;

    public EhCacheRepository(Ehcache ehcache) {
        if (log.isDebugEnabled())
            log.debug("EhCacheRepository 인스턴스를 생성합니다. ehcache=[{}]", ehcache);
        this.ehcache = shouldNotBeNull(ehcache, "ehcache");
    }

    @Override
    public Object get(final String key) {
        shouldNotBeWhiteSpace(key, "key");

        Element element = ehcache.get(key);
        if (element != null)
            element.getValue();
        return null;
    }

    public Map gets(String... keys) {
        return ehcache.getAll(Arrays.asList(keys));
    }

    @Override
    public void set(final String key, Object value, long validFor) {
        shouldNotBeWhiteSpace(key, "key");
        ehcache.put(new Element(key, value, validFor));
    }

    @Override
    public void remove(final String key) {
        shouldNotBeWhiteSpace(key, "key");
        ehcache.remove(key);
    }

    @Override
    public void removes(String... keys) {
        ehcache.removeAll(Arrays.asList(keys));
    }

    @Override
    public boolean exists(final String key) {
        shouldNotBeWhiteSpace(key, "key");
        return ehcache.get(key) != null;
    }

    @Override
    public void clear() {
        ehcache.removeAll();
    }
}
