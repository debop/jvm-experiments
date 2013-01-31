package org.examples.esper.orders;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import lombok.extern.slf4j.Slf4j;

/**
 * org.examples.esper.orders.MyListener
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 30
 */
@Slf4j
public class MyListener implements UpdateListener {
    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        EventBean event = newEvents[0];
        if (log.isDebugEnabled())
            log.debug("arg=[{}]", event.get("avg(price)"));
    }
}
