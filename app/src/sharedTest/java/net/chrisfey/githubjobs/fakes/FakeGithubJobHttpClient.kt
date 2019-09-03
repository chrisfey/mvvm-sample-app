package net.chrisfey.githubjobs.fakes

import io.reactivex.Observable
import net.chrisfey.githubjobs.repository.networking.GithubJobHttpClient
import net.chrisfey.githubjobs.repository.networking.GithubJobResponse

class FakeGithubJobHttpClient : GithubJobHttpClient {
    private lateinit var searchJobs: List<GithubJobResponse>
    private lateinit var viewJob: GithubJobResponse

    override fun searchJobs(description: String, location: String): Observable<List<GithubJobResponse>> {
        return Observable.just(searchJobs)
    }

    override fun viewJob(jobId: String): Observable<GithubJobResponse> {
        return Observable.just(viewJob)
    }
}