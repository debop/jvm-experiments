package kr.nsoft.contact.service;

import kr.nsoft.contact.dao.ContactDao;
import kr.nsoft.contact.model.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * kr.nsoft.contact.service.ContactServiceImpl
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 2. 6
 */
@Slf4j
@Service
public class ContactServiceImpl implements ContactService {

    @Qualifier("contactDaoImpl")
    @Autowired
    private ContactDao contactDao;

    @Transactional
    public void addContact(Contact contact) {
        contactDao.addContact(contact);
    }

    @Transactional
    public List<Contact> listContact() {
        return contactDao.listContact();
    }

    @Transactional
    public void removeContact(Long id) {
        contactDao.removeContact(id);
    }
}
