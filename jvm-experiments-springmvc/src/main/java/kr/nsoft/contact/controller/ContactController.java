package kr.nsoft.contact.controller;

import kr.nsoft.contact.model.Contact;
import kr.nsoft.contact.service.ContactService;
import kr.nsoft.data.hibernate.unitofwork.IUnitOfWork;
import kr.nsoft.data.hibernate.unitofwork.UnitOfWorks;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * kr.nsoft.contact.controller.ContactController
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 2. 6
 */
@Slf4j
@Controller
public class ContactController {

    @Autowired
    private ContactService contactService;

    @RequestMapping("/index")
    public String listContact(Map<String, Object> map) {
        try (IUnitOfWork unitOfWork = UnitOfWorks.start()) {
            map.put("contact", new Contact());
            map.put("contactList", contactService.listContact());
        } catch (Exception e) {
            log.error("예외가 발생했습니다.", e);
        }
        return "contact";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addContact(@ModelAttribute("contact") Contact contact, BindingResult result) {
        try (IUnitOfWork unitOfWork = UnitOfWorks.start()) {
            contactService.addContact(contact);
        } catch (Exception e) {
            log.error("예외가 발생했습니다.", e);
        } finally {
            UnitOfWorks.getCurrent().transactionalFlush();
        }
        return "redirect:/index";
    }

    @RequestMapping("/delete/{contactId}")
    public String deleteContact(@PathVariable("contactId") Long contactId) {

        try (IUnitOfWork unitOfWork = UnitOfWorks.start()) {
            contactService.removeContact(contactId);
        } catch (Exception e) {
            log.error("예외가 발생했습니다.", e);
        } finally {
            UnitOfWorks.getCurrent().transactionalFlush();
        }
        return "redirect:/index";
    }
}
