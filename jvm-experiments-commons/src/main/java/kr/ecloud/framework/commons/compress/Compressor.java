package kr.ecloud.framework.commons.compress;

/**
 * 데이터의 압축/복원을 수행하는 Compressor 입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 16
 */
public interface Compressor {

	byte[] compress(byte[] input);

	byte[] decompress(byte[] input);
}
