package org.examples.twitter;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import twitter4j.*;

import java.util.List;

/**
 * org.examples.happyevent.TimelineTest
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 31
 */
@Slf4j
public class TimelineTest {

    @Test
    public void getHomeTimeline() {
        Twitter twitter = Twitters.getTwitter();

        try {
            List<Status> statuses = twitter.getHomeTimeline(new Paging(1, 200));
            for (Status status : statuses) {
                log.debug("[{}]:[{}] at [{}]", status.getUser().getName(), status.getText(), status.getCreatedAt());
            }
        } catch (TwitterException e) {
            log.error("트위터에 예외가 발생했습니다.", e);
        }
    }

    @Test
    public void getUserTimeline() {
        Twitter twitter = Twitters.getTwitter();

        try {
            List<Status> statuses = twitter.getUserTimeline("debop68", new Paging(1, 20));
            for (Status status : statuses)
                log.debug("[{}]:[{}] at [{}]", status.getUser().getName(), status.getText(), status.getCreatedAt());

        } catch (TwitterException e) {
            log.error("트위터에 예외가 발생했습니다.", e);
        }
    }

    @Test
    public void getUserTest() {
        Twitter twitter = Twitters.getTwitter();

        try {
            User user = twitter.getUserTimeline().get(0).getUser();
            log.debug("User=[{}]", Twitters.getUserAsString(user));
        } catch (TwitterException e) {
            log.error("트위터에 예외가 발생했습니다.", e);
        }
    }

    @Test
    public void searchTest() {
        Twitter twitter = Twitters.getTwitter();

        try {
            Query query = new Query("나로호");
            QueryResult result = twitter.search(query);

            List<Status> tweets = result.getTweets();
            for (Status status : tweets)
                log.debug("[{}]:[{}] at [{}]", status.getUser().getName(), status.getText(), status.getCreatedAt());
        } catch (TwitterException e) {
            log.error("트위터에 예외가 발생했습니다.", e);
        }
    }
}
