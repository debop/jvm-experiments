package kr.nsoft.commons.guava.eventbus.events;

import com.google.common.base.Objects;
import lombok.Getter;

/**
 * kr.nsoft.commons.guava.eventbus.events.PurchaseEvent
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 10.
 */
public class PurchaseEvent {

    @Getter
    protected long amount;

    public PurchaseEvent(long amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("amount", amount)
                .toString();
    }
}
