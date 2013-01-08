package springbook.chap03;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.user.domain.User;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * springbook.chap03.UserDaoTest
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 17.
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/springbook/chap03/applicationContext.xml")
public class UserDaoTest {

    @Autowired
    private ApplicationContext appContext;

    @Test
    public void springXmlApplicationContextTest() {

        UserDao dao = appContext.getBean("userDao", UserDao.class);
        Assert.assertNotNull(dao);

        UserDao dao2 = appContext.getBean("userDao", UserDao.class);
        Assert.assertNotNull(dao2);

        assertEquals(dao, dao2);

        Assert.assertNotNull(dao.getJdbcTemplate().getDataSource());
    }

    @Test
    public void userDaoTest() throws Exception {

        UserDao dao = appContext.getBean("userDao", UserDao.class);

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

        UserDao dao = appContext.getBean("userDao", UserDao.class);

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

    @Test
    public void getAllTest() {

        UserDao dao = appContext.getBean("userDao", UserDao.class);

        dao.deleteAll();
        assertEquals(0, dao.getCount());

        User user = new User();
        user.setId("debop");
        user.setName("배성혁");
        user.setPassword("real21");

        dao.add(user);

        assertThat(dao.getCount(), is(1));

        List<User> users = dao.getAll();
        assertThat(users.size(), is(1));

    }
}
