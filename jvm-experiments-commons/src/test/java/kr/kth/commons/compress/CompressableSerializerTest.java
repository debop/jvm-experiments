package kr.kth.commons.compress;

import kr.kth.commons.spring3.Spring;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

/**
 * 설명을 추가하세요.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 17
 */
@Slf4j
public class CompressableSerializerTest {

    @BeforeClass
    public static void beforeClass() {
        if (Spring.isNotInitialized())
            Spring.initByPackages("kr.kth.commons.spring3.configuration");
    }

    @Test
    public void shouldBeExistsCompressors() {
        Map<String, ICompressor> compressorMap = Spring.getBeansOfType(ICompressor.class);
        Assert.assertNotNull(compressorMap);
        Assert.assertTrue(compressorMap.size() > 0);
    }

    @Test
    public void compressBinarySerializer() {
    }
}
