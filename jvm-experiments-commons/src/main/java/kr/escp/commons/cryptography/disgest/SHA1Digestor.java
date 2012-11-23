package kr.escp.commons.cryptography.disgest;

/**
 * SHA-1 알고리즘을 이용한 Digestor
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 14
 */
public final class SHA1Digestor extends DigestorBase {


	@Override
	public final String getAlgorithm() {
		return "SHA-1";
	}
}
