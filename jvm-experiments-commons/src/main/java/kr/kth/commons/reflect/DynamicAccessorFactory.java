package kr.kth.commons.reflect;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentMap;

/**
 * {@link DynamicAccessor} 의 생성자입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 21
 */
@Slf4j
public class DynamicAccessorFactory {

    static final ConcurrentMap<Class<?>, DynamicAccessor<?>> accessorMap = Maps.newConcurrentMap();

    @SuppressWarnings("unchecked")
    public static <T> DynamicAccessor<T> createAccessor(Class<T> targetType) {
        if (!accessorMap.containsKey(targetType)) {
            accessorMap.putIfAbsent(targetType, new DynamicAccessor<T>(targetType));
            if (log.isDebugEnabled())
                log.debug("새로운 DynamicAccessor<{}> 를 생성했습니다.", targetType.getName());
        }
        return (DynamicAccessor<T>) accessorMap.get(targetType);
    }
}
