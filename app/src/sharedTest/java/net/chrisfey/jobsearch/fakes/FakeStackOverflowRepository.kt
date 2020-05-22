package net.chrisfey.jobsearch.fakes

import io.reactivex.Observable
import net.chrisfey.jobsearch.repository.IStackOverflowJobRepository
import net.chrisfey.jobsearch.repository.StackOverflowJob
import net.chrisfey.jobsearch.repository.networking.StackOverflowScrapedJobResponse

class FakeStackOverflowRepository : IStackOverflowJobRepository {
    override fun viewJob(uri: String): Observable<StackOverflowScrapedJobResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var searchJobs = emptyList<StackOverflowJob>()

    override fun searchJobs(description: String, location: String): Observable<List<StackOverflowJob>> {
        return Observable.just(searchJobs)
    }
}