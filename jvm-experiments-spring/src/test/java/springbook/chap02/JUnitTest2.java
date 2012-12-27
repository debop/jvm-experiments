package springbook.chap02;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;

import java.util.Set;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

/**
 * 설명을 추가하세요.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 16
 */
public class JUnitTest2 {

	private Set<JUnitTest2> testObjects;

	@Before
	public void before() {
		testObjects = Sets.newHashSet();
	}


	@Test
	public void test1() {
		assertThat(testObjects, not(JUnitMatchers.hasItem(this)));
		testObjects.add(this);
	}

	@Test
	public void test2() {
		assertThat(testObjects, not(JUnitMatchers.hasItem(this)));
		testObjects.add(this);
	}

	@Test
	public void test3() {
		assertThat(testObjects, not(JUnitMatchers.hasItem(this)));
		testObjects.add(this);
	}
}
