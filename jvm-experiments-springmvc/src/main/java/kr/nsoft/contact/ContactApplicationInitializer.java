package kr.nsoft.contact;

import kr.nsoft.commons.spring.Springs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

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

        //XmlWebApplicationContext appContext = new XmlWebApplicationContext();
        // appContext.setConfigLocation("/WEB-INF/spring-servlet.xml");

        WebApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);

        Springs.init(appContext);

        servletContext.addListener(new ContextLoaderListener(appContext));

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet());
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("*.htm");
    }
}
