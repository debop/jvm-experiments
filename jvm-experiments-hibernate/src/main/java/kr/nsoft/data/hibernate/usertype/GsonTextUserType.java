package kr.nsoft.data.hibernate.usertype;

import kr.nsoft.commons.json.GsonSerializer;
import kr.nsoft.commons.json.IJsonSerializer;

/**
 * {@link GsonSerializer} 를 이용하여, 객체를 Json 직렬화하여 저장하는 사용자 타입입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
public class GsonTextUserType extends AbstractJsonTextUserType {

    private static final IJsonSerializer serializer = new GsonSerializer();

    @Override
    public IJsonSerializer getJsonSerializer() {
        return serializer;
    }
}
