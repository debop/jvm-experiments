package springbook.chap01;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import springbook.chap01.dao.UserDao;
import springbook.user.domain.User;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * 설명을 추가하세요.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 16
 */
@Slf4j
public class UserDaoTest {

	@Test
	public void springXmlApplicationContextTest() {

		ApplicationContext context = new GenericXmlApplicationContext("springbook/chap01/applicationContext.xml");

		UserDao dao = context.getBean("userDao", UserDao.class);
		Assert.assertNotNull(dao);

		UserDao dao2 = context.getBean("userDao", UserDao.class);
		Assert.assertNotNull(dao2);

		assertEquals(dao, dao2);

		Assert.assertNotNull(dao.getDataSource());
	}

	@Test
	public void userDaoTest() throws Exception {

		ApplicationContext context = new GenericXmlApplicationContext("springbook/chap01/applicationContext.xml");
		UserDao dao = context.getBean("userDao", UserDao.class);

		dao.deleteAll();

		User user = new User();
		user.setId("debop");
		user.setName("배성혁");
		user.setPassword("real21");

		dao.add(user);

		if (log.isDebugEnabled())
			log.debug("User=[{}] add success!!!", user);

		User user2 = dao.get(user.getId());

		Assert.assertNotNull(user2);

		if (log.isDebugEnabled())
			log.debug("User=[{}] retrive success!!!", user2);

		dao.deleteAll();
	}

	@Test
	public void getCountTest() throws Exception {

		ApplicationContext context = new GenericXmlApplicationContext("springbook/chap01/applicationContext.xml");
		UserDao dao = context.getBean("userDao", UserDao.class);

		dao.deleteAll();
		assertEquals(0, dao.getCount());

		User user = new User();
		user.setId("debop");
		user.setName("배성혁");
		user.setPassword("real21");

		dao.add(user);

		assertThat(dao.getCount(), is(1));

		dao.deleteAll();
	}
}
