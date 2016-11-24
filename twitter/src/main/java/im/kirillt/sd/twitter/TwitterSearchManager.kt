package im.kirillt.sd.twitter

import java.util.Date
import java.util.concurrent.TimeUnit

/**
 * @author Kirill
 */

class TwitterSearchManager(private val client: TwitterSearch) {
    fun lastHoursTweets(hashTag: String, hours: Int): List<Int> {
        val now = Date()
        val feed = client.requestTweets(hashTag, now, hours)
        val countByHours = IntArray(hours)

        feed.map { hoursFromTime(now, it.createdAt) }
                .filter { 0 <= it && it < hours }
                .groupBy({ it })
                .mapValues { it.value.size }
                .forEach { entry -> countByHours[entry.key] = entry.value }
        return countByHours.toList()
    }

    private fun hoursFromTime(from: Date, past: Date) = TimeUnit.MILLISECONDS.toHours(from.time - past.time).toInt()
}