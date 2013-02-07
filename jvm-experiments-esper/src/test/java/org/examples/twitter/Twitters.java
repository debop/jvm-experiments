package org.examples.twitter;

import lombok.extern.slf4j.Slf4j;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 * org.examples.twitter.Twitters
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 31
 */
@Slf4j
public abstract class Twitters {

    private Twitters() {}

    protected static final String CONSUMER_KEY = "TM6B5JuM29SgAZJVKDCw";
    protected static final String CONSUMER_SECRET = "5bYa9RbRKAEeB57seIdOvbQSKayixrVjenaRm6H5Kk";
    protected static final String ACCESS_TOKEN = "94962170-tukzJswgnZke3DC19S2zJ0P0T6caLQyYDcpwmCL7X";
    protected static final String ACCESS_TOKEN_SECRET = "JWxsZv7Oa2Kbj8BzUw2R6kJlr3rqjd6pnDHBK9NtQ";

    private static ConfigurationBuilder cfgBuilder = null;
    private static Configuration twitterCfg = null;
    private static volatile Twitter twitter = null;
    private static volatile TwitterStream twitterStream = null;

    // protected static final String username = "debop68";

    static {
        cfgBuilder = new ConfigurationBuilder();
        cfgBuilder.setDebugEnabled(true)
                .setOAuthConsumerKey(CONSUMER_KEY)
                .setOAuthConsumerSecret(CONSUMER_SECRET)
                .setOAuthAccessToken(ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);

        twitterCfg = cfgBuilder.build();
    }

    public static Configuration getConfiguration() {
        return twitterCfg;
    }

    public synchronized static Twitter getTwitter() {
        if (twitter == null) {
            twitter = new TwitterFactory(getConfiguration()).getInstance();
        }
        return twitter;
    }

    public synchronized static TwitterStream getTwitterStream() {
        if (twitterStream == null)
            twitterStream = new TwitterStreamFactory(getConfiguration()).getInstance();

        return twitterStream;
    }

    protected static String getUserAsString(twitter4j.User user) {
        StringBuilder sb = new StringBuilder();
        return sb.append("id=").append(user.getId()).append(",")
                .append("name=").append(user.getName()).append(",")
                .append("screenName=").append(user.getScreenName()).append(",")
                .append("description=").append(user.getDescription())
                .toString();
    }
}
