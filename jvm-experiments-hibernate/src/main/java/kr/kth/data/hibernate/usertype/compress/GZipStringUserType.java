package kr.kth.data.hibernate.usertype.compress;

import kr.kth.commons.compress.GZipCompressor;
import kr.kth.commons.compress.ICompressor;

/**
 * GZip 알고리즘 ({@link GZipCompressor} 으로 문자열 속성 값을 압축하여 Binary로 저장합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
public class GZipStringUserType extends AbstractCompressedStringUserType {

    private static final ICompressor compressor = new GZipCompressor();

    @Override
    public ICompressor getCompressor() {
        return compressor;
    }
}
