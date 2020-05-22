package net.chrisfey.githubjobs.fakes

import io.reactivex.Single
import net.chrisfey.githubjobs.repository.GithubJob
import net.chrisfey.githubjobs.repository.IGithubJobRepository

class FakeGithubJobRepository : IGithubJobRepository {
    lateinit var searchJobs: Single<List<GithubJob>>
    lateinit var viewJob: Single<GithubJob>

    override fun viewJob(jobId: String) = viewJob

    override fun searchJobs(description: String, location: String) = searchJobs

}