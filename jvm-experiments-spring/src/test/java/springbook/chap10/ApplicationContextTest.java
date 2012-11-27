package springbook.chap10;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.context.support.StaticApplicationContext;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * springbook.chap10.ApplicationContextTest
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 20.
 */
@Slf4j
public class ApplicationContextTest {

	@Test
	public void registerSingletonBean() {
		StaticApplicationContext context = new StaticApplicationContext();
		context.registerSingleton("hello1", Hello.class);

		Hello hello1 = context.getBean("hello1", Hello.class);
		assertNotNull(hello1);
	}

	@Test
	public void setPropertyBean() {
		BeanDefinition helloDef = new RootBeanDefinition(Hello.class);
		helloDef.getPropertyValues().addPropertyValue("name", "Spring");

		StaticApplicationContext context = new StaticApplicationContext();
		context.registerSingleton("hello1", Hello.class);
		context.registerBeanDefinition("hello2", helloDef);

		Hello hello1 = context.getBean("hello1", Hello.class);
		Hello hello2 = context.getBean("hello2", Hello.class);
		assertNotNull(hello2);
		assertEquals("Hello Spring", hello2.sayHello());

		// hello1 과 hello2 는 다른 bean에서 생성하는 것이므로 다르다.
		assertNotEquals(hello1, hello2);
		// application context 에 등록된 bean 의 갯수가 2개이다. (hello1, hello2)
		assertEquals(2, context.getBeanFactory().getBeanDefinitionCount());
	}

	@Test
	public void registerBeanWithDependency() {
		StaticApplicationContext context = new StaticApplicationContext();
		context.registerBeanDefinition("printer", new RootBeanDefinition(StringPrinter.class));

		BeanDefinition helloDef = new RootBeanDefinition(Hello.class);
		helloDef.getPropertyValues().addPropertyValue("name", "Spring");
		helloDef.getPropertyValues().addPropertyValue("printer", new RuntimeBeanReference("printer"));
		context.registerBeanDefinition("hello", helloDef);

		Hello hello = context.getBean("hello", Hello.class);
		hello.print();

		assertEquals("Hello Spring", context.getBean("printer").toString());
	}

	@Test
	public void genericApplicationContext() {
//		GenericApplicationContext context = new GenericApplicationContext();
//		XmlBeanDefinitionReader reader  = new XmlBeanDefinitionReader(context);
//		reader.loadBeanDefinitions("springbook/chap10/applicationContext.xml");
//		context.refresh();

		GenericXmlApplicationContext context =
			new GenericXmlApplicationContext("springbook/chap10/applicationContext.xml");

		Hello hello = context.getBean("hello", Hello.class);
		hello.print();

		assertEquals("Hello Spring", context.getBean("printer").toString());
	}

	@Test
	public void parentChildApplicationContext() {

		ApplicationContext parent =
			new GenericXmlApplicationContext("springbook/chap10/parentContext.xml");
		GenericApplicationContext child = new GenericApplicationContext(parent);
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(child);
		reader.loadBeanDefinitions("springbook/chap10/childContext.xml");

		child.refresh();

		Printer printer = child.getBean("printer", Printer.class);
		assertNotNull(printer);

		Hello hello = child.getBean("hello", Hello.class);
		assertNotNull(hello);
		hello.print();

		assertEquals("Hello Child", printer.toString());
	}

	@Test
	public void annotatedBean() {
		ApplicationContext ctx = new AnnotationConfigApplicationContext("springbook.chap10");

		AnnotatedHello hello = ctx.getBean("annotatedHello", AnnotatedHello.class);
		assertNotNull(hello);
	}

	@Test
	public void configurationContext() {
		// bean 환경 설정 정보를 class 로 표현한 클래스 (@Configuration) 를 이용하여 ApplicationContext를 구성
		ApplicationContext ctx = new AnnotationConfigApplicationContext(AnnotatedHelloConfig.class);

		AnnotatedHello hello = ctx.getBean("annotatedHello", AnnotatedHello.class);
		assertNotNull(hello);

		AnnotatedHelloConfig config = ctx.getBean("annotatedHelloConfig", AnnotatedHelloConfig.class);
		assertNotNull(config);

		assertNotNull(config.printer());
		assertSame(hello, config.annotatedHello(config.printer()));
	}

	@Test
	public void helloConfiguration() {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(HelloConfig.class);

		Hello hello = ctx.getBean("hello", Hello.class);
		assertNotNull(hello);
		assertNotNull(ctx.getBean("printer", Printer.class));

		Hello hello2 = ctx.getBean("hello2", Hello.class);

		assertNotSame(hello, hello2);
	}

	@Test
	public void dataSourceConfigration() {
		ApplicationContext ctx =
			new AnnotationConfigApplicationContext(HelloConfig.class,
			                                       ServiceConfig.class);
		DataSource dataSource = ctx.getBean("dataSource", DataSource.class);
		assertNotNull(dataSource);
	}

	@Test
	public void annotatedAutowiredComponent() {
		ApplicationContext ctx = new AnnotationConfigApplicationContext("springbook.chap10");

		AnnotatedHello hello = ctx.getBean("annotatedHello", AnnotatedHello.class);
		assertNotNull(hello);

		HelloAutowired autowired = ctx.getBean("autowiredHello", HelloAutowired.class);
		assertNotNull(autowired);
		assertNotNull(autowired.getPrinter());
	}

	@Test
	public void annotationSampleTest() {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(BeanA.class, BeanB.class);

		BeanA beanA = ctx.getBean(BeanA.class);
		assertNotNull(beanA);
		assertNotNull(beanA.beanB);
	}

	private static class BeanA {

		@Autowired BeanB beanB;
	}

	private static class BeanB { }

	@Test
	public void genericBeanTest() {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(GenericBean.class);

		@SuppressWarnings("unchecked")
		GenericBean<String> bean = ctx.getBean(GenericBean.class);
		assertNotNull(bean);
		bean.add("a");
		List<String> list = Lists.newArrayList(bean.iterator());
		assertEquals("a", list.get(0));

		Map<String, HashSet> beans = ctx.getBeansOfType(HashSet.class);
		assertEquals(1, beans.size());
	}

	private static class GenericBean<T> extends HashSet<T> { }
}
