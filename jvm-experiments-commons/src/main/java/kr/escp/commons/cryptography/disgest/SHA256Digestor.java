package kr.escp.commons.cryptography.disgest;

/**
 * SHA-256 알고리즘을 이용한 Digestor
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 14
 */
public final class SHA256Digestor extends DigestorBase {

	@Override
	public final String getAlgorithm() {
		return "SHA-256";
	}
}
