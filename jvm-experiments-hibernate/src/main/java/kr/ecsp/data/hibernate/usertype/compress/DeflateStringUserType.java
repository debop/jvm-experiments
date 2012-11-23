package kr.ecsp.data.hibernate.usertype.compress;

import kr.escp.commons.compress.Compressor;
import kr.escp.commons.compress.DeflateCompressor;

/**
 * Deflate 알고리즘 ({@link DeflateCompressor} 으로 문자열 속성 값을 압축하여 Binary로 저장합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
public class DeflateStringUserType extends AbstractCompressedStringUserType {

	private static final Compressor compressor = new DeflateCompressor();

	@Override
	public Compressor getCompressor() {
		return compressor;
	}
}
