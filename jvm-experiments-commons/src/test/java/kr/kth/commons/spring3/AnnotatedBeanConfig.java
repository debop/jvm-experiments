package kr.kth.commons.spring3;

import kr.kth.commons.compress.Compressor;
import kr.kth.commons.compress.DeflateCompressor;
import kr.kth.commons.compress.GZipCompressor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * kr.kth.commons.spring3.SpringTestConfig
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 2.
 */
@Slf4j
@Configuration
public class AnnotatedBeanConfig {

	@Bean
	public Compressor defaultCompressor() {
		return new GZipCompressor();
	}

	@Bean
	public Compressor deflateCompressor() {
		return new DeflateCompressor();
	}
}
