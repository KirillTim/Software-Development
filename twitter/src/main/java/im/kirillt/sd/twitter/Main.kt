package im.kirillt.sd.twitter

import java.util.Properties

/**
 * @author Kirill
 */

fun main(args: Array<String>) {

    val properties = Properties()
    properties.load(ClassLoader.getSystemResourceAsStream("oauth.properties"))
    val oauth = OAuthProperties(properties.getProperty("key"),
            properties.getProperty("secret"),
            properties.getProperty("token"),
            properties.getProperty("token.secret"))

    val twt = TwitterSearchImpl("https://api.twitter.com/", oauth).requestTweets("pepe", 24)
    twt.map(::println)
}


