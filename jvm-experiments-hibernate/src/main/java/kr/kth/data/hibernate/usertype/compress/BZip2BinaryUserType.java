package kr.kth.data.hibernate.usertype.compress;

import kr.kth.commons.compress.BZip2Compressor;
import kr.kth.commons.compress.Compressor;

/**
 * GZip 알고리즘 ({@link BZip2Compressor} 으로 이진 데이터 값을 압축하여 Binary로 저장합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
public class BZip2BinaryUserType extends AbstractCompressedBinaryUserType {

	private static final Compressor compressor = new BZip2Compressor();

	@Override
	public Compressor getCompressor() {
		return compressor;
	}
}
