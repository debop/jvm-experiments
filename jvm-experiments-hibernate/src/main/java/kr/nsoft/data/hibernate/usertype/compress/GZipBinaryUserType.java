package kr.nsoft.data.hibernate.usertype.compress;

import kr.nsoft.commons.compress.GZipCompressor;
import kr.nsoft.commons.compress.ICompressor;

/**
 * GZip 알고리즘 ({@link GZipCompressor} 으로 이진 데이터 값을 압축하여 Binary로 저장합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
public class GZipBinaryUserType extends AbstractCompressedBinaryUserType {

    private static final ICompressor compressor = new GZipCompressor();

    @Override
    public ICompressor getCompressor() {
        return compressor;
    }
}