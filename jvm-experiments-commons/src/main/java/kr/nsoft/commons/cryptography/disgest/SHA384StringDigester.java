package kr.nsoft.commons.cryptography.disgest;

import lombok.extern.slf4j.Slf4j;

/**
 * SHA-384 알고리즘을 이용하여, 문자열을 Digest 합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 18
 */
@Slf4j
public class SHA384StringDigester extends StringDigesterBase {

    @Override
    public final String getAlgorithm() {
        return "SHA-384";
    }
}
