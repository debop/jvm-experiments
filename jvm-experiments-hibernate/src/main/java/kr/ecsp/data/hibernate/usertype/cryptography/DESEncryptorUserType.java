package kr.ecsp.data.hibernate.usertype.cryptography;

import kr.escp.commons.cryptography.symmetric.DESEncryptor;
import kr.escp.commons.cryptography.symmetric.SymmetricEncryptor;

/**
 * DES 알고리즘({@link DESEncryptor})을 이용하여 속성 값을 암호화하여 16진수 문자열로 저장합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
public class DESEncryptorUserType extends AbstractSymmetricEncryptStringUserType {

	private static final SymmetricEncryptor encryptor = new DESEncryptor();

	@Override
	public SymmetricEncryptor getEncryptor() {
		return encryptor;
	}
}
