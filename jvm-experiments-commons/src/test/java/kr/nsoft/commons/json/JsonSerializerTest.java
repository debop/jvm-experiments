package kr.nsoft.commons.json;

import com.google.common.collect.Lists;
import kr.nsoft.commons.AbstractTest;
import kr.nsoft.commons.User;
import org.junit.Test;

import java.util.List;

import static kr.nsoft.commons.tools.StringTool.listToString;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * kr.nsoft.commons.json.JsonSerializerTest
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 5.
 */
public class JsonSerializerTest extends AbstractTest {

    private static final List<IJsonSerializer> serializers =
            Lists.newArrayList(new GsonSerializer(), new JacksonSerializer());

    @Test
    public void serializeTest() {
        User user = User.getUser(999);

        for (int i = 0; i < serializers.size(); i++) {
            IJsonSerializer serializer = serializers.get(i);

            byte[] serializedBytes = serializer.serialize(user);
            User deserialized = serializer.deserialize(serializedBytes, User.class);

            assertEquals(user, deserialized);
            assertEquals(user.getHomeAddress(), deserialized.getHomeAddress());
            assertEquals(user.getOfficeAddress(), deserialized.getOfficeAddress());
            assertEquals(listToString(user.getFavoriteMovies()), listToString(deserialized.getFavoriteMovies()));

            assertArrayEquals(user.getByteArray(), deserialized.getByteArray());
        }
    }
}
