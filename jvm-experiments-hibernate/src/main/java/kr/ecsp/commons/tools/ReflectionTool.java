package kr.ecsp.commons.tools;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 설명을 추가하세요.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 19
 */
@Slf4j
public class ReflectionTool {

	private ReflectionTool() {}

	public static Type[] getParameterTypes(Object x) {

		assert x != null;

		try {

			ParameterizedType ptype = (ParameterizedType) x.getClass().getGenericSuperclass();
			assert ptype != null : "지정된 객체가 generic 형식이 아닙니다.";

			return ptype.getActualTypeArguments();

		} catch (Exception e) {
			log.error("Generic 형식의 객체로부터 인자 수형들을 추출하는데 실패했습니다.", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 인스턴스가 Generic 형식이라면 첫번째 Type parameter의 수형을 반환한다.
	 *
	 * @param x
	 * @param <T>
	 * @return
	 */
	public static <T> Class<T> getGenericParameterType(Object x) {
		return getGenericParameterType(x, 0);
	}

	/**
	 * 인스턴스가 Generic 형식이라면 index+1 번째 Type parameter의 수형을 반환한다.
	 *
	 * @param x
	 * @param <T>
	 * @return
	 */
	public static <T> Class<T> getGenericParameterType(Object x, int index) {

		if (log.isDebugEnabled())
			log.debug(String.format("인스턴스 %s 의 %d 번째 제너릭 인자 수형을 찾습니다.", x, index));

		assert x != null;

		try {
			Type[] types = getParameterTypes(x);

			if (types != null && types.length > index)
				return (Class<T>) types[index];

		} catch (Exception e) {
			log.error("Generic 형식의 객체로부터 인자 수형들을 추출하는데 실패했습니다.", e);
			throw new RuntimeException(e);
		}

		return (Class<T>) null;
	}
}
