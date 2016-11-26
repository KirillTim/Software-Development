package im.kirillt.sd.twitter

import java.util.*
import kotlin.system.exitProcess

/**
 * @author Kirill
 */

fun main(args: Array<String>) {
    if (args.size < 2) {
        println("usage: Main.kt <hash_tag> <hours>")
        exitProcess(1)
    }

    val PROPERTIES_FILENAME = "oauth.properties"
    val HOST = "https://api.twitter.com/"

    val properties = Properties()
    properties.load(ClassLoader.getSystemResourceAsStream(PROPERTIES_FILENAME))
    val oauth = OAuthProperties(properties.getProperty("key"),
            properties.getProperty("secret"),
            properties.getProperty("token"),
            properties.getProperty("token.secret"))

    val manager = TwitterSearchManager(TwitterSearchImpl(HOST, oauth))
    val ans = manager.lastHoursTweets(args[0], Date(), args[1].toInt())
    ans.forEachIndexed { hour, count -> println("$hour : $count") }
}


