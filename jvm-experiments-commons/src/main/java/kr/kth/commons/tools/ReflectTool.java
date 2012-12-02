package kr.kth.commons.tools;

import com.google.common.base.Objects;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static kr.kth.commons.Guard.shouldNotBeNull;
import static kr.kth.commons.tools.StringTool.join;


/**
 * Reflection 관련 Utility Class 입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 12
 */
@Slf4j
public final class ReflectTool {

	private ReflectTool() {}

	/**
	 * 객체가 Generic 형식일 경우, 형식인자(type parameter)들을 가져옵니다.
	 *
	 * @param x 검사할 객체
	 * @return 객체가 Generic 형식인 경우, 형식인자의 배열, Generic이 아니면 빈 배열을 반환
	 */
	public static Type[] getParameterTypes(Object x) {
		shouldNotBeNull(x, "x");

		try {
			ParameterizedType ptype = (ParameterizedType) x.getClass().getGenericSuperclass();
			assert ptype != null : "지정된 객체가 generic 형식이 아닙니다.";
			return ptype.getActualTypeArguments();
		} catch (Exception e) {
			log.warn("Generic 형식의 객체로부터 인자 수형들을 추출하는데 실패했습니다.", e);
			// throw new UnsupportedOperationException("Generic 형식의 객체로부터 인자 수형들을 추출하는데 실패했습니다.", e);
			return new Type[0];
		}
	}

	/**
	 * 인스턴스가 Generic 형식이라면 첫번째 Type parameter의 수형을 반환한다.
	 */
	public static <T> Class<T> getGenericParameterType(Object x) {
		return getGenericParameterType(x, 0);
	}

	/**
	 * 인스턴스가 Generic 형식이라면 index+1 번째 Type parameter의 수형을 반환한다.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getGenericParameterType(Object x, int index) throws UnsupportedOperationException {
		shouldNotBeNull(x, "x");

		if (log.isDebugEnabled())
			log.debug("인스턴스 [{}]의 [{}] 번째 제너릭 인자 수형을 찾습니다.", x, index);

		Type[] types = getParameterTypes(x);

		if (types != null && types.length > index)
			return (Class<T>) types[index];

		throw new UnsupportedOperationException("Generic 형식의 객체로부터 인자 수형들을 추출하는데 실패했습니다.");
	}

	/**
	 * 객체의 필드 정보를 이용하여, 객체를 문자열로 표현합니다.
	 */
	public static String objectToString(Object obj) {
		if (obj == null) return "null";

		Objects.ToStringHelper helper = Objects.toStringHelper(obj);

		try {
			Class objClazz = obj.getClass();
			Field[] fields = objClazz.getFields();

			for (Field field : fields)
				helper.add(field.getName(), field.get(obj));
		} catch (IllegalAccessException ignored) {
			if (log.isWarnEnabled())
				log.warn("필드 정보를 얻는데 실패했습니다.", ignored);
		}
		return helper.toString();
	}

	/**
	 * {@link Iterable} 정보를 문자열로 표현합니다.
	 */
	public static <T> String listToString(final Iterable<T> items) {
		if (items == null)
			return "null";

		return join(items, ",");
	}

	/**
	 * {@link java.util.Collection} 정보를 문자열로 표현합니다.
	 */
	public static <T> String listToString(final Collection<T> items) {
		if (items == null)
			return "null";

		return join(items, ",");
	}

	@SafeVarargs
	public static <T> String listToString(final T... items) {
		if (items == null)
			return "null";

		return join(items, ",");
	}

	/**
	 * {@link java.util.Map} 정보를 문자열로 표현합니다.
	 */
	public static String mapToString(final Map map) {
		if (map == null)
			return "null";

		return "{" + join(mapToEntryList(map), ",") + "}";
	}

	private static List<String> mapToEntryList(final Map map) {
		List<String> list = new ArrayList<String>();
		for (Object key : map.keySet()) {
			list.add(key + "=" + map.get(key));
		}
		return list;
	}
}
