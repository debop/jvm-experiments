package kr.kth.commons.cryptography.disgest;

import kr.kth.commons.tools.ArrayTool;
import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;

/**
 * Hash Algorithm 을 이용하여, 데이터를 암호화합니다. (복호화는 불가능합니다.)
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 14
 */
@Slf4j
public abstract class DigestorBase implements Digestor {

	abstract public String getAlgorithm();

	protected byte[] doDigest(byte[] plainBytes) {
		if (ArrayTool.isEmpty(plainBytes))
			return ArrayTool.EmptyByteArray;

		if (log.isDebugEnabled())
			log.debug("Digestor=[{}] 를 이용하여 Hashing 암호화를 수행합니다.", getAlgorithm());

		try {
			MessageDigest digestor = MessageDigest.getInstance(getAlgorithm());
			return digestor.digest(plainBytes);
		} catch (Exception e) {
			if (log.isErrorEnabled())
				log.error("예외가 발생했습니다.", e);
			throw new RuntimeException(e);
		}

	}

	@Override
	public byte[] digest(byte[] plainBytes) {
		if (log.isDebugEnabled())
			log.debug("데이터를 암호화합니다. algorithm=[{}]", getAlgorithm());

		return doDigest(plainBytes);
	}

	@Override
	public String toString() {
		return getAlgorithm() + "Digest";
	}
}
