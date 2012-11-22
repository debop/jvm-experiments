package kr.escp.commons.core.compress;

/**
 * 설명을 추가하세요.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 12
 */
public interface Compressor {

	int BUFFER_SIZE = 4096;

	/**
	 * 정보를 압축합니다.
	 *
	 * @param plain 압축할 데이타
	 * @return 압축한 바이트 배열
	 */
	byte[] compress(byte[] plain);

	/**
	 * 압축된 정보를 복원합니다.
	 *
	 * @param compressed 압축된 데이타 정보
	 * @return 압축 해제한 바이트 배열
	 */
	byte[] decompress(byte[] compressed);
}