package kr.kth.commons.guava.eventbus.events;

import lombok.Getter;

/**
 * kr.kth.commons.guava.eventbus.events.NoSubscriberEvent
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 10.
 */
public class NoSubscriberEvent {

    @Getter
    String message = "I'm lost";
}
