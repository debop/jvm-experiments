package kr.nsoft.commons.compress;

import kr.nsoft.commons.BinaryStringFormat;
import kr.nsoft.commons.Guard;
import kr.nsoft.commons.parallelism.AsyncTool;
import kr.nsoft.commons.tools.StreamTool;
import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import static kr.nsoft.commons.Guard.shouldNotBeNull;
import static kr.nsoft.commons.tools.StreamTool.toByteArray;
import static kr.nsoft.commons.tools.StringTool.*;

/**
 * 압축 툴
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 13
 */
public abstract class CompressTool {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CompressTool.class);

    private CompressTool() {}

    @Getter
    private static final byte[] buffer = new byte[ICompressor.BUFFER_SIZE];

    public static String compressString(ICompressor compressor,
                                        String plainText) {
        return compressString(compressor,
                              plainText,
                              BinaryStringFormat.HexDecimal);
    }

    public static String compressString(ICompressor compressor,
                                        String plainText,
                                        BinaryStringFormat stringFormat) {
        Guard.shouldNotBeNull(compressor, "compressor");
        if (isEmpty(plainText))
            return "";

        if (log.isDebugEnabled())
            log.debug("다음 문자열을 압축합니다... plainText=[{}]", ellipsisChar(plainText, 80));

        byte[] compressedBytes = compressor.compress(getUtf8Bytes(plainText));
        return getStringFromBytes(compressedBytes, stringFormat);
    }

    public static Future<String> compressStringAsync(final ICompressor compressor,
                                                     final String plainText) {
        return compressStringAsync(compressor,
                                   plainText,
                                   BinaryStringFormat.HexDecimal);
    }

    public static Future<String> compressStringAsync(final ICompressor compressor,
                                                     final String plainText,
                                                     final BinaryStringFormat stringFormat) {
        Guard.shouldNotBeNull(compressor, "compressor");
        if (isEmpty(plainText)) {
            AsyncTool.getTaskHasResult("");
        }

        if (log.isDebugEnabled())
            log.debug("다음 문자열을 압축합니다... plainText=[{}]", ellipsisChar(plainText, 80));

        return
                AsyncTool.startNew(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        byte[] compressedBytes = compressor.compress(getUtf8Bytes(plainText));
                        return getStringFromBytes(compressedBytes, stringFormat);
                    }
                });
    }

    public static String decompressString(ICompressor compressor,
                                          String compressedText) {
        return decompressString(compressor,
                                compressedText,
                                BinaryStringFormat.HexDecimal);
    }

    public static String decompressString(ICompressor compressor,
                                          String compressedText,
                                          BinaryStringFormat stringFormat) {
        shouldNotBeNull(compressor, "compressor");
        if (isEmpty(compressedText))
            return "";

        if (log.isDebugEnabled())
            log.debug("압축된 문자열을 복원합니다... compressedText=[{}]", ellipsisChar(compressedText, 80));

        byte[] plainBytes = compressor.decompress(getBytesFromString(compressedText, stringFormat));

        String plainText = getUtf8String(plainBytes);

        if (log.isDebugEnabled())
            log.debug("압축 복원한 문자열입니다... plainText=[{}]", ellipsisChar(plainText, 80));

        return plainText;
    }

    public static Future<String> decompressStringAsync(final ICompressor compressor,
                                                       final String compressedText) {
        return decompressStringAsync(compressor,
                                     compressedText,
                                     BinaryStringFormat.HexDecimal);
    }

    public static Future<String> decompressStringAsync(final ICompressor compressor,
                                                       final String compressedText,
                                                       final BinaryStringFormat stringFormat) {
        shouldNotBeNull(compressor, "compressor");

        if (isEmpty(compressedText)) {
            return AsyncTool.getTaskHasResult("");
        }

        if (log.isDebugEnabled())
            log.debug("압축된 문자열을 복원합니다... compressedText=" +
                              ellipsisChar(compressedText, 80));
        return
                AsyncTool.startNew(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        byte[] plainBytes = compressor.decompress(getBytesFromString(compressedText,
                                                                                     stringFormat));
                        String plainText = getUtf8String(plainBytes);

                        if (log.isDebugEnabled())
                            log.debug("압축 복원한 문자열입니다... plainText=[{}]", ellipsisChar(plainText, 80));

                        return plainText;
                    }
                });

    }

    public static OutputStream compressStream(ICompressor compressor,
                                              InputStream inputStream) throws IOException {
        shouldNotBeNull(compressor, "compressor");
        shouldNotBeNull(inputStream, "inputStream");

        byte[] plainBytes = toByteArray(inputStream);
        byte[] compressedBytes = compressor.compress(plainBytes);

        return StreamTool.toOutputStream(compressedBytes);
    }

    public static OutputStream decompressStream(ICompressor compressor,
                                                InputStream inputStream) throws IOException {
        shouldNotBeNull(compressor, "compressor");
        shouldNotBeNull(inputStream, "inputStream");

        byte[] compressedBytes = toByteArray(inputStream);
        byte[] plainBytes = compressor.decompress(compressedBytes);

        return StreamTool.toOutputStream(plainBytes);
    }

    public static Future<OutputStream> compressStreamAsync(final ICompressor compressor,
                                                           final InputStream inputStream) {
        shouldNotBeNull(compressor, "compressor");
        shouldNotBeNull(inputStream, "inputStream");

        return
                AsyncTool.startNew(new Callable<OutputStream>() {
                    @Override
                    public OutputStream call() throws Exception {
                        return compressStream(compressor, inputStream);
                    }
                });
    }

    public static Future<OutputStream> decompressStreamAsync(final ICompressor compressor,
                                                             final InputStream inputStream) {
        shouldNotBeNull(compressor, "compressor");
        shouldNotBeNull(inputStream, "inputStream");

        return AsyncTool.startNew(new Callable<OutputStream>() {
            @Override
            public OutputStream call() throws Exception {
                byte[] compressedBytes = toByteArray(inputStream);
                byte[] plainBytes = compressor.decompress(compressedBytes);

                return StreamTool.toOutputStream(plainBytes);
            }
        });
    }
}
