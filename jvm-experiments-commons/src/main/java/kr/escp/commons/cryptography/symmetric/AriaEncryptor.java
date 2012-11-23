package kr.escp.commons.cryptography.symmetric;


/**
 * ARIA 알고리즘을 이용한 대칭형 암호화기<br/>
 * 참고 : <a href="http://seed.kisa.or.kr/kor/aria/aria.jsp">ARIA 알고리즘</a>
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 14
 */
public final class AriaEncryptor extends SymmetricEncryptorBase {

	@Override
	public final String getAlgorithm() {
		return "Aria";
	}

	@Override
	public int getKeySize() {
		return 128;
	}

	@Override
	protected byte[] doEncrypt(byte[] plainBytes) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected byte[] doDecrypt(byte[] cipherBytes) {
		throw new UnsupportedOperationException();
	}


}
