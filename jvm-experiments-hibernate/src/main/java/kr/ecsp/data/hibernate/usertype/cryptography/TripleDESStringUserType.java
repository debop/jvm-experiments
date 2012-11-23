package kr.ecsp.data.hibernate.usertype.cryptography;

import kr.escp.commons.cryptography.symmetric.SymmetricEncryptor;
import kr.escp.commons.cryptography.symmetric.TripleDESEncryptor;

/**
 * TripleDES 알고리즘을 이용한 {@link TripleDESEncryptor} 를 이용하여, 속성 값을 암호화하여 저장합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
public class TripleDESStringUserType extends AbstractSymmetricEncryptStringUserType {

	private static final SymmetricEncryptor encryptor = new TripleDESEncryptor();

	@Override
	public SymmetricEncryptor getEncryptor() {
		return encryptor;
	}
}
