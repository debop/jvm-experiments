package springbook.chap11;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

import static org.junit.Assert.assertNotNull;

/**
 * springbook.chap11.TransactionManagerTest
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 23.
 */

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/springbook/chap11/applicationContext.xml")
public class TransactionManagerTest {

	@Autowired ApplicationContext springContext;

	@Test
	public void loadTransactionManager() {
		PlatformTransactionManager txm = springContext.getBean("transactionManager", PlatformTransactionManager.class);
		assertNotNull(txm);
	}
}
