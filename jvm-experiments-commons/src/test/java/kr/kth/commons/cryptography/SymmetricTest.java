package kr.kth.commons.cryptography;

import kr.kth.commons.cryptography.symmetric.ISymmetricEncryptor;
import kr.kth.commons.spring3.Spring;
import kr.kth.commons.spring3.configuration.EncryptorConfiguration;
import kr.kth.commons.tools.StringTool;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.security.Security;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 설명을 추가하세요.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 11
 */
@Slf4j
public class SymmetricTest {

	private static final String PLAIN_TEXT = "동해물과 백두산이 마르고 닳도록, Hello Worlds!!!";

	@BeforeClass
	public static void beforeClass() {
		if (Spring.isNotInitialized())
			Spring.initByAnnotatedClasses(EncryptorConfiguration.class);
	}

	@Test
	public void loadCiphers() {
		final List<String> algos = new ArrayList<String>(Security.getAlgorithms("Cipher"));
		Collections.sort(algos);

		for (Object algorithm : algos) {
			log.debug("Symethric Algorithm=[{}]", algorithm);
		}
	}

	@Test
	public void encryptDecrypt() {
		List<ISymmetricEncryptor> encryptors = Spring.getBeansByType(ISymmetricEncryptor.class);

		for (ISymmetricEncryptor encrytor : encryptors) {
			assertEncrypt(encrytor, PLAIN_TEXT);
		}
	}

	private void assertEncrypt(ISymmetricEncryptor encryptor, final String text) {
		byte[] cipher = encryptor.encrypt(StringTool.getUtf8Bytes(text));
		String decrypted = StringTool.getUtf8String(encryptor.decrypt(cipher));

		Assert.assertEquals(text, decrypted);

	}
}
