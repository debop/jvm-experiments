package kr.nsoft.data.hibernate.usertype.cryptography;

import kr.nsoft.commons.cryptography.symmetric.ISymmetricByteEncryptor;
import kr.nsoft.commons.cryptography.symmetric.TripleDESByteEncryptor;

/**
 * TripleDES 알고리즘을 이용한 {@link TripleDESByteEncryptor} 를 이용하여, 속성 값을 암호화하여 저장합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
public class TripleDESStringUserType extends AbstractSymmetricEncryptStringUserType {

    private static final ISymmetricByteEncryptor encryptor = new TripleDESByteEncryptor();

    @Override
    public ISymmetricByteEncryptor getEncryptor() {
        return encryptor;
    }
}
