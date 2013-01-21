package kr.kth.commons.reflect;

import kr.kth.commons.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * kr.kth.commons.reflect.DynamicAccessorFactoryTest
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 21.
 */
@Slf4j
public class DynamicAccessorFactoryTest {

    @Test
    public void createDynamicAccessor() {
        DynamicAccessor<User> userAccessor = DynamicAccessorFactory.create(User.class);
        Assert.assertNotNull(userAccessor);

        Object user = userAccessor.newInstance();

        userAccessor.setProperty(user, "email", "sunghyouk.bae@gmail.com");
        Assert.assertEquals("sunghyouk.bae@gmail.com", userAccessor.getProperty(user, "email"));
    }
}
