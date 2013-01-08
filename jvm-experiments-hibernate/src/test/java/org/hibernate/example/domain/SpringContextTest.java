package org.hibernate.example.domain;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.example.domain.model.Category;
import org.hibernate.example.domain.model.Event;
import org.hibernate.example.domain.model.StatefulEntityImpl;
import org.hibernate.metadata.ClassMetadata;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * org.hibernate.example.domain.SpringContextTest
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 21.
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class SpringContextTest {

    @Autowired
    SessionFactory sessionFactory;

    private Session session;

    @Before
    public void before() {
        session = sessionFactory.openSession();
    }

    @After
    public void after() {
        if (session != null)
            session.close();
    }

    @Test
    public void configurationVerfication() {

        assertNotNull(sessionFactory);

        @Cleanup
        Session session = sessionFactory.openSession();
        assertNotNull(session);
    }

    @Test
    public void entityMappingTest() {
        try {
            log.info("querying all the managed entities...");
            final Map<String, ClassMetadata> metadataMap = session.getSessionFactory().getAllClassMetadata();

            for (Object key : metadataMap.keySet()) {
                final ClassMetadata classMetadata = metadataMap.get(key);
                final String entityName = classMetadata.getEntityName();
                final Query query = session.createQuery("from " + entityName);
                query.setCacheable(true);

                log.info("executing hql= " + query.getQueryString());

                for (Object o : query.list()) {
                    log.info("IEntity=  " + o);
                }
            }
        } finally {
            session.flush();
        }
        log.info("성공했습니다.");
    }

    @Test
    public void categoryAndEvent() {

        session.createQuery("delete from Event").executeUpdate();
        session.createQuery("delete from Category").executeUpdate();
        session.flush();
        Category category = new Category("category1");

        Event event1 = new Event("event1", new Date());
        Event event2 = new Event("event2", new Date());
        category.getEvents().add(event1);
        event1.setCategory(category);

        category.getEvents().add(event2);
        event2.setCategory(category);

        session.saveOrUpdate(category);
        session.flush();
        session.clear();

        @SuppressWarnings("unchecked")
        final List<Category> categories = (List<Category>) session.createCriteria(Category.class).list();
        assertEquals(1, categories.size());
        assertEquals(2, categories.get(0).getEvents().size());

        for (Category c : categories)
            log.debug("Category=[{}]", c);
    }

    @Test
    public void stateEntityImplSave() {

        session.createQuery("delete from StatefulEntityImpl").executeUpdate();
        session.flush();

        StatefulEntityImpl stateEntity = new StatefulEntityImpl("abc");
        session.persist(stateEntity);
        session.flush();

        StatefulEntityImpl stateEntity2 = new StatefulEntityImpl("가나다");
        session.persist(stateEntity2);
        session.flush();

        if (log.isDebugEnabled())
            log.debug("엔티티를 저장했습니다. entity=" + stateEntity);

        session.clear();

        @SuppressWarnings("unchecked")
        final List<StatefulEntityImpl> loaded =
                (List<StatefulEntityImpl>) session.createQuery("from " + StatefulEntityImpl.class.getName()).list();

        assertEquals(2, loaded.size());

        StatefulEntityImpl entity = loaded.get(0);
        assertNotNull(entity);
        assertEquals("abc", entity.getName());

        entity.setName("modified");
        session.saveOrUpdate(entity);
        session.flush();

        log.debug("엔티티를 로드했습니다. entity=" + entity);
    }
}
