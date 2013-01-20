package kr.kth.commons.tools;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

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
}



