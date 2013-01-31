package org.examples.esper.happyevent;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import lombok.extern.slf4j.Slf4j;
import org.examples.twitter.Twitters;
import org.junit.BeforeClass;
import org.junit.Test;
import twitter4j.*;

/**
 * org.examples.esper.happyevent.TwitterMessageTest
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 31
 */
@Slf4j
public class TwitterMessageTest {

    private static HappyEventListener listener;
    private static EPServiceProvider epService;

    @BeforeClass
    public static void beforeClass() {

        Configuration cfg = new Configuration();
        cfg.addEventType("HappyMessage", HappyMessage.class.getName());
        epService = EPServiceProviderManager.getDefaultProvider(cfg);

        String expression = "select user, sum(ctr) from HappyMessage.win:time(10 seconds) having sum(ctr) > 2";
        EPStatement statement = epService.getEPAdministrator().createEPL(expression);

        listener = new HappyEventListener();
        statement.addListener(listener);
    }

    private static void raiseEvent(EPServiceProvider epService, String name, Status status) {
        HappyMessage msg = new HappyMessage();
        msg.setUser(status.getUser().getScreenName());
        epService.getEPRuntime().sendEvent(msg);
    }

    private static StatusListener getStatusListener() {
        return new StatusListener() {
            @Override
            public void onStatus(Status status) {
                String[] searchStrs = new String[]{"lol", "ㅎㅎ", "ㅋㅋ"};
                for (String searchStr : searchStrs) {
                    if (status.getText().indexOf(searchStr) > 0) {
                        log.debug("********** [{}] found *********: [{}]", searchStr, status.getText());
                        raiseEvent(epService, status.getUser().getScreenName(), status);
                        break;
                    }
                }
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                log.debug("Got a status deletion notice id=[{}]", statusDeletionNotice.getStatusId());
            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                log.debug("Got track limitation notice:[{}]", numberOfLimitedStatuses);
            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
                log.debug("Got scrub_geo event userId=[{}], upToStatusId=[{}]", +userId, upToStatusId);

            }

            @Override
            public void onStallWarning(StallWarning warning) {
                // Nothing to do
            }

            @Override
            public void onException(Exception ex) {
                log.error("예외가 발생했습니다.", ex);
            }
        };
    }

    @Test
    public void twitterStreamTest() throws Exception {
        TwitterStream twitterStream = Twitters.getTwitterStream();
        StatusListener listener = getStatusListener();

        twitterStream.addListener(listener);
        twitterStream.sample();

        Thread.sleep(30000);
    }
}
