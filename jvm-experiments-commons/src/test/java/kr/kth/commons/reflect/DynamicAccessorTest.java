package kr.kth.commons.reflect;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * DynamicAccessor Test
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 21.
 */
@Slf4j
public class DynamicAccessorTest {

    @lombok.Getter
    @lombok.Setter
    static class User {
        private String email;
        private Double age;

        public User() {}

        public void includeAge(int delta) {
            age += delta;
        }
    }

    @Test
    public void dynamicInstancing() {
        DynamicAccessor<User> userAccessor = DynamicAccessorFactory.create(User.class);
        Assert.assertNotNull(userAccessor);

        Object user = userAccessor.newInstance();

        userAccessor.setProperty(user, "email", "sunghyouk.bae@gmail.com");
        userAccessor.setProperty(user, "age", 110.0);

        Assert.assertEquals("sunghyouk.bae@gmail.com", userAccessor.getProperty(user, "email"));
        Assert.assertEquals(110.0, userAccessor.getProperty(user, "age"));
    }
}
