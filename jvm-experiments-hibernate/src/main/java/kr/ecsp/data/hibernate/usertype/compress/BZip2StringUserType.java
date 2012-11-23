package kr.ecsp.data.hibernate.usertype.compress;

import kr.escp.commons.compress.BZip2Compressor;
import kr.escp.commons.compress.Compressor;

/**
 * BZip2 알고리즘 ({@link BZip2Compressor} 으로 문자열 속성 값을 압축하여 Binary로 저장합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
public class BZip2StringUserType extends AbstractCompressedStringUserType {

	private static final Compressor compressor = new BZip2Compressor();

	@Override
	public Compressor getCompressor() {
		return compressor;
	}
}
