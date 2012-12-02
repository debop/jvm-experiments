package kr.kth.commons.cryptography.disgest;

/**
 * SHA512 알고리즘을 이용한 Digestor
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 14
 */
public final class SHA512Digestor extends DigestorBase {

	@Override
	public final String getAlgorithm() {
		return "SHA-512";
	}
}
