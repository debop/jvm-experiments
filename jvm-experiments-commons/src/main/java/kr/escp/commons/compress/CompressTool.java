package kr.escp.commons.compress;

import com.google.common.base.Preconditions;
import kr.escp.commons.BinaryStringFormat;
import kr.escp.commons.tools.StreamTool;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.OutputStream;

import static kr.escp.commons.Guard.shouldNotBeNull;
import static kr.escp.commons.tools.StreamTool.toByteArray;
import static kr.escp.commons.tools.StringTool.*;

/**
 * 압축 툴
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 13
 */
@Slf4j
public class CompressTool {

	@Getter(lazy = true)
	private static final byte[] buffer = new byte[Compressor.BUFFER_SIZE];

	public static String compressString(Compressor compressor, String plainText) throws Exception {
		return compressString(compressor, plainText, BinaryStringFormat.HexDecimal);
	}

	public static String compressString(Compressor compressor,
	                                    String plainText,
	                                    BinaryStringFormat stringFormat) throws Exception {
		Preconditions.checkNotNull(compressor, "compressor should not be null.");
		if (isEmpty(plainText))
			return "";

		if (log.isDebugEnabled())
			log.debug("다음 문자열을 압축합니다... plainText=[{}]", ellipsisChar(plainText, 80));

		byte[] compressedBytes = compressor.compress(getUtf8Bytes(plainText));
		return getStringFromBytes(compressedBytes, stringFormat);
	}

	public static String decompressString(Compressor compressor,
	                                      String compressedText) throws Exception {
		return decompressString(compressor,
		                        compressedText,
		                        BinaryStringFormat.HexDecimal);
	}

	public static String decompressString(Compressor compressor,
	                                      String compressedText,
	                                      BinaryStringFormat stringFormat) throws Exception {
		shouldNotBeNull(compressor, "compressor");
		if (isEmpty(compressedText))
			return "";

		if (log.isDebugEnabled())
			log.debug("압축된 문자열을 복원합니다... compressedText=" +
				          ellipsisChar(compressedText, 80));

		byte[] plainBytes = compressor.decompress(getBytesFromString(compressedText, stringFormat));

		String plainText = getUtf8String(plainBytes);

		if (log.isDebugEnabled())
			log.debug("압축 복원한 문자열입니다... plainText=" + ellipsisChar(plainText, 80));

		return plainText;
	}

	public static OutputStream compressStream(Compressor compressor,
	                                          InputStream inputStream) throws Exception {
		shouldNotBeNull(compressor, "compressor");
		shouldNotBeNull(inputStream, "inputStream");

		byte[] plainBytes = toByteArray(inputStream);
		byte[] compressedBytes = compressor.compress(plainBytes);

		return StreamTool.toOutputStream(compressedBytes);
	}

	public static OutputStream decompressStream(Compressor compressor,
	                                            InputStream inputStream) throws Exception {
		shouldNotBeNull(compressor, "compressor");
		shouldNotBeNull(inputStream, "inputStream");

		byte[] compressedBytes = toByteArray(inputStream);
		byte[] plainBytes = compressor.decompress(compressedBytes);

		return StreamTool.toOutputStream(plainBytes);
	}
}
