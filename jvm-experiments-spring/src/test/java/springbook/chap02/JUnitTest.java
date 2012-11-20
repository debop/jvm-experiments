package springbook.chap02;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * 설명을 추가하세요.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 16
 */
public class JUnitTest {

	static JUnitTest testObject;

	@Test
	public void test1() {
		assertThat(this, is(not(sameInstance(testObject))));
		testObject = this;
	}

	@Test
	public void test2() {
		assertThat(this, is(not(sameInstance(testObject))));
		testObject = this;
	}

	@Test
	public void test3() {
		assertThat(this, is(not(sameInstance(testObject))));
		testObject = this;
	}
}
