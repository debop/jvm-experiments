package kr.nsoft.contact.dao;

import kr.nsoft.contact.model.Contact;

import java.util.List;

/**
 * kr.nsoft.contact.dao.ContactDao
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 2. 6
 */
public interface ContactDao {

    public void addContact(Contact contact);

    public List<Contact> listContact();

    public void removeContact(Long id);
}
