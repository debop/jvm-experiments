package org.springframework.examples;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

/**
 * org.springframework.examples.MvcServerSideTest
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 31
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/spring-servlet.xml")
public class MvcServerSideTest {

    @Autowired
    private ApplicationContext wac;

    private MockMvc mvc;

    @Test
    public void springContextTest() {
        Assert.assertNotNull(wac);
    }
}
