package kr.nsoft.data.hibernate.repository;

import kr.nsoft.commons.spring3.SpringTool;
import kr.nsoft.data.hibernate.unitofwork.IUnitOfWork;
import kr.nsoft.data.hibernate.unitofwork.UnitOfWorks;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.example.domain.model.Category;
import org.hibernate.example.domain.model.Event;
import org.jpa.example.domain.model.JpaUser;
import org.junit.*;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

/**
 * kr.nsoft.data.hibernate.repository.HibernateDaoTest
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 26.
 */
@Slf4j
public class HibernateDaoTest {

    HibernateDaoFactory hibernateDaofactory;
    HibernateTransactionManager transactionManager;
    IUnitOfWork unitOfWork;

    @BeforeClass
    public static void beforeClass() {
        if (SpringTool.isNotInitialized())
            SpringTool.init("applicationContext.xml");
    }

    @Before
    public void before() {
        hibernateDaofactory = SpringTool.getBean(HibernateDaoFactory.class);
        transactionManager = SpringTool.getBean(HibernateTransactionManager.class);

        unitOfWork = UnitOfWorks.start();
    }

    @After
    public void after() throws Exception {
        if (unitOfWork != null)
            unitOfWork.close();
    }

    @AfterClass
    public static void afterClass() {
        if (SpringTool.isInitialized())
            SpringTool.reset();
    }

    @Test
    public void createHibernateDao() {

        Assert.assertNotNull(hibernateDaofactory);

        TransactionStatus txstatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            IHibernateDao<JpaUser> jpaUserDao = hibernateDaofactory.getOrCreateHibernateDao(JpaUser.class);
            List<JpaUser> users = jpaUserDao.getAll();

            Assert.assertEquals(0, users.size());

            transactionManager.commit(txstatus);
        } catch (Exception e) {
            transactionManager.rollback(txstatus);
            log.error("예외가 발생했습니다.", e);
            Assert.fail();
        }
    }

    @Test
    public void createCategoryHiberateDao() {
        IHibernateDao<Category> categoryDao = hibernateDaofactory.getOrCreateHibernateDao(Category.class);
        List<Category> categories = categoryDao.getAll();
        Assert.assertEquals(0, categories.size());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void loadSessionFactory() {
        SessionFactory sessionFactory = SpringTool.getBean(SessionFactory.class);
        Assert.assertNotNull(sessionFactory);

        Session session = sessionFactory.openSession();
        Assert.assertNotNull(session);
        List<Event> events = session.createCriteria(Event.class).list();

        session.close();
    }
}
