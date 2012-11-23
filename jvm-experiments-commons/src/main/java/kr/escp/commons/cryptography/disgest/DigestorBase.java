package kr.escp.commons.cryptography.disgest;

import kr.escp.commons.tools.ArrayTool;
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

	protected byte[] doDigest(byte[] plainBytes) throws Exception {
		if (ArrayTool.isEmpty(plainBytes))
			return ArrayTool.EmptyByteArray;

		MessageDigest digestor = MessageDigest.getInstance(getAlgorithm());
		return digestor.digest(plainBytes);
	}

	@Override
	public byte[] digest(byte[] plainBytes) throws Exception {
		if (log.isDebugEnabled())
			log.debug("데이터를 암호화합니다. algorithm=" + getAlgorithm());

		return doDigest(plainBytes);
	}

	@Override
	public String toString() {
		return getAlgorithm() + "Digest";
	}
}
