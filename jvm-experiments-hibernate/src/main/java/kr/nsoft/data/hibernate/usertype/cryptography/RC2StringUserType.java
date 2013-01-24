package kr.nsoft.data.hibernate.usertype.cryptography;

import kr.nsoft.commons.cryptography.symmetric.ISymmetricByteEncryptor;
import kr.nsoft.commons.cryptography.symmetric.RC2ByteEncryptor;

/**
 * RC2 알고리즘을 이용한 {@link RC2ByteEncryptor} 를 이용하여, 속성 값을 16진수 문자열로 암호화하여 저장합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
public class RC2StringUserType extends AbstractSymmetricEncryptStringUserType {

    private static final ISymmetricByteEncryptor encryptor = new RC2ByteEncryptor();

    @Override
    public ISymmetricByteEncryptor getEncryptor() {
        return encryptor;
    }
}
