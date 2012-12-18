package kr.kth.commons.spring3;

import kr.kth.commons.AbstractTest;
import kr.kth.commons.compress.GZipCompressor;
import kr.kth.commons.compress.ICompressor;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * kr.kth.commons.spring3.AnnotatedContextTest
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 2.
 */
public class AnnotatedContextTest extends AbstractTest {

	@Override
	protected void onAfter() {
		if (Spring.isInitialized())
			Spring.reset();
	}

	@Test
	public void initByAnnotatedClasses() {
		Spring.initByAnnotatedClasses(AnnotatedBeanConfig.class);
		assertTrue(Spring.isInitialized());

		ICompressor compressor = (ICompressor) Spring.getBean("defaultCompressor");
		assertNotNull(compressor);
		assertTrue(compressor instanceof GZipCompressor);
	}

	@Test
	public void initByPackages() {
		Spring.initByPackages(AnnotatedBeanConfig.class.getPackage().getName());
		assertTrue(Spring.isInitialized());

		String[] beanNames = Spring.getBeanNamesForType(ICompressor.class, true, true);

		assertTrue(beanNames.length > 0);

		ICompressor compressor = (ICompressor) Spring.getBean("defaultCompressor");
		assertNotNull(compressor);
		assertTrue(compressor instanceof GZipCompressor);
	}
}
