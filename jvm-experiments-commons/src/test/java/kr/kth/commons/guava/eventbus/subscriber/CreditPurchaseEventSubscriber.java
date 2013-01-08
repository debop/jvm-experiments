package kr.kth.commons.guava.eventbus.subscriber;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import kr.kth.commons.guava.eventbus.events.CreditPurchaseEvent;

/**
 * kr.kth.commons.guava.eventbus.subscriber.CreditPurchaseEventSubscriber
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 10.
 */
public class CreditPurchaseEventSubscriber extends EventSubscriber<CreditPurchaseEvent> {

    public CreditPurchaseEventSubscriber(EventBus eventBus) {
        super(eventBus);
    }

    @Override
    @Subscribe
    public void handleEvent(CreditPurchaseEvent event) {
        events.add(event);
    }
}
