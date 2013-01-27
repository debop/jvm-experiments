package kr.nsoft.commons.spring3;

import kr.nsoft.commons.AbstractTest;
import kr.nsoft.commons.AutoCloseableAction;
import kr.nsoft.commons.compress.*;
import kr.nsoft.commons.unitTesting.TestTool;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.support.GenericApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static kr.nsoft.commons.spring3.SpringTool.getOrRegisterBean;
import static org.junit.Assert.*;

/**
 * kr.nsoft.commons.spring3.SpringTest
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
        if (SpringTool.isNotInitialized())
            SpringTool.init(new GenericApplicationContext());
    }

    @Override
    protected void onAfter() {
        SpringTool.reset();
    }

    @Test
    public void localContainerOverrideGlobalOne() {
        synchronized (syncLock) {

            SpringTool.reset();

            GenericApplicationContext context = new GenericApplicationContext();

            SpringTool.init(context);

            assertSame(context, SpringTool.getContext());

            GenericApplicationContext localContext = new GenericApplicationContext();

            try (AutoCloseableAction action = SpringTool.useLocalContext(localContext)) {
                assertSame(localContext, SpringTool.getContext());
            } catch (Exception ex) {
                fail(ex.getMessage());
            }

            assertSame(context, SpringTool.getContext());
        }
        SpringTool.reset();
    }

    @Test
    public void getBeanIfNoRegisteredBean() {

        try {
            Integer bean = SpringTool.getBean(Integer.class);
            assertNull(bean);
        } catch (Exception e) {
        }

        Long longBean = SpringTool.tryGetBean(Long.class);
        assertNull(longBean);
    }

    @Test
    public void getOrRegisterBean_NotRegisteredBean() {

        ArrayList arrayList = SpringTool.getOrRegisterBean(ArrayList.class);
        assertNotNull(arrayList);
    }

    @Test
    public void getOrRegisterBean_NotRegisteredBean_WithScope() {

        Object compressor = SpringTool.getOrRegisterBean(GZipCompressor.class, BeanDefinition.SCOPE_PROTOTYPE);
        assertNotNull(compressor);
        assertTrue(compressor instanceof GZipCompressor);

        SpringTool.removeBean(GZipCompressor.class);

        compressor = SpringTool.tryGetBean(GZipCompressor.class);
        assertNull(compressor);

        ICompressor deflator = SpringTool.getOrRegisterBean(DeflateCompressor.class, BeanDefinition.SCOPE_SINGLETON);
        assertNotNull(deflator);
        assertTrue(deflator instanceof DeflateCompressor);
    }

    @Test
    public void getOrRegisterBean_WithSubClass_WithScope() {

        Object compressor = SpringTool.getOrRegisterBean(ICompressor.class, GZipCompressor.class, BeanDefinition.SCOPE_PROTOTYPE);
        assertNotNull(compressor);
        assertTrue(compressor instanceof GZipCompressor);

        SpringTool.removeBean(GZipCompressor.class);

        compressor = SpringTool.tryGetBean(GZipCompressor.class);
        assertNull(compressor);

        ICompressor deflator = SpringTool.getOrRegisterBean(ICompressor.class, DeflateCompressor.class, BeanDefinition.SCOPE_SINGLETON);
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

        Map<String, ICompressor> compressorMap = SpringTool.getBeansOfType(ICompressor.class, true, true);
        assertTrue(compressorMap.size() > 0);
        assertEquals(compressorClasses.length, compressorMap.size());

        ICompressor gzip = SpringTool.getBean(GZipCompressor.class);
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

        List<ICompressor> compressors = SpringTool.getBeansByType(ICompressor.class);
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
