package net.chrisfey.githubjobs.repository

import io.reactivex.Observable
import net.chrisfey.githubjobs.repository.networking.GithubJobHttpClient
import net.chrisfey.githubjobs.repository.networking.GithubJobResponse

class GithubJobRepository(val githubJobClient: GithubJobHttpClient) {

    fun searchJobs(description: String, location: String): Observable<List<GithubJob>> {
        return githubJobClient.searchJobs(description, location)
            .map { it.toGitHubJobs() }
    }

    fun viewJob(jobId: String): Observable<GithubJob> {
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


private fun  List<GithubJobResponse>.toGitHubJobs() = map {
    it.toGitHubJob()
}

private fun  GithubJobResponse.toGitHubJob() =
    GithubJob(
        id = id,
        title = title,
        company = company,
        company_logo = company_logo
    )
