package net.chrisfey.githubjobs.fakes

import io.reactivex.Observable
import net.chrisfey.githubjobs.repository.GithubJob
import net.chrisfey.githubjobs.repository.IGithubJobRepository

class FakeGithubJobRepository : IGithubJobRepository{

    var searchJobs = emptyList<GithubJob>()

    override fun searchJobs(description: String, location: String): Observable<List<GithubJob>> {
        return Observable.just(searchJobs)
    }
}