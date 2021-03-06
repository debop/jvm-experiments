package kr.nsoft.commons.io;

import kr.nsoft.commons.Guard;
import kr.nsoft.commons.ISerializer;

/**
 * {@link ISerializer} 를 데코레이터 패턴으로, 여러가지 작업을 수행할 수 있도록 합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 17
 */
public abstract class SerializerDecorator implements ISerializer {

    private final ISerializer serializer;

    public SerializerDecorator(ISerializer serializer) {
        Guard.shouldNotBeNull(serializer, "serializer");
        this.serializer = serializer;
    }


    @Override
    public byte[] serialize(Object graph) {
        return serializer.serialize(graph);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        return serializer.deserialize(bytes, clazz);
    }
}
