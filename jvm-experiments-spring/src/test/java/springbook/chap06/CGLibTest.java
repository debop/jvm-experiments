package springbook.chap06;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

/**
 * springbook.chap06.CGLibTest
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 18.
 */
@Slf4j
public class CGLibTest {

    @Test
    public void basicProxyCreate() {
        Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback(new UppercaseInterceptor());

        // spawn proxy
        Object proxy = enhancer.create();

        Hello hello = (Hello) proxy;

        assertEquals("HELLO DEBOP", hello.sayHello("debop"));
    }

    static class UppercaseInterceptor implements MethodInterceptor {

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            String result = (String) methodProxy.invokeSuper(o, objects);
            return result.toUpperCase();
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
