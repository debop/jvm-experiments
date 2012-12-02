package kr.kth.commons.cryptography.disgest;

/**
 * Hash Algorithm 을 이용하여, 데이터를 암호화합니다. (복호화는 불가능합니다.)
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 14
 */
public interface Digestor {

	String getAlgorithm();

	byte[] digest(byte[] plainBytes) throws Exception;
}
