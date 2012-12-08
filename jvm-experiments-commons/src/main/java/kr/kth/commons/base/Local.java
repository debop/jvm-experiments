package kr.kth.commons.base;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.concurrent.ThreadSafe;
import java.util.HashMap;

import static kr.kth.commons.base.Guard.shouldNotBeNull;


/**
 * Thread Context 별로 격리된 저장소를 제공합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 12
 */
@Slf4j
@ThreadSafe
public class Local {

	private Local() { }

	private static ThreadLocal<HashMap> threadLocal =
		new ThreadLocal<HashMap>() {
			@Override
			public HashMap initialValue() {
				return Maps.newLinkedHashMap();
			}
		};

	private static HashMap getMap() {
		return threadLocal.get();
	}

	public static Object get(Object key) {
		shouldNotBeNull(key, "key");
		return getMap().get(key);
	}

	@SuppressWarnings("unchecked")
	public static void put(Object key, Object value) {
		shouldNotBeNull(key, "key");
		getMap().put(key, value);
	}

	public static void clear() {
		if (threadLocal.get() != null) {
			threadLocal.get().clear();
			threadLocal.remove();
		}
	}
}
