package im.kirillt.sd.twitter

import java.util.Properties

/**
 * @author Kirill
 */

fun main(args: Array<String>) {

    val PROPERTIES_FILENAME = "oauth.properties"
    val HOST = "https://api.twitter.com/"

    val properties = Properties()
    properties.load(ClassLoader.getSystemResourceAsStream(PROPERTIES_FILENAME))
    val oauth = OAuthProperties(properties.getProperty("key"),
            properties.getProperty("secret"),
            properties.getProperty("token"),
            properties.getProperty("token.secret"))

    val manager = TwitterSearchManager(TwitterSearchImpl(HOST, oauth))
    val ans = manager.lastHoursTweets("#kotlin", 10)
    ans.forEachIndexed { hour, count -> println("$hour : $count") }
}


