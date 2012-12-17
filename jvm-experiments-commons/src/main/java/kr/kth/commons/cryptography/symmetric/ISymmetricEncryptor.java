package kr.kth.commons.cryptography.symmetric;

/**
 * 대칭형 암호화를 수행하는 Encryptor 의 인터페이스
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 14
 */
public interface ISymmetricEncryptor {

	/**
	 * 암호화 알고리즘에 사용되는 키 크기
	 */
	int getKeySize();

	/**
	 * 알고리즘 종류
	 */
	String getAlgorithm();

	/**
	 * 일반 데이터를 암호화합니다.
	 */
	byte[] encrypt(byte[] plainBytes);

	/**
	 * 암호화된 데이터를 복호화합니다.
	 */
	byte[] decrypt(byte[] cipherBytes);
}
