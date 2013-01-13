package springbook.chap10;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * springbook.chap10.HelloConfig
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 20.
 */
@Configuration
@Slf4j
public class HelloConfig {

    @Bean
    public Hello hello() {
        if (log.isDebugEnabled())
            log.debug("spawn hello instance...");

        Hello hello = new Hello();
        hello.setName("Spring");
        hello.setPrinter(printer());
        return hello;
    }

    @Bean
    public Hello hello2() {
        if (log.isDebugEnabled())
            log.debug("spawn hello2 instance...");

        Hello hello = new Hello();
        hello.setName("Spring2");
        hello.setPrinter(printer());
        return hello;
    }

    @Bean
    public Printer printer() {
        if (log.isDebugEnabled())
            log.debug("spawn StringPrinter instance...");
        return new StringPrinter();
    }
}
