package kr.kth.commons.io;

import kr.kth.commons.AbstractTest;
import kr.kth.commons.base.YearWeek;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * TestCase for {@link BinarySerializer}
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 10. 4.
 */
@Slf4j
public class BinarySerializerTest extends AbstractTest {

	@Test
	public void serializeAndDeserialize() throws Exception {
		BinarySerializer serializer = new BinarySerializer();

		YearWeek yearWeek = new YearWeek(2000, 1);

		YearWeek copied = (YearWeek) serializer.deserialize(serializer.serialize(yearWeek));

		assertNotNull(copied);
		assertEquals(yearWeek, copied);
	}

	@Test
	public void deepReferenceSerialize() throws Exception {
		BinarySerializer serializer = new BinarySerializer();

		Company company = new Company();
		company.setCode("KTH");
		company.setName("KT Hitel");
		company.setAmount(10000L);
		company.setDescription("한국통신 하이텔");

		for (int i = 0; i < 100; i++) {
			User user = new User();
			user.setName("USER_" + i);
			user.setEmployeeNumber("EMPNO_" + i);
			user.setAddress("ADDR_" + i);
			company.getUsers().add(user);
		}

		Company copied = (Company) serializer.deserialize(serializer.serialize(company));

		assertNotNull(copied);
		assertEquals(100, copied.getUsers().size());
		assertEquals(company, copied);

		int length = company.getUsers().size();
		for (int i = 0; i < length; i++)
			assertEquals(company.getUsers().get(i), copied.getUsers().get(i));
	}
}
