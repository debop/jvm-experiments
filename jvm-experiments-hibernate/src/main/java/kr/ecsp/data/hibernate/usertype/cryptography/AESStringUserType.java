package kr.ecsp.data.hibernate.usertype.cryptography;

import kr.escp.commons.cryptography.symmetric.AESEncryptor;
import kr.escp.commons.cryptography.symmetric.SymmetricEncryptor;

/**
 * AES 알고리즘({@link AESEncryptor})을 이용하여 속성 값을 암호화하여 16진수 문자열로 저장합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
public class AESStringUserType extends AbstractSymmetricEncryptStringUserType {

	private static final SymmetricEncryptor encryptor = new AESEncryptor();

	@Override
	public SymmetricEncryptor getEncryptor() {
		return encryptor;
	}
}
