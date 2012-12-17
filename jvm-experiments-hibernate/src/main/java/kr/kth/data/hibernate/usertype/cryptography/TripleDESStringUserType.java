package kr.kth.data.hibernate.usertype.cryptography;

import kr.kth.commons.cryptography.symmetric.ISymmetricEncryptor;
import kr.kth.commons.cryptography.symmetric.TripleDESEncryptor;

/**
 * TripleDES 알고리즘을 이용한 {@link TripleDESEncryptor} 를 이용하여, 속성 값을 암호화하여 저장합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
public class TripleDESStringUserType extends AbstractSymmetricEncryptStringUserType {

	private static final ISymmetricEncryptor encryptor = new TripleDESEncryptor();

	@Override
	public ISymmetricEncryptor getEncryptor() {
		return encryptor;
	}
}
