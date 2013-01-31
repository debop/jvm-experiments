package org.examples.esper.happyevent;

import lombok.Getter;
import lombok.Setter;

/**
 * org.examples.esper.happyevent.HappyMessage
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 31
 */
public class HappyMessage {
    @Getter
    @Setter
    private String user;
    @Getter
    private final int ctr = 1;
}
