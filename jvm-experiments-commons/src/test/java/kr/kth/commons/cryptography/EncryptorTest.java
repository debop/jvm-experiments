package kr.kth.commons.cryptography;

import kr.kth.commons.cryptography.symmetric.ISymmetricByteEncryptor;
import kr.kth.commons.spring3.Spring;
import kr.kth.commons.spring3.configuration.EncryptorConfiguration;
import kr.kth.commons.tools.StringTool;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

/**
 * 설명을 추가하세요.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 18
 */
@Slf4j
public class EncryptorTest {

	private static final String PLAIN_TEXT = "동해물과 백두산이 마르고 닳도록~ Hello World! 1234567890";

	@BeforeClass
	public static void beforeClass() {
		if (Spring.isNotInitialized())
			Spring.initByAnnotatedClasses(EncryptorConfiguration.class);
	}

	@Test
	public void byteEncryptorTest() {
		List<ISymmetricByteEncryptor> byteEncryptors = Spring.getBeansByType(ISymmetricByteEncryptor.class);

		for (ISymmetricByteEncryptor encryptor : byteEncryptors) {
			if (log.isDebugEnabled())
				log.debug("Encryptor=[{}] 를 테스트합니다.", encryptor.getClass().getSimpleName());

			encryptor.setPassword("debop");

			byte[] encryptedBytes = encryptor.encrypt(StringTool.getUtf8Bytes(PLAIN_TEXT));
			byte[] decryptedBytes = encryptor.decrypt(encryptedBytes);

			Assert.assertEquals(PLAIN_TEXT, StringTool.getUtf8String(decryptedBytes));
		}
	}
}
