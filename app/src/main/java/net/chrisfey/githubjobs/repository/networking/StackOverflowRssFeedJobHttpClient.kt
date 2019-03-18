package net.chrisfey.githubjobs.repository.networking

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface StackOverflowRssFeedJobHttpClient {
    @GET("/jobs/feed?u=Miles&d=20")
    fun searchJobs(@Query("q") description: String, @Query("l") location: String): Observable<StackOverflowRssFeedResponse>

}


class StackOverflowRssFeedResponse{
    val channel : Channel? = null

    class Channel{
        @JacksonXmlElementWrapper(useWrapping = false)
        val item : List<RssJob>? = null
        class RssJob{
            val link:String? = null
            val title:String? = null
            val description:String? = null
            val author: Author? = null
            class Author{
                val name: String? = null
            }
        }
    }




}
