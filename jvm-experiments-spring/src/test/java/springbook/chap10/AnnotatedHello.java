package springbook.chap10;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * springbook.chap10.AnnotatedHello
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 20.
 */
@Component // @Component("annotatedHello") 와 같다. (Bean 이름을 지정하면 된다)
public class AnnotatedHello {

    @Autowired
    @Setter
    Printer printer;
}
