package kr.kth.commons.cryptography.disgest;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.digest.StandardStringDigester;

/**
 * 설명을 추가하세요.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 18
 */
@Slf4j
public abstract class StringDigesterBase implements IStringDigester {

	private final StandardStringDigester standardStringDigester;

	public StringDigesterBase() {
		this(5);
	}

	public StringDigesterBase(int iterations) {
		standardStringDigester = new StandardStringDigester();
		standardStringDigester.setAlgorithm(getAlgorithm());
		standardStringDigester.setIterations(iterations);

		if (log.isDebugEnabled())
			log.debug("문자열을 암호화하는 IStringDigester 인스턴스를 생성했습니다. algorithm=[{}], iteration=[{}]",
			          getAlgorithm(), iterations);
	}

	abstract public String getAlgorithm();

	@Override
	public boolean isInitialized() {
		return standardStringDigester.isInitialized();
	}

	@Override
	public String digest(String message) {
		return standardStringDigester.digest(message);
	}

	@Override
	public boolean matches(String message, String digest) {
		return standardStringDigester.matches(message, digest);
	}
}
