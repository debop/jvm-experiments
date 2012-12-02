package kr.kth.commons.cryptography.symmetric;

import lombok.extern.slf4j.Slf4j;

/**
 * RC2 대칭형 암호화 알고리즘을 이용한 암호화 클래스
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 14
 */
@Slf4j
public final class RC2Encryptor extends SymmetricEncryptorBase {

	public static final String Algorithm = "RC2";

	public RC2Encryptor() {
		super();
	}

	public RC2Encryptor(String password) {
		super(password);
	}

	@Override
	public String getAlgorithm() {
		return Algorithm;
	}

	@Override
	public int getKeySize() {
		return 128;
	}
}
