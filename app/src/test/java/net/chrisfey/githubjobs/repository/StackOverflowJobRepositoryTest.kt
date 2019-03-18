package net.chrisfey.githubjobs.repository

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import net.chrisfey.githubjobs.repository.networking.StackOverflowRssFeedJobHttpClient
import net.chrisfey.githubjobs.repository.networking.StackOverflowRssFeedResponse
import net.chrisfey.githubjobs.repository.networking.StackOverflowScreenScrapeJobHttpClient
import net.chrisfey.stackOverflowjobs.repository.StackOverflowJobRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class StackOverflowJobRepositoryTest{

    @Test
    fun getsJobs(){
        val rssClient = mockk<StackOverflowRssFeedJobHttpClient>()
        val scraperClient = mockk<StackOverflowScreenScrapeJobHttpClient>()
        every { rssClient.searchJobs("java","london") } returns Observable.just(StackOverflowRssFeedResponse())

        val repo = StackOverflowJobRepository(rssClient,scraperClient)

        val result = repo.searchJobs("java","london")


        assertThat(result)
            .isEqualTo("NOT DONE YET")

        verify(exactly = 1) { rssClient.searchJobs("java","london") }
    }
}