package kr.nsoft.commons.guava.eventbus.subscriber;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import kr.nsoft.commons.guava.eventbus.events.CashPurchaseEvent;
import kr.nsoft.commons.guava.eventbus.events.CreditPurchaseEvent;
import kr.nsoft.commons.guava.eventbus.events.SimpleEvent;
import lombok.Getter;

import java.util.List;

/**
 * kr.nsoft.commons.guava.eventbus.subscriber.MultiHandlerSubscriber
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 10.
 */
@Getter
public class MultiHandlerSubscriber {

    private List<CashPurchaseEvent> cashEvents = Lists.newArrayList();
    private List<CreditPurchaseEvent> creditEvents = Lists.newArrayList();
    private List<SimpleEvent> simpleEvents = Lists.newArrayList();

    public MultiHandlerSubscriber(EventBus eventBus) {
        eventBus.register(this);
    }

    @Subscribe
    public void handleCashEvents(CashPurchaseEvent event) {
        cashEvents.add(event);
    }

    @Subscribe
    public void handleCreditEvents(CreditPurchaseEvent event) {
        creditEvents.add(event);
    }

    @Subscribe
    public void handleCreditEvents(SimpleEvent event) {
        simpleEvents.add(event);
    }


}
