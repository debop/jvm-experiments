package kr.ecsp.commons.tools;

import lombok.extern.slf4j.Slf4j;

/**
 * 설명을 추가하세요.
 * JpaUser: sunghyouk.bae@gmail.com
 * Date: 12. 11. 19
 */
@Slf4j
public class ActivatorTool {

	private ActivatorTool() {}

	public static Object getInstance(String className)
		throws ClassNotFoundException, IllegalAccessException, InstantiationException {
		return Class.forName(className).newInstance();
	}

	public static Object getInstance(Class clazz) throws IllegalAccessException, InstantiationException {
		assert clazz != null;
		return clazz.newInstance();
	}
}
