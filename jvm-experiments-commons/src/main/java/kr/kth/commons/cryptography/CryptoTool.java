package kr.kth.commons.cryptography;

import lombok.extern.slf4j.Slf4j;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 설명을 추가하세요.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 14
 */
@Slf4j
public class CryptoTool {

    private CryptoTool() {
    }

    private static final String RandomNumberGeneration = "SHA1PRNG";

    public static byte[] getRandomBytes(int numBytes) {

        SecureRandom random = null;
        try {
            random = SecureRandom.getInstance(RandomNumberGeneration);
        } catch (NoSuchAlgorithmException e) {
            if (log.isErrorEnabled())
                log.error("해당 난수 발생 알고리즘을 찾을 수 없습니다. algorithm=" + RandomNumberGeneration);
            throw new RuntimeException(e);
        }

        byte[] bytes = new byte[numBytes];
        random.nextBytes(bytes);
        return bytes;
    }
}
