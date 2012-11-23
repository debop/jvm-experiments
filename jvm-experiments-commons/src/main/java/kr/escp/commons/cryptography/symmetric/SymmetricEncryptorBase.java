package kr.escp.commons.cryptography.symmetric;

import com.google.common.base.Objects;
import kr.escp.commons.Guard;
import kr.escp.commons.tools.ArrayTool;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * 대칭형 암호화 클래스의 기본 클래스입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 14
 */
@Slf4j
public abstract class SymmetricEncryptorBase implements SymmetricEncryptor {

	protected static final String DEFAULT_PASSWD = "pudding.pudding.commons.core";

	@Getter
	protected final String password;

	public SymmetricEncryptorBase() {
		this(DEFAULT_PASSWD);
	}

	public SymmetricEncryptorBase(String password) {
		Guard.shouldNotBeEmpty(password, "password");
		this.password = password;
	}

	abstract public String getAlgorithm();

	abstract public int getKeySize();

	protected Key generateKey() throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance(getAlgorithm());
		kgen.init(getKeySize());
		SecretKey skey = kgen.generateKey();

		return new SecretKeySpec(skey.getEncoded(), getAlgorithm());
	}

	protected byte[] doEncrypt(byte[] plainBytes) throws Exception {
		Cipher cipher = Cipher.getInstance(getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, generateKey());

		return cipher.doFinal(plainBytes);
	}

	protected byte[] doDecrypt(byte[] cipherBytes) throws Exception {
		Cipher cipher = Cipher.getInstance(getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, generateKey());

		return cipher.doFinal(cipherBytes);
	}


	@Override
	public byte[] encrypt(byte[] plainBytes) throws Exception {

		if (ArrayTool.isEmpty(plainBytes))
			return ArrayTool.EmptyByteArray;

		if (log.isDebugEnabled())
			log.debug("암호화를 수행합니다...");

		return doEncrypt(plainBytes);
	}

	@Override
	public byte[] decrypt(byte[] cipherBytes) throws Exception {
		if (ArrayTool.isEmpty(cipherBytes))
			return ArrayTool.EmptyByteArray;

		if (log.isDebugEnabled())
			log.debug("복호화를 수행합니다...");

		return doDecrypt(cipherBytes);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
		              .add("algorithm", getAlgorithm())
		              .add("keySize", getKeySize())
		              .toString();
	}
}
