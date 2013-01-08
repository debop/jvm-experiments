package kr.kth.commons.io;

import kr.kth.commons.base.ISerializer;
import kr.kth.commons.compress.ICompressor;
import kr.kth.commons.cryptography.symmetric.ISymmetricByteEncryptor;
import kr.kth.commons.spring3.Spring;
import kr.kth.commons.spring3.configuration.CompressorConfiguration;
import kr.kth.commons.spring3.configuration.EncryptorConfiguration;
import kr.kth.commons.spring3.configuration.SerializerConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

/**
 * 설명을 추가하세요.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 17
 */
@Slf4j
public class SerializerTest {

    static List<ICompressor> compressors;
    static List<ISerializer> serializers;
    static List<ISymmetricByteEncryptor> encryptors;

    @BeforeClass
    public static void beforeClass() {
        if (Spring.isNotInitialized())
            Spring.initByAnnotatedClasses(CompressorConfiguration.class,
                    EncryptorConfiguration.class,
                    SerializerConfiguration.class);

        compressors = Spring.getBeansByType(ICompressor.class);
        serializers = Spring.getBeansByType(ISerializer.class);
        encryptors = Spring.getBeansByType(ISymmetricByteEncryptor.class);
    }

    private static final Company company;

    static {
        company = new Company();
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
    }


    @Test
    public void compressableSerializeTest() {
        for (ICompressor compressor : compressors) {
            for (ISerializer serializer : serializers) {
                ISerializer cs = new CompressableSerializer(serializer, compressor);

                if (log.isDebugEnabled())
                    log.debug("compressor=[{}], serializer=[{}]", compressor.getClass(), serializer.getClass());

                byte[] bytes = cs.serialize(company);
                Company deserialized = cs.deserialize(bytes, Company.class);

                Assert.assertNotNull(deserialized);
                Assert.assertEquals(deserialized.getCode(), company.getCode());
                Assert.assertEquals(deserialized.getUsers().size(), company.getUsers().size());
            }
        }
    }

    @Test
    public void encryptableSerializeTest() {
        for (ISymmetricByteEncryptor encryptor : encryptors) {
            for (ISerializer serializer : serializers) {
                ISerializer cs = new EncryptableSerializer(serializer, encryptor);

                if (log.isDebugEnabled())
                    log.debug("encryptor=[{}], serializer=[{}]", encryptor.getClass(), serializer.getClass());

                byte[] bytes = cs.serialize(company);
                Company deserialized = cs.deserialize(bytes, Company.class);

                Assert.assertNotNull(deserialized);
                Assert.assertEquals(deserialized.getCode(), company.getCode());
                Assert.assertEquals(deserialized.getUsers().size(), company.getUsers().size());
            }
        }
    }
}
