package kr.nsoft.commons.spring.configuration;

import kr.nsoft.commons.io.BinarySerializer;
import kr.nsoft.commons.json.GsonSerializer;
import kr.nsoft.commons.json.JacksonSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ISerializer 를 구현한 클래스들을 Springs Bean 으로 제공하는 Anntated Configuration 입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 17
 */
@Slf4j
@Configuration
public class SerializerConfiguration {

    @Bean(name = "binarySerializer")
    public BinarySerializer binarySerializer() {
        return new BinarySerializer();
    }

    @Bean(name = "gsonSerializer")
    public GsonSerializer gsonSerializer() {
        return new GsonSerializer();
    }

    @Bean(name = "jacksonSerializer")
    public JacksonSerializer jacksonSerializer() {
        return new JacksonSerializer();
    }
}
