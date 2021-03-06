package kr.nsoft.commons.json;

import kr.nsoft.commons.AbstractTest;
import kr.nsoft.commons.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static kr.nsoft.commons.tools.StringTool.listToString;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * kr.nsoft.commons.json.JsonToolTest
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 5.
 */
@Slf4j
public class JsonToolTest extends AbstractTest {

    @Test
    public void serializeToBytes() {

        User user = User.getUser(999);

        byte[] serializedBytes = JsonTool.serializeAsBytes(user);
        User deserialized = JsonTool.deserializeFromBytes(serializedBytes, User.class);

        assertEquals(user, deserialized);
        assertEquals(user.getHomeAddress(), deserialized.getHomeAddress());
        assertEquals(user.getOfficeAddress(), deserialized.getOfficeAddress());
        assertEquals(listToString(user.getFavoriteMovies()), listToString(deserialized.getFavoriteMovies()));

        assertArrayEquals(user.getByteArray(), deserialized.getByteArray());
    }

    @Test
    public void serializeToText() {

        User user = User.getUser(999);

        String serializedText = JsonTool.serializeAsText(user);
        User deserialized = JsonTool.deserializeFromText(serializedText, User.class);

        assertEquals(user, deserialized);
        assertEquals(user.getHomeAddress(), deserialized.getHomeAddress());
        assertEquals(user.getOfficeAddress(), deserialized.getOfficeAddress());
        assertEquals(listToString(user.getFavoriteMovies()), listToString(deserialized.getFavoriteMovies()));

        assertArrayEquals(user.getByteArray(), deserialized.getByteArray());
    }
}
