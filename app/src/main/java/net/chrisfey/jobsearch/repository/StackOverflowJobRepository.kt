package net.chrisfey.jobsearch.repository

import io.reactivex.Observable
import net.chrisfey.jobsearch.repository.networking.RssJob
import net.chrisfey.jobsearch.repository.networking.StackOverflowRssFeedJobHttpClient
import net.chrisfey.jobsearch.repository.networking.StackOverflowScrapedJobResponse
import net.chrisfey.jobsearch.repository.networking.StackOverflowScreenScrapeJobHttpClient

interface IStackOverflowJobRepository {
    fun searchJobs (description: String, location: String) : Observable<List<StackOverflowJob>>
    fun viewJob(uri: String): Observable<StackOverflowScrapedJobResponse>
}

class StackOverflowJobRepository  (
    private val rssFeed: StackOverflowRssFeedJobHttpClient,
    private val scraper: StackOverflowScreenScrapeJobHttpClient
): IStackOverflowJobRepository {

    override fun searchJobs(description: String, location: String): Observable<List<StackOverflowJob>> =
        rssFeed.searchJobs(description, location)
            .map { rss -> rss.channel!!.item!! }
            .map { it.take(10) }
            .flatMap { list ->
                Observable.fromIterable(list)
                    .flatMap { enrichWithImage(it) }
                    .toList()
                    .toObservable()
            }

    private fun enrichWithImage(rssJob: RssJob) =
        viewJob(rssJob.link!!)
            .map { rssJob.toStackOverflowJob(it) }


    override fun viewJob(uri: String): Observable<StackOverflowScrapedJobResponse> {
        return scraper.viewJob(uri)
    }
}

private fun RssJob.toStackOverflowJob(scrapedJob: StackOverflowScrapedJobResponse? = null) =
    StackOverflowJob(
        link = link,
        title = title,
        description = description,
        company = author!!.name,
        companyImage = scrapedJob?.companyImage
    )


class StackOverflowJob(
    val link: String? = null,
    val title: String? = null,
    val description: String? = null,
    val company: String? = null,
    val companyImage: String? = null
)