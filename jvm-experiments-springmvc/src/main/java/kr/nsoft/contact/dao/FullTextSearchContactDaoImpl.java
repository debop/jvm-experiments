package kr.nsoft.contact.dao;

import kr.nsoft.commons.Local;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.search.Search;
import org.springframework.stereotype.Repository;

/**
 * FullText 검색을 위한 Dao 입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 2. 6
 */
@Slf4j
@Repository
public class FullTextSearchContactDaoImpl extends ContactDaoImpl {

    @Override
    protected Session getSession() {
        if (Local.get("ContactDao.FullTextSession") == null) {
            Session session = Search.getFullTextSession(super.getSession());
            Local.put("ContactDao.FullTextSession", session);
        }
        return (Session) Local.get("ContactDao.FullTextSession");
    }
}
