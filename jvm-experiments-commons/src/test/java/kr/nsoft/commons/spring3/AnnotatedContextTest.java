package kr.nsoft.commons.spring3;

import kr.nsoft.commons.AbstractTest;
import kr.nsoft.commons.compress.GZipCompressor;
import kr.nsoft.commons.compress.ICompressor;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * kr.nsoft.commons.spring3.AnnotatedContextTest
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 2.
 */
public class AnnotatedContextTest extends AbstractTest {

    @Override
    protected void onAfter() {
        if (SpringTool.isInitialized())
            SpringTool.reset();
    }

    @Test
    public void initByAnnotatedClasses() {
        SpringTool.initByAnnotatedClasses(AnnotatedBeanConfig.class);
        assertTrue(SpringTool.isInitialized());

        ICompressor compressor = (ICompressor) SpringTool.getBean("defaultCompressor");
        assertNotNull(compressor);
        assertTrue(compressor instanceof GZipCompressor);
    }

    @Test
    public void initByPackages() {
        SpringTool.initByPackages(AnnotatedBeanConfig.class.getPackage().getName());
        assertTrue(SpringTool.isInitialized());

        String[] beanNames = SpringTool.getBeanNamesForType(ICompressor.class, true, true);

        assertTrue(beanNames.length > 0);

        ICompressor compressor = (ICompressor) SpringTool.getBean("defaultCompressor");
        assertNotNull(compressor);
        assertTrue(compressor instanceof GZipCompressor);
    }
}
