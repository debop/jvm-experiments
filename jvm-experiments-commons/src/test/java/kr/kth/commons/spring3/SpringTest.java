package kr.kth.commons.spring3;

import kr.kth.commons.AbstractTest;
import kr.kth.commons.base.AutoCloseableAction;
import kr.kth.commons.compress.*;
import kr.kth.commons.unitTesting.TestTool;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.support.GenericApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static kr.kth.commons.spring3.Spring.getOrRegisterBean;
import static org.junit.Assert.*;

/**
 * kr.kth.commons.spring3.SpringTest
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 2.
 */
@Slf4j
public class SpringTest extends AbstractTest {

	private static final Object syncLock = new Object();

	private static Class[] compressorClasses =
		new Class[]{BZip2Compressor.class,
		            DeflateCompressor.class,
		            GZipCompressor.class,
		            XZCompressor.class};

	@Override
	protected void onBefore() {
		if (Spring.isNotInitialized())
			Spring.init(new GenericApplicationContext());
	}

	@Override
	protected void onAfter() {
		Spring.reset();
	}

	@Test
	public void localContainerOverrideGlobalOne() {
		synchronized (syncLock) {

			Spring.reset();
			GenericApplicationContext context = new GenericApplicationContext();

			Spring.init(context);

			assertSame(context, Spring.getContext());

			GenericApplicationContext localContext = new GenericApplicationContext();

			try (AutoCloseableAction action = Spring.useLocalContext(localContext)) {
				assertSame(localContext, Spring.getContext());
			} catch (Exception ex) {
				fail(ex.getMessage());
			}

			assertSame(context, Spring.getContext());
		}
		Spring.reset();
	}

	@Test
	public void getBeanIfNoRegisteredBean() {

		try {
			Integer bean = Spring.getBean(Integer.class);
			assertNull(bean);
		} catch (Exception e) {
		}

		Long longBean = Spring.tryGetBean(Long.class);
		assertNull(longBean);
	}

	@Test
	public void getOrRegisterBean_NotRegisteredBean() {

		ArrayList arrayList = getOrRegisterBean(ArrayList.class);
		assertNotNull(arrayList);
	}

	@Test
	public void getOrRegisterBean_NotRegisteredBean_WithScope() {

		Object compressor = getOrRegisterBean(GZipCompressor.class, BeanDefinition.SCOPE_PROTOTYPE);
		assertNotNull(compressor);
		assertTrue(compressor instanceof GZipCompressor);

		Spring.removeBean(GZipCompressor.class);

		compressor = Spring.tryGetBean(GZipCompressor.class);
		assertNull(compressor);

		ICompressor deflator = getOrRegisterBean(DeflateCompressor.class, BeanDefinition.SCOPE_SINGLETON);
		assertNotNull(deflator);
		assertTrue(deflator instanceof DeflateCompressor);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void getAllTypes() {

		for (Class clazz : compressorClasses) {
			ICompressor bean = (ICompressor) getOrRegisterBean(clazz, BeanDefinition.SCOPE_PROTOTYPE);
			assertNotNull(bean);
		}

		Map<String, ICompressor> compressorMap = Spring.getBeansOfType(ICompressor.class, true, true);
		assertTrue(compressorMap.size() > 0);
		assertEquals(compressorClasses.length, compressorMap.size());

		ICompressor gzip = Spring.getBean(GZipCompressor.class);
		assertNotNull(gzip);
		assertTrue(gzip instanceof GZipCompressor);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void getBeansByTypeTest() {

		for (Class clazz : compressorClasses) {
			ICompressor bean = (ICompressor) getOrRegisterBean(clazz, BeanDefinition.SCOPE_PROTOTYPE);
			assertNotNull(bean);
		}

		List<ICompressor> compressors = Spring.getBeansByType(ICompressor.class);
		assertTrue(compressors.size() > 0);
		for (ICompressor compressor : compressors)
			assertNotNull(compressor);
	}

	@Test
	public void getAllTypesInMultiThread() {
		TestTool.runTasks(5, new Runnable() {
			@Override
			public void run() {
				getAllTypes();
			}
		});
	}
}
