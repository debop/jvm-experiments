package kr.nsoft.data.hibernate.usertype.compress;

import kr.nsoft.commons.compress.DeflateCompressor;
import kr.nsoft.commons.compress.ICompressor;

/**
 * Deflate 알고리즘 ({@link DeflateCompressor} 으로 문자열 속성 값을 압축하여 Binary로 저장합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
public class DeflateStringUserType extends AbstractCompressedStringUserType {

    private static final ICompressor compressor = new DeflateCompressor();

    @Override
    public ICompressor getCompressor() {
        return compressor;
    }
}
