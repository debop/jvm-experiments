package org.examples.esper.orders;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;
import java.util.Random;

/**
 * org.examples.esper.orders.OrderEventTest
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 30
 */
@Slf4j
public class OrderEventTest {

    private static MyListener listener;
    private static EPServiceProvider epService;

    @BeforeClass
    public static void beforeClass() {
        listener = new MyListener();
        epService = EPServiceProviderManager.getDefaultProvider();
        String expression = "select avg(price) from org.examples.esper.orders.OrderEvent.win:time(30 sec)";
        EPStatement statement = epService.getEPAdministrator().createEPL(expression);
        statement.addListener(listener);
    }

    @Test
    public void sendEvent() throws Exception {
        log.debug("Start send event...");

        Random rnd = new Random(new Date().getTime());

        for (int i = 0; i < 100; i++) {
            OrderEvent event = new OrderEvent("shirt", rnd.nextInt(100));
            epService.getEPRuntime().sendEvent(event);
        }
        Thread.sleep(100);
    }
}
