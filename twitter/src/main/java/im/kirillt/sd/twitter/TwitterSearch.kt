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
import java.util.*

/**
 * @author Kirill
 */

data class ResultTweet(val text: String, val createdAt: Date)

interface TwitterSearch {
    fun requestTweets(hashTag: String, hours: Int): List<Tweet>
}

class TwitterSearchImpl(host: String, val oauth: OAuthProperties) : TwitterSearch {

    private val DATE_FORMAT = SimpleDateFormat("EEE MMM dd HH:mm:ss +0000 yyyy")

    private val twitterApi = Retrofit.Builder()
            .baseUrl(host.trimEnd('/'))
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(makeHttpClient())
            .build()
            .create(TwitterApi::class.java)

    override fun requestTweets(hashTag: String, hours: Int): List<Tweet> {
        //throw UnsupportedOperationException("not implemented")
        val response = twitterApi.search(hashTag).execute()
        if (!response.isSuccessful)
            throw IOException("response code = ${response.code()}")
        return response.body()?.tweets ?: throw Exception("body is null")
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

}

data class OAuthProperties(val key: String, val secret: String, val token: String, val tokenSecret: String)

private interface TwitterApi {
    @GET("/1.1/search/tweets.json?result_type=mixed&include_entities=0")
    fun search(@Query("q") query: String, @Query("max_id") maxId: String? = null,
               @Query("count") count: Int = 100): Call<TweetsTimeLine>
}

data class Tweet(@SerializedName("created_at") val createdAt: String,
                 @SerializedName("id_str") val strId: String,
                 @SerializedName("text") val text: String)

private data class TweetsTimeLine(@SerializedName("statuses") val tweets: List<Tweet>)