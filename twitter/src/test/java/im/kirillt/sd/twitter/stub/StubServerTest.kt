package im.kirillt.sd.twitter.stub

import com.google.gson.Gson
import com.xebialabs.restito.builder.stub.StubHttp.whenHttp
import com.xebialabs.restito.semantics.Action.status
import com.xebialabs.restito.semantics.Action.stringContent
import com.xebialabs.restito.semantics.Condition.*
import com.xebialabs.restito.server.StubServer
import im.kirillt.sd.twitter.*
import org.glassfish.grizzly.http.util.HttpStatus
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*
import org.junit.rules.ExpectedException


/**
 * @author Kirill
 */

class StubServerTest {
    private val PORT: Int = 32453
    private val EMPTY_OAUTH = OAuthProperties("", "", "", "")
    private val client = TwitterSearchImpl("http://localhost:$PORT", EMPTY_OAUTH)

    private val DATE_FORMAT = SimpleDateFormat("EEE MMM dd HH:mm:ss +0000 yyyy")

    @Test
    fun requestTweets() {
        withStubServer(PORT, { s ->
            val now = Date()
            val tweets = TweetsTimeLine(listOf(TweetEntry(DATE_FORMAT.format(now), 100500, "text")))

            whenHttp(s)
                    .match(startsWithUri("/1.1/search/"))
                    .then(stringContent(Gson().toJson(TweetsTimeLine(emptyList()))))

            val getTweets = client.requestTweets("#tag", now, 24)
            Assert.assertTrue(getTweets.isEmpty())
            //Assert.assertTrue(getTweets.size == 1)
            //Assert.assertEquals("text", getTweets[0].text)
            //Assert.assertEquals(now, getTweets[0].createdAt)
        })
    }

    @Rule
    @JvmField
    val expectedEx = ExpectedException.none()

    @Test
    fun requestTweetsWithNotFoundError() {
        expectedEx.expect(TwitterSearchException::class.java)
        expectedEx.expectMessage("Search response failed with code 404")
        withStubServer(PORT, { s ->
            whenHttp(s)
                    .match(startsWithUri("/1.1/search"))
                    .then(status(HttpStatus.NOT_FOUND_404))

            client.requestTweets("#whatever", Date(), 24)
        })
    }

    private fun withStubServer(port: Int, callback: (StubServer) -> Unit) {
        var stubServer: StubServer? = null
        try {
            stubServer = StubServer(port).run()
            callback.invoke(stubServer)
        } finally {
            if (stubServer != null) {
                stubServer.stop()
            }
        }
    }
}