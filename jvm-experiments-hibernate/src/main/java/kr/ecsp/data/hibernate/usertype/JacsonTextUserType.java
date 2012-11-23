package kr.ecsp.data.hibernate.usertype;

import kr.escp.commons.json.JacksonSerializer;
import kr.escp.commons.json.JsonSerializer;

/**
 * {@link JacksonSerializer} 를 이용하여, 객체를 Json 직렬화하여 저장하는 사용자 타입입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
public class JacsonTextUserType extends AbstractJsonTextUserType {

	private static final JsonSerializer serializer = new JacksonSerializer();

	@Override
	public JsonSerializer getJsonSerializer() {
		return serializer;
	}
}
