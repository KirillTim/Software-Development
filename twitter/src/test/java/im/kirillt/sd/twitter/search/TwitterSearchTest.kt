package im.kirillt.sd.twitter.search

import im.kirillt.sd.twitter.*
import im.kirillt.sd.twitter.stub.StubServerTest
import org.junit.Test
import org.junit.Rule
import org.junit.rules.ExpectedException
import java.util.*

/**
 * @author Kirill
 */
class TwitterSearchTest {

    @Rule
    @JvmField
    val expectedEx = ExpectedException.none()

    @Test
    fun shouldThrowIllegalHashTagException() {
        expectedEx.expect(IllegalHashTagException::class.java)
        expectedEx.expectMessage("Illegal hashtag:")
        val client = TwitterSearchImpl("https://api.twitter.com/", OAuthProperties("", "", "", ""))
        client.requestTweets("wrong hash tag", Date(), 10)
    }
}