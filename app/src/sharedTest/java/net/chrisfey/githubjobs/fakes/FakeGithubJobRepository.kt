package net.chrisfey.githubjobs.fakes

import io.reactivex.Observable
import net.chrisfey.githubjobs.repository.GithubJob
import net.chrisfey.githubjobs.repository.IGithubJobRepository

class FakeGithubJobRepository : IGithubJobRepository {
    lateinit var searchJobs: Observable<List<GithubJob>>
    lateinit var viewJob: Observable<GithubJob>

    override fun viewJob(jobId: String) = viewJob

    override fun searchJobs(description: String, location: String) = searchJobs

}