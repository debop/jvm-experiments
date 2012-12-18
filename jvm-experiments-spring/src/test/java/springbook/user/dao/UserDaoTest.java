package springbook.user.dao;

import kr.kth.commons.spring3.Spring;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import springbook.user.domain.User;

/**
 * Test Case for UserDaoJdbc
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 16
 */
@Slf4j
public class UserDaoTest {

	@Test
	public void userDaoTest() throws Exception {
		IConnectionMaker connectionMaker = new SimpleConnectionMaker();
		UserDao dao = new UserDao(connectionMaker);

		User user = new User();
		user.setId("debop");
		user.setName("배성혁");
		user.setPassword("real21");

		dao.delete(user.getId());

		dao.add(user);

		if (log.isDebugEnabled())
			log.debug("User=[{}] 등록 성공", user);

		User user2 = dao.get(user.getId());

		Assert.assertNotNull(user2);

		if (log.isDebugEnabled())
			log.debug("User=[{}] retrive success", user2);
	}

	@Test
	public void springAnnotationApplicationContextTest() {
		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
		UserDao dao = context.getBean("userDao", UserDao.class);
		Assert.assertNotNull(dao);

		UserDao dao2 = context.getBean("userDao", UserDao.class);
		Assert.assertNotNull(dao2);

		Assert.assertEquals(dao, dao2);
	}

	@Test
	public void springXmlApplicationContextTest() {
		ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
		UserDao dao = context.getBean("userDao", UserDao.class);
		Assert.assertNotNull(dao);

		UserDao dao2 = context.getBean("userDao", UserDao.class);
		Assert.assertNotNull(dao2);

		Assert.assertEquals(dao, dao2);
	}

	@Test
	public void springAnnotationApplicationContext() {
		Spring.initByAnnotatedClasses(DaoFactory.class);

		UserDao dao = Spring.getBean("userDao", UserDao.class);
		Assert.assertNotNull(dao);

		UserDao dao2 = Spring.getBean("userDao", UserDao.class);
		Assert.assertNotNull(dao2);

		Assert.assertEquals(dao, dao2);
	}

	@Test
	public void springXmlApplicationContext() {
		Spring.init("applicationContext.xml");

		UserDao dao = Spring.getBean("userDao", UserDao.class);
		Assert.assertNotNull(dao);

		UserDao dao2 = Spring.getBean("userDao", UserDao.class);
		Assert.assertNotNull(dao2);

		Assert.assertEquals(dao, dao2);
	}
}
