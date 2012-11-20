package springbook.chap10;

import lombok.Setter;

/**
 * springbook.chap10.Hello
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 20.
 */
public class Hello {

	@Setter String name;
	@Setter Printer printer;

	public String sayHello() {
		return "Hello " + name;
	}

	public void print() {
		this.printer.print(sayHello());
	}
}
