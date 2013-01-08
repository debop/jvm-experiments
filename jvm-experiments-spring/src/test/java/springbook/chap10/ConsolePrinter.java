package springbook.chap10;

/**
 * springbook.chap10.ConsolePrinter
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 20.
 */
public class ConsolePrinter implements Printer {

    @Override
    public void print(String message) {
        System.out.println(message);
    }
}
