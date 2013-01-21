package kr.kth.commons.tools;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * kr.kth.commons.tools.ActivatorToolTest
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 20.
 */
@Slf4j
public class ActivatorToolTest {

    @Test
    public void createInstanceWithDefaultConstructor() {
        JClass obj = ActivatorTool.createInstance(JClass.class);
        Assert.assertNotNull(obj);
        Assert.assertEquals(0, obj.getId());
    }

    @Test
    public void crateInstanceWithParameters() {
        JClass obj = ActivatorTool.createInstance(JClass.class, 100, "Dynamic", 200);
        Assert.assertNotNull(obj);
        Assert.assertEquals(100, obj.getId());
        Assert.assertEquals("Dynamic", obj.getName());
    }

    @Test
    public void crateInstanceWithParameterTypes() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        JClass obj = (JClass)ActivatorTool
                .getConstructor(JClass.class, Integer.TYPE, String.class, Integer.class)
                .newInstance(100, "Dynamic", 200);
        Assert.assertNotNull(obj);
        Assert.assertEquals(100, obj.getId());
        Assert.assertEquals("Dynamic", obj.getName());
    }

    @Test
    public void reflectionsWithDefaultConstructor() {
        try {
            JClass obj =
                    (JClass) JClass.class
                            .getConstructor(Integer.TYPE, String.class, Integer.class)
                            .newInstance(100, "Dynamic", 200);
            Assert.assertNotNull(obj);
            Assert.assertEquals(100, obj.getId());
            Assert.assertEquals("Dynamic", obj.getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}



