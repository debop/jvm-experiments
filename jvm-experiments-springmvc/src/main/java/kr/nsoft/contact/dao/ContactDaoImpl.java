package kr.nsoft.contact.dao;

import kr.nsoft.contact.model.Contact;
import kr.nsoft.data.hibernate.repository.HibernateDaoImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * kr.nsoft.contact.dao.ContactDaoImpl
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 2. 6
 */
@Slf4j
@Repository
public class ContactDaoImpl extends HibernateDaoImpl<Contact> implements ContactDao {

    public ContactDaoImpl() {
        super(Contact.class);
    }

    @Override
    public void addContact(Contact contact) {
        save(contact);
//        getSession().flush();
    }

    @Override
    public List<Contact> listContact() {
        return getAll();
//        Session session = getSession();
//        return (List<Contact>)session.createQuery("from Contact").list();
    }

    @Override
    public void removeContact(Long id) {
        if (log.isDebugEnabled())
            log.debug("Contact[{}] 을 삭제합니다...", id);

        // Session session = getSession();

        // Contact contact = (Contact)session.get(Contact.class, id);
        Contact contact = get(id);
        if (contact != null) {
            //session.delete(contact);
            //session.flush();
            delete(contact);

            if (log.isDebugEnabled())
                log.debug("Contact 정보를 삭제했습니다. Contact=" + contact);
        }
    }
}