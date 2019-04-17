package net.chrisfey.githubjobs.data

import net.chrisfey.githubjobs.repository.networking.Author
import net.chrisfey.githubjobs.repository.networking.Channel
import net.chrisfey.githubjobs.repository.networking.RssJob
import net.chrisfey.githubjobs.repository.networking.StackOverflowRssFeedResponse

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