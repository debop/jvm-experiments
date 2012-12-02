package kr.kth.commons.cryptography.disgest;

/**
 * MD5 알고리즘을 이용한 Digestor
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 14
 */
public final class MD5Digestor extends DigestorBase {

	@Override
	public final String getAlgorithm() {
		return "MD5";
	}
}
