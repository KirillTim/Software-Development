package im.kirillt.sd.twitter

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import se.akerfeldt.okhttp.signpost.SigningInterceptor
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

/**
 * @author Kirill
 */

data class Tweet(val text: String, val createdAt: Date)

interface TwitterSearch {
    fun requestTweets(hashTag: String, from: Date, hours: Int): List<Tweet>
}

class TwitterSearchImpl(host: String, private val oauth: OAuthProperties) : TwitterSearch {

    private val DATE_FORMAT = SimpleDateFormat("EEE MMM dd HH:mm:ss +0000 yyyy")

    private val twitterApi = Retrofit.Builder()
            .baseUrl(host.trimEnd('/'))
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(makeHttpClient())
            .build()
            .create(TwitterApi::class.java)

    override fun requestTweets(hashTag: String, from: Date, hours: Int): List<Tweet> {
        fun pastHourFromTime(nowTime: Date, hours: Int): Date {
            val cal = Calendar.getInstance()
            cal.time = nowTime
            cal.add(Calendar.HOUR_OF_DAY, -1 * hours)
            return cal.time
        }

        if (!isCorrectHashTag(hashTag))
            throw IllegalHashTagException(hashTag)

        val endTime = from
        val startTime = pastHourFromTime(endTime, hours)

        val result = mutableListOf<Tweet>()
        var maxId: Long? = null
        while (true) {
            val response = twitterApi.search(hashTag, maxId.toString()).execute()
            if (!response.isSuccessful)
                throw TwitterSearchException(response.code())
            val inTime = response.body().tweets.filter { isBetween(DATE_FORMAT.parse(it.createdAt), startTime, endTime) }
            result += inTime.map { Tweet(it.text, DATE_FORMAT.parse(it.createdAt)) }
            if (inTime.isEmpty())
                break
            maxId = inTime.map { it.id }.min()!! - 1
        }
        return result
    }

    private fun makeHttpClient(): OkHttpClient {
        fun getConsumer(): OkHttpOAuthConsumer {
            val consumer = OkHttpOAuthConsumer(oauth.key, oauth.secret)
            consumer.setTokenWithSecret(oauth.token, oauth.tokenSecret)
            return consumer
        }
        return OkHttpClient.Builder()
                .addInterceptor(SigningInterceptor(getConsumer()))
                .build()
    }

    private fun isBetween(time: Date, start: Date, end: Date) = time >= start && time <= end
}

private interface TwitterApi {
    @GET("/1.1/search/tweets.json?result_type=mixed&include_entities=0")
    fun search(@Query("q") query: String, @Query("max_id") maxId: String? = null,
               @Query("count") count: Int = 100): Call<TweetsTimeLine>
}

fun isCorrectHashTag(hashTag: String): Boolean {
    if (hashTag.length < 2 || !hashTag.startsWith("#"))
        return false
    val word = hashTag.substring(1)
    return word.filter(Char::isLetterOrDigit) == word
}

data class TweetEntry(@SerializedName("created_at") val createdAt: String,
                              @SerializedName("id") val id: Long,
                              @SerializedName("text") val text: String)

data class TweetsTimeLine(@SerializedName("statuses") val tweets: List<TweetEntry>)

class TwitterSearchException(val responseCode: Int) : IOException("Search response failed with code $responseCode")

class IllegalHashTagException(val hashTag: String) : IllegalArgumentException("Illegal hashtag: $hashTag")

data class OAuthProperties(val key: String, val secret: String, val token: String, val tokenSecret: String)