package net.chrisfey.githubjobs.repository.networking

import io.reactivex.Observable
import pl.droidsonroids.jspoon.annotation.Selector
import retrofit2.http.GET
import retrofit2.http.Url

interface StackOverflowScreenScrapeJobHttpClient {

    @GET
    fun viewJob(@Url url: String): Observable<StackOverflowScrapedJobResponse>

}


data class StackOverflowScrapedJobResponse(
    @Selector(value = "div.s-avatar img", attr = "src") val companyImage: String? = null
)