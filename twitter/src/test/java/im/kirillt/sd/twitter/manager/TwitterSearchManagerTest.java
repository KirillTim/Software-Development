package im.kirillt.sd.twitter.manager;

import im.kirillt.sd.twitter.Tweet;
import im.kirillt.sd.twitter.TwitterSearch;
import im.kirillt.sd.twitter.TwitterSearchManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Kirill
 */
public class TwitterSearchManagerTest {

  private TwitterSearchManager manager;

  @Mock
  private TwitterSearch client;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    manager = new TwitterSearchManager(client);
  }

  @Test
  public void testTweets() {
    String hashTag = "#tag";
    Date now = new Date();
    when(client.requestTweets(hashTag, now, 2))
        .thenReturn(createAnswer());

    List<Integer> lastHours = manager.lastHoursTweets(hashTag, now, 2);
    Assert.assertEquals(Arrays.asList(1, 1), lastHours);
  }

  private List<Tweet> createAnswer() {
    long now = System.currentTimeMillis();
    long hoursMs = TimeUnit.HOURS.toMillis(1);
    Tweet nowTweet = new Tweet("1", new Date(now));
    Tweet hourAgoTweet = new Tweet("2", new Date(now - hoursMs - 1000));
    return Arrays.asList(nowTweet, hourAgoTweet);
  }
}
