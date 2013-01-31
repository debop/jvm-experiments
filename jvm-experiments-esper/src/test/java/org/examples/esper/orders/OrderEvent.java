package org.examples.esper.orders;

import lombok.Getter;

/**
 * org.examples.esper.orders.OrderEvent
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 30
 */
public class OrderEvent {

    @Getter
    private final String itemName;

    @Getter
    private final double price;

    public OrderEvent(String itemName, double price) {
        this.itemName = itemName;
        this.price = price;
    }

}
