package net.chrisfey.githubjobs.repository.networking

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface GithubJobHttpClient{

    //https://jobs.github.com/positions.json?description=python&location=new+york
    @GET("positions.json")
    fun searchJobs(@Query("description") description: String, @Query("location") location: String): Single<List<GithubJobResponse>>


    @GET("positions/{jobId}.json")
    fun viewJob(@Path("jobId") jobId: String): Single<GithubJobResponse>

}



data class GithubJobResponse(
    val id : String = "",
    val title: String = "",
    val company: String = "",
    val company_logo: String? = ""
//    val howToApply: String = "",
//    val createdAt: String = "",
//    val description: String = "",
//    val companyUrl: String = "",
//    val location: String = "",
//    val id: String = "",
//    val type: String = "",
//    val url: String = ""
)