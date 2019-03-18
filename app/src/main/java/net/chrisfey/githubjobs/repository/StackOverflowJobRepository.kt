package net.chrisfey.stackOverflowjobs.repository

import io.reactivex.Observable
import net.chrisfey.githubjobs.repository.networking.RssJob
import net.chrisfey.githubjobs.repository.networking.ScrapedStackOverflowJobResponse
import net.chrisfey.githubjobs.repository.networking.StackOverflowRssFeedJobHttpClient
import net.chrisfey.githubjobs.repository.networking.StackOverflowScreenScrapeJobHttpClient


class StackOverflowJobRepository(
    val rssFeed: StackOverflowRssFeedJobHttpClient,
    val scraper: StackOverflowScreenScrapeJobHttpClient
) {

    fun searchJobs(description: String, location: String) =
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


    fun viewJob(uri: String): Observable<ScrapedStackOverflowJobResponse> {
        return scraper.viewJob(uri)
    }
}

private fun RssJob.toStackOverflowJob(scrapedJob: ScrapedStackOverflowJobResponse? = null) =
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