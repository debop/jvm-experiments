package kr.nsoft.commons.cryptography;

import kr.nsoft.commons.cryptography.disgest.IStringDigester;
import kr.nsoft.commons.spring.Springs;
import kr.nsoft.commons.spring.configuration.EncryptorConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

/**
 * 설명을 추가하세요.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 18
 */
@Slf4j
public class StringDiagesterTest {

    private static final String MESSAGE = "kth1234!";

    @BeforeClass
    public static void beforeClass() {
        if (Springs.isNotInitialized())
            Springs.initByAnnotatedClasses(EncryptorConfiguration.class);
    }

    @Test
    public void stringDigesterTest() {
        List<IStringDigester> diagesters = Springs.getBeansByType(IStringDigester.class);

        for (IStringDigester digester : diagesters) {
            if (log.isDebugEnabled())
                log.debug("Digest message by [{}]", digester.getAlgorithm());
            String digest = digester.digest(MESSAGE);
            Assert.assertTrue(digester.matches(MESSAGE, digest));
        }
    }
}
