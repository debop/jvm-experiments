package org.examples.esper.happyevent;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import lombok.extern.slf4j.Slf4j;

/**
 * org.examples.esper.happyevent.HappyEventListener
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 31
 */
@Slf4j
public class HappyEventListener implements UpdateListener {
    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        if (newEvents == null)
            return;
        try {
            EventBean event = newEvents[0];
            if (log.isDebugEnabled())
                log.debug("exceeded the count, actual=[{}]", event.get("sum(ctr)"));
        } catch (Exception e) {
            log.error("예외가 발생했습니다.", e);
        }
    }
}
