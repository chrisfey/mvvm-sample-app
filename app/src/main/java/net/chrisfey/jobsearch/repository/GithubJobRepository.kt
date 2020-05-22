package net.chrisfey.jobsearch.repository

import io.reactivex.Single
import net.chrisfey.jobsearch.repository.networking.GithubJobHttpClient
import net.chrisfey.jobsearch.repository.networking.GithubJobResponse
interface IGithubJobRepository {
    fun searchJobs(description: String, location: String): Single<List<GithubJob>>
    fun viewJob(jobId: String): Single<GithubJob>
}
class GithubJobRepository(private val githubJobClient: GithubJobHttpClient) : IGithubJobRepository{

    override fun searchJobs(description: String, location: String): Single<List<GithubJob>> {
        return githubJobClient.searchJobs(description, location)
            .map { it.toGitHubJobs() }
    }

    override fun viewJob(jobId: String): Single<GithubJob> {
        return githubJobClient.viewJob(jobId)
            .map { it.toGitHubJob() }
    }
}


data class GithubJob(
    val id : String = "",
    val title: String = "",
    val company: String = "",
    val company_logo: String? = ""
)


fun  List<GithubJobResponse>.toGitHubJobs() = map {
    it.toGitHubJob()
}

fun  GithubJobResponse.toGitHubJob() =
    GithubJob(
        id = id,
        title = title,
        company = company,
        company_logo = company_logo
    )

