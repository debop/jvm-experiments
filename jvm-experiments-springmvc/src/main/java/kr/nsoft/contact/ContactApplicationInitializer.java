package kr.nsoft.contact;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * kr.nsoft.contact.ContactApplicationInitializer
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 2. 8.
 */
@Slf4j
public class ContactApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        if (log.isDebugEnabled())
            log.debug("Applicatin starting...");

//        XmlWebApplicationContext appContext = new XmlWebApplicationContext();
//        appContext.setConfigLocation("/WEB-INF/spring-servlet.xml");
//
//        Springs.init(appContext);
//
//        servletContext.addListener(new ContextLoaderListener(appContext));
//
//        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet());
//        dispatcher.setLoadOnStartup(1);
//        dispatcher.addMapping("*.htm");
    }
}
