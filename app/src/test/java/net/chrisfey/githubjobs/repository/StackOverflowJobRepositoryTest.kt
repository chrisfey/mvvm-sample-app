package net.chrisfey.githubjobs.repository

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable.just
import net.chrisfey.githubjobs.data.STACK_OVERFLOW_RSS_FEED_RESPONSE_1
import net.chrisfey.githubjobs.repository.networking.StackOverflowRssFeedJobHttpClient
import net.chrisfey.githubjobs.repository.networking.StackOverflowScrapedJobResponse
import net.chrisfey.githubjobs.repository.networking.StackOverflowScreenScrapeJobHttpClient
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class StackOverflowJobRepositoryTest {

    @Test
    fun getsJobs() {
        val rssClient = mockk<StackOverflowRssFeedJobHttpClient>()
        val scraperClient = mockk<StackOverflowScreenScrapeJobHttpClient>()


        every { rssClient.searchJobs("java", "london") } returns just(STACK_OVERFLOW_RSS_FEED_RESPONSE_1)

        every { scraperClient.viewJob("https://something.com") } returns just(
            StackOverflowScrapedJobResponse(companyImage = "image")
        )

        val repo = StackOverflowJobRepository(rssClient, scraperClient)

        val result = repo.searchJobs("java", "london")
            .blockingFirst()


        assertThat(result[0]).isEqualToComparingFieldByField(
            StackOverflowJob(
                link = "https://something.com",
                title = "title",
                description = "description",
                company = "company",
                companyImage = "image"
            )
        )

        verify(exactly = 1) { rssClient.searchJobs("java", "london") }
    }
}