package springbook.chap02;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * 설명을 추가하세요.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/springbook/chap02/junit.xml")
public class JUnitTestWithApplicationContext {

	@Autowired ApplicationContext context;

	static Set<JUnitTestWithApplicationContext> testObjects = new HashSet<>();
	static ApplicationContext contextObject = null;

	@Test
	public void test1() {
		assertThat(testObjects, not(hasItems(this)));
		testObjects.add(this);

		assertThat(contextObject == null || contextObject == this.context, is(true));
		contextObject = this.context;
	}

	@Test
	public void test2() {
		assertThat(testObjects, not(hasItems(this)));
		testObjects.add(this);

		assertThat(contextObject == null || contextObject == this.context, is(true));
		contextObject = this.context;
	}

	@Test
	public void test3() {
		assertThat(testObjects, not(hasItems(this)));
		testObjects.add(this);

		assertThat(contextObject == null || contextObject == this.context, is(true));
		contextObject = this.context;
	}
}
