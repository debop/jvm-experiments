package kr.nsoft.commons.tools;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static kr.nsoft.commons.Guard.shouldNotBeNull;


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

        if (log.isDebugEnabled())
            log.debug("객체가 Generic 수형이라면 모든 형식인자들을 가져옵니다. clazz=[{}]", x.getClass().getName());
        try {
            ParameterizedType ptype = (ParameterizedType) x.getClass().getGenericSuperclass();
            assert ptype != null : "지정된 객체가 generic 형식이 아닙니다.";
            return ptype.getActualTypeArguments();
        } catch (Exception e) {
            log.warn("Generic 형식의 객체로부터 인자 수형들을 추출하는데 실패했습니다.", e);
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
    public static <T> Class<T> getGenericParameterType(Object x, int index) {
        shouldNotBeNull(x, "x");

        if (log.isDebugEnabled())
            log.debug("인스턴스 [{}]의 [{}] 번째 제너릭 인자 수형을 찾습니다.", x.getClass().getName(), index);

        Type[] types = getParameterTypes(x);

        if (types != null && types.length > index)
            return (Class<T>) types[index];

        throw new UnsupportedOperationException("Generic 형식의 객체로부터 인자 수형들을 추출하는데 실패했습니다.");
    }

    /**
     * 수형이 primitive type 과 호환된다면 Primitive type으로 변경합니다.
     *
     * @param clazz
     * @return
     */
    public static Class toPrimitiveType(Class<?> clazz) {
        if (clazz == java.lang.Boolean.class)
            return Boolean.TYPE;
        else if (clazz == java.lang.Character.class)
            return Character.TYPE;
        else if (clazz == java.lang.Byte.class)
            return Byte.TYPE;
        else if (clazz == java.lang.Short.class)
            return Short.TYPE;
        else if (clazz == java.lang.Integer.class)
            return Integer.TYPE;
        else if (clazz == java.lang.Long.class)
            return Long.TYPE;
        else if (clazz == java.lang.Float.class)
            return Float.TYPE;
        else if (clazz == java.lang.Double.class)
            return Double.TYPE;
        else
            return clazz;
    }
}
