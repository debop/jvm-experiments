package springbook.chap10;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * springbook.chap10.AnnotatedHelloConfig
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 20.
 */
@Configuration
@Slf4j
public class AnnotatedHelloConfig {

	@Bean
	//@Scope(value = "prototype")
	public AnnotatedHello annotatedHello(Printer printer) {
		if (log.isDebugEnabled())
			log.debug("annotatedHello instance creating...");

		AnnotatedHello hello = new AnnotatedHello();
		hello.setPrinter(printer);
		return hello;
	}

	@Bean
	public Printer printer() {
		return new StringPrinter();
	}
}
