package im.kirillt.sd.twitter

import java.util.*

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

    val twt = TwitterSearchImpl("https://api.twitter.com/", oauth).requestTweets("#kotlin", 10)
    println("feed size = ${twt.size}")
    twt.map(::println)

}


