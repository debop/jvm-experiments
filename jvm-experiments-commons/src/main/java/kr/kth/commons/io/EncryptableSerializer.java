package kr.kth.commons.io;

import kr.kth.commons.base.Guard;
import kr.kth.commons.base.ISerializer;
import kr.kth.commons.cryptography.symmetric.ISymmetricEncryptor;
import kr.kth.commons.cryptography.symmetric.RC2Encryptor;

/**
 * 객체 직렬화를 수행한 후, 암호화를 수행합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 17
 */
public class EncryptableSerializer extends SerializerDecorator {

	private final ISymmetricEncryptor encryptor;

	public EncryptableSerializer(ISerializer serializer) {
		this(serializer, new RC2Encryptor());
	}

	public EncryptableSerializer(ISerializer serializer, ISymmetricEncryptor encryptor) {
		super(serializer);

		Guard.shouldNotBeNull(encryptor, "encryptor");
		this.encryptor = encryptor;
	}

	@Override
	public byte[] serialize(Object graph) {
		return encryptor.encrypt(super.serialize(graph));
	}

	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clazz) {
		return super.deserialize(encryptor.decrypt(bytes), clazz);
	}
}
