package kr.nsoft.contact.service;

import kr.nsoft.contact.model.Contact;

import java.util.List;

/**
 * kr.nsoft.contact.service.ContactService
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 2. 6
 */
public interface ContactService {

    public void addContact(Contact contact);

    public List<Contact> listContact();

    public void removeContact(Long id);
}
