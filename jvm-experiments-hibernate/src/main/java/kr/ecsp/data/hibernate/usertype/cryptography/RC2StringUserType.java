package kr.ecsp.data.hibernate.usertype.cryptography;

import kr.escp.commons.cryptography.symmetric.RC2Encryptor;
import kr.escp.commons.cryptography.symmetric.SymmetricEncryptor;

/**
 * RC2 알고리즘을 이용한 {@link RC2Encryptor} 를 이용하여, 속성 값을 16진수 문자열로 암호화하여 저장합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
public class RC2StringUserType extends AbstractSymmetricEncryptStringUserType {

	// TODO: 비밀번호를 지정할 수 있는데, HBM에서 정의할 수 있는지 알아보자!!!
	//
	private static final SymmetricEncryptor encryptor = new RC2Encryptor();

	@Override
	public SymmetricEncryptor getEncryptor() {
		return encryptor;
	}
}
