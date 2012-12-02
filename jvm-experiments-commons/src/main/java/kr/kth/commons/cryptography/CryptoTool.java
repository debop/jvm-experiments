package kr.kth.commons.cryptography;

import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;

/**
 * 설명을 추가하세요.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 14
 */
@Slf4j
public class CryptoTool {

	private CryptoTool() {}

	private static final String RandomNumberGeneration = "SHA1PRNG";

	public static byte[] getRandomBytes(int numBytes) throws Exception {
		SecureRandom random = SecureRandom.getInstance(RandomNumberGeneration);

		byte[] bytes = new byte[numBytes];
		random.nextBytes(bytes);
		return bytes;
	}
}
