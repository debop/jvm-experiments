package kr.kth.commons.io;

import kr.kth.commons.Guard;
import kr.kth.commons.ISerializer;
import kr.kth.commons.compress.GZipCompressor;
import kr.kth.commons.compress.ICompressor;
import lombok.extern.slf4j.Slf4j;

/**
 * {@link ISerializer} 를 통해 직렬화/역직렬환 정보를 압축/복원을 수행하여 전달한다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 17
 */
@Slf4j
public class CompressableSerializer extends SerializerDecorator {

    private final ICompressor compressor;

    public CompressableSerializer(ISerializer serializer) {
        this(serializer, new GZipCompressor());
    }

    public CompressableSerializer(ISerializer serializer, ICompressor compressor) {
        super(serializer);
        Guard.shouldNotBeNull(compressor, "compressor");
        this.compressor = compressor;
    }

    @Override
    public byte[] serialize(Object graph) {
        return compressor.compress(super.serialize(graph));
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        return super.deserialize(compressor.decompress(bytes), clazz);
    }
}
