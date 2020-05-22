package net.chrisfey.jobsearch.data

import net.chrisfey.jobsearch.repository.networking.Author
import net.chrisfey.jobsearch.repository.networking.Channel
import net.chrisfey.jobsearch.repository.networking.RssJob
import net.chrisfey.jobsearch.repository.networking.StackOverflowRssFeedResponse

val STACK_OVERFLOW_RSS_FEED_RESPONSE_1 = StackOverflowRssFeedResponse(
    channel = Channel(
        item = listOf(

            RssJob(
                title = "title",
                description = "description",
                author = Author(name = "company"),
                link = "https://something.com"
            )
        )
    )
)