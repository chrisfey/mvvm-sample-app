package net.chrisfey.githubjobs.fakes

import io.reactivex.Observable
import net.chrisfey.githubjobs.repository.networking.StackOverflowScrapedJobResponse
import net.chrisfey.stackOverflowjobs.repository.IStackOverflowJobRepository
import net.chrisfey.stackOverflowjobs.repository.StackOverflowJob

class FakeStackOverflowRepository : IStackOverflowJobRepository{
    override fun viewJob(uri: String): Observable<StackOverflowScrapedJobResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var searchJobs = emptyList<StackOverflowJob>()

    override fun searchJobs(description: String, location: String): Observable<List<StackOverflowJob>> {
        return Observable.just(searchJobs)
    }
}