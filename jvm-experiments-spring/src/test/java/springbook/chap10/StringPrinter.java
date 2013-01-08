package springbook.chap10;

/**
 * springbook.chap10.StringPrinter
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 20.
 */
public class StringPrinter implements Printer {

    private StringBuilder builder = new StringBuilder();

    @Override
    public void print(String message) {
        this.builder.append(message);
    }

    @Override
    public String toString() {
        return this.builder.toString();
    }
}
