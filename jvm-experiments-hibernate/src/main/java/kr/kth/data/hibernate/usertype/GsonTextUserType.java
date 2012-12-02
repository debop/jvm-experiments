package kr.kth.data.hibernate.usertype;

import kr.kth.commons.json.GsonSerializer;
import kr.kth.commons.json.JsonSerializer;

/**
 * {@link GsonSerializer} 를 이용하여, 객체를 Json 직렬화하여 저장하는 사용자 타입입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
public class GsonTextUserType extends AbstractJsonTextUserType {

	private static final JsonSerializer serializer = new GsonSerializer();

	@Override
	public JsonSerializer getJsonSerializer() {
		return serializer;
	}
}
