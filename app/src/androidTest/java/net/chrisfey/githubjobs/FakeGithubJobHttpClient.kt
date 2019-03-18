package net.chrisfey.githubjobs

import io.reactivex.Observable
import net.chrisfey.githubjobs.repository.networking.GithubJob
import net.chrisfey.githubjobs.repository.networking.GithubJobHttpClient

class FakeGithubJobHttpClient() : GithubJobHttpClient {
    lateinit var searchJobs: List<GithubJob>
    lateinit var viewJob: GithubJob

    override fun searchJobs(description: String, location: String): Observable<List<GithubJob>> {
        return Observable.just(searchJobs)
    }

    override fun viewJob(jobId: String): Observable<GithubJob> {
        return Observable.just(viewJob)
    }
}