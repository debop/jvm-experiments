package kr.nsoft.commons.spring.configuration;

import kr.nsoft.commons.cryptography.disgest.*;
import kr.nsoft.commons.cryptography.symmetric.DESByteEncryptor;
import kr.nsoft.commons.cryptography.symmetric.RC2ByteEncryptor;
import kr.nsoft.commons.cryptography.symmetric.TripleDESByteEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Encriptor 를 구현한 클래스들을 Springs Bean 으로 제공하는 Anntated Configuration 입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 17
 */
@Configuration
public class EncryptorConfiguration {

    @Bean(name = "md5StringDigester")
    public MD5StringDigester md5StringDigester() {
        return new MD5StringDigester();
    }

    @Bean(name = "sha1StringDigester")
    public SHA1StringDigester sha1StringDigester() {
        return new SHA1StringDigester();
    }

    @Bean(name = "sha256StringDigester")
    public SHA256StringDigester sha256StringDigester() {
        return new SHA256StringDigester();
    }

    @Bean(name = "sha384StringDigester")
    public SHA384StringDigester sha384StringDigester() {
        return new SHA384StringDigester();
    }

    @Bean(name = "sha512StringDigester")
    public SHA512StringDigester sha512StringDigester() {
        return new SHA512StringDigester();
    }


    @Bean(name = "desByteEncryptor")
    public DESByteEncryptor desByteEncryptor() {
        return new DESByteEncryptor();
    }

    @Bean(name = "tripleByteEncryptor")
    public TripleDESByteEncryptor tripleDESByteEncryptor() {
        return new TripleDESByteEncryptor();
    }

    @Bean(name = "rc2ByteEncryptor")
    public RC2ByteEncryptor rc2ByteEncryptor() {
        return new RC2ByteEncryptor();
    }
}
