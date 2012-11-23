package kr.escp.commons.cryptography.symmetric;

import lombok.extern.slf4j.Slf4j;

/**
 * DES 대칭형 암호화 알고리즘을 이용한 암호화 클래스
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 14
 */
@Slf4j
public final class DESEncryptor extends SymmetricEncryptorBase {

	public static final String Algorithm = "DES";

	public DESEncryptor() {
		super();
	}

	public DESEncryptor(String password) {
		super(password);
	}

	@Override
	public String getAlgorithm() {
		return Algorithm;
	}

	@Override
	public int getKeySize() {
		return 56;
	}
}
