package springbook.chap10;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


/**
 * springbook.chap10.HelloAutowired
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 21.
 */
@Component("autowiredHello")
@Slf4j
public class HelloAutowired {

	public HelloAutowired() {
		if (log.isDebugEnabled())
			log.debug("HelloAutowiterd created...");
	}

	@Autowired
	@Qualifier("dummyPrinter")
	@Getter
	private Printer printer;

}

@Component
@Qualifier("dummyPrinter")
@Slf4j
class DummyPrinter implements Printer {

	public DummyPrinter() {
		if (log.isDebugEnabled())
			log.debug("DummyPrinter created...");
	}

	@Override
	public void print(String message) {
		//
	}
}
