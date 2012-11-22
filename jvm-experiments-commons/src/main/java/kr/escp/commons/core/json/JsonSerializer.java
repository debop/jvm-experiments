package kr.escp.commons.core.json;

import kr.escp.commons.core.Serializer;

/**
 * JSON 포맷으로 직렬화 / 역직렬화를 수행합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 14
 */
public interface JsonSerializer extends Serializer {

	/**
	 * JSON 포맷으로 직렬화하여 Json Text 형식의 문자열로 반환합니다.
	 */
	String serializeAsText(Object graph);

	/**
	 * JSON 포맷으로 직렬화된 정보를 역직렬화하여 객체로 인스턴싱합니다.
	 */
	<T> T deserialize(byte[] bytes, Class<? extends T> targetType);

	/**
	 * Json Text 형식의 문자열을 역직렬화하여, 객체로 빌드합니다.
	 */
	<T> T deserialize(String jsonText, Class<? extends T> targetType);
}
