package springbook.chap06;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.chap06.impl.UserServiceImpl;

import static org.junit.Assert.*;

/**
 * springbook.chap06.ProxyFactoryBeanByXmlTest
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 18.
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/springbook/chap06/applicationContext.xml")
public class ProxyFactoryBeanByXmlTest {

	@Autowired ApplicationContext context;

	@Test
	public void loadProxyFactoryBean() {
		ProxyFactoryBean pfb = context.getBean("&proxyFactoryBean.userService", ProxyFactoryBean.class);

		assertNotNull(pfb);
		assertEquals(UserServiceImpl.class, pfb.getTargetClass());
		assertTrue(pfb.getObject() instanceof UserService);
	}
}
