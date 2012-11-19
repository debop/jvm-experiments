package springbook.chap06;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


/**
 * springbook.chap06.DynamicProxyTest
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 18.
 */
public class DynamicProxyTest {

	@Test
	public void proxyFactoryBean() {
		ProxyFactoryBean pfb = new ProxyFactoryBean();
		pfb.setTarget(new HelloTarget());
		pfb.addAdvice(new UppercaseAdvice());

		Hello helloProxy = (Hello)pfb.getObject();
		assertThat(helloProxy.sayHello("Toby"), is("HELLO TOBY"));
		assertThat(helloProxy.sayHi("Toby"), is("HI TOBY"));
		assertThat(helloProxy.sayThankYou("Toby"), is("THANK YOU TOBY"));
	}

	@Test
	public void pointcutAdvisor() {
		ProxyFactoryBean pfb = new ProxyFactoryBean();
		pfb.setTarget(new HelloTarget());
		NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
		pointcut.setMappedName("sayH*");

		pfb.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));

		Hello helloProxy = (Hello)pfb.getObject();
		assertThat(helloProxy.sayHello("Toby"), is("HELLO TOBY"));
		assertThat(helloProxy.sayHi("Toby"), is("HI TOBY"));
		assertThat(helloProxy.sayThankYou("Toby"), is("Thank you Toby"));
	}

	static class UppercaseAdvice implements MethodInterceptor {

		@Override
		public Object invoke(MethodInvocation invocation) throws Throwable {
			String ret = (String)invocation.proceed();
			return ret.toUpperCase();
		}
	}

	static interface Hello {
		String sayHello(String name);
		String sayHi(String name);
		String sayThankYou(String name);
	}

	static class HelloTarget implements Hello {

		@Override
		public String sayHello(String name) {
			return "Hello " + name;
		}

		@Override
		public String sayHi(String name) {
			return "Hi " + name;
		}

		@Override
		public String sayThankYou(String name) {
			return "Thank you " + name;
		}
	}
}
