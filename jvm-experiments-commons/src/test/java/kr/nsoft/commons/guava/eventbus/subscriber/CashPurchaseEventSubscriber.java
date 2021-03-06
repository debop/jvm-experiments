package kr.nsoft.commons.guava.eventbus.subscriber;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import kr.nsoft.commons.guava.eventbus.events.CashPurchaseEvent;

/**
 * kr.nsoft.commons.guava.eventbus.subscriber.CashPurchaseEventSubscriber
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 10.
 */
public class CashPurchaseEventSubscriber extends EventSubscriber<CashPurchaseEvent> {

    public CashPurchaseEventSubscriber(EventBus eventBus) {
        super(eventBus);
    }

    @Override
    @Subscribe
    public void handleEvent(CashPurchaseEvent event) {
        events.add(event);
    }
}
