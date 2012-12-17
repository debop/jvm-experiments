package kr.kth.commons.compress;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import kr.kth.commons.AbstractTest;
import kr.kth.commons.spring3.Spring;
import kr.kth.commons.spring3.configuration.CompressorConfiguration;
import kr.kth.commons.spring3.configuration.SerializerConfiguration;
import kr.kth.commons.tools.StringTool;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collection;
import java.util.Map;
import java.util.Random;

/**
 * {@link Compressor} TestCase
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 12
 */
@Slf4j
public class CompressorTest extends AbstractTest {

	private static final int TextCount = 10;
	protected static final String text = "동해물과 백두산이 마르고 닳도록, 하느님이 부우하사 우리나라 만세~ Hello world!";
	protected static String plainText;

	private static Collection<Compressor> compressors;

	@BeforeClass
	public static void BeforeClass() {

		if (Spring.isNotInitialized())
			Spring.initByAnnotatedClasses(CompressorConfiguration.class,
			                              SerializerConfiguration.class);

		Map<String, Compressor> compressorMap = Spring.getBeansOfType(Compressor.class);
		compressors = compressorMap.values();


		Random random = new Random(System.currentTimeMillis());
		StringBuilder builder = new StringBuilder();
		byte[] bytes = new byte[1024];

		for (int i = 0; i < TextCount; i++) {
			random.nextBytes(bytes);
			builder.append(StringTool.replicate(text, i));
			builder.append(StringTool.getHexString(bytes));
			builder.append(StringTool.replicate(StringTool.reverse(text), i));
		}
		plainText = builder.toString();
	}

	@BenchmarkOptions(concurrency = BenchmarkOptions.CONCURRENCY_AVAILABLE_CORES,
	                  benchmarkRounds = 1,
	                  warmupRounds = 1)
	@Test
	public void testCompressors() {
		for (Compressor compressor : compressors)
			try {
				compressAndDecompress(compressor);
			} catch (Exception e) {
				log.error("compressor=" + compressor, e);
			}
	}

	private static void compressAndDecompress(Compressor compressor) {

		if (log.isDebugEnabled())
			log.debug("압축/복원 테스트를 시작합니다... compressor=" + compressor);

		try {
			byte[] plainBytes = StringTool.getUtf8Bytes(plainText);
			byte[] compressed = compressor.compress(plainBytes);
			Assert.assertNotNull(compressed);

			byte[] decompressedBytes = compressor.decompress(compressed);
			Assert.assertNotNull(plainBytes);

			String decompressedText = StringTool.getUtf8String(decompressedBytes);
			Assert.assertNotNull(decompressedText);

			Assert.assertEquals(plainText, decompressedText);

		} catch (Exception e) {
			if (log.isErrorEnabled())
				log.error("압축/복원 테스트 실패 compressor=" + compressor, e);
		}
	}
}
