package kr.kth.commons.spring3.configuration;

import kr.kth.commons.cryptography.symmetric.AESEncryptor;
import kr.kth.commons.cryptography.symmetric.DESEncryptor;
import kr.kth.commons.cryptography.symmetric.RC2Encryptor;
import kr.kth.commons.cryptography.symmetric.TripleDESEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Encriptor 를 구현한 클래스들을 Spring Bean 으로 제공하는 Anntated Configuration 입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 17
 */
@Configuration
public class EncryptorConfiguration {

	@Bean(name = "aesEncryptor")
	public AESEncryptor aesEncryptor() {
		return new AESEncryptor();
	}

	@Bean(name = "desEncryptor")
	public DESEncryptor desEncryptor() {
		return new DESEncryptor();
	}

	@Bean(name = "rc2Encryptor")
	public RC2Encryptor rc2Encryptor() {
		return new RC2Encryptor();
	}

	@Bean(name = "tripleDESEncryptor")
	public TripleDESEncryptor tripleDESEncryptor() {
		return new TripleDESEncryptor();
	}
}
