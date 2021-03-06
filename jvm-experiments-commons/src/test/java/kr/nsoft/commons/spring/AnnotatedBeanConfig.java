package kr.nsoft.commons.spring;

import kr.nsoft.commons.compress.DeflateCompressor;
import kr.nsoft.commons.compress.GZipCompressor;
import kr.nsoft.commons.compress.ICompressor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * kr.nsoft.commons.spring.SpringTestConfig
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 2.
 */
@Slf4j
@Configuration
public class AnnotatedBeanConfig {

    @Bean
    public ICompressor defaultCompressor() {
        return new GZipCompressor();
    }

    @Bean
    public ICompressor deflateCompressor() {
        return new DeflateCompressor();
    }
}
