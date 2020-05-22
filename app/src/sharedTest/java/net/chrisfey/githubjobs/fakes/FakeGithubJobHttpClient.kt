package net.chrisfey.githubjobs.fakes

import io.reactivex.Single
import net.chrisfey.githubjobs.repository.networking.GithubJobHttpClient
import net.chrisfey.githubjobs.repository.networking.GithubJobResponse

class FakeGithubJobHttpClient : GithubJobHttpClient {
    private lateinit var searchJobs: List<GithubJobResponse>
    private lateinit var viewJob: GithubJobResponse

    override fun searchJobs(description: String, location: String): Single<List<GithubJobResponse>> {
        return Single.just(searchJobs)
    }

    override fun viewJob(jobId: String): Single<GithubJobResponse> {
        return Single.just(viewJob)
    }
}