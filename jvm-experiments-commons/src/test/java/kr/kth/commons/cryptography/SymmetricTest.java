package kr.kth.commons.cryptography;

import lombok.extern.slf4j.Slf4j;
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

	@Test
	public void loadCiphers() {
		final List<String> algos = new ArrayList<String>(Security.getAlgorithms("Cipher"));
		Collections.sort(algos);

		for(Object algorithm : algos) {
			log.debug("Symethric Algorithm=[{}]", algorithm);
		}
	}
}
