package net.chrisfey.githubjobs.repository.networking

import net.chrisfey.githubjobs.data.GITHUBJOB1
import net.chrisfey.githubjobs.utils.Jackson
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GithubJobResponseTest {
    @Test
    fun deserialiseTest() {
        val deserialised = Jackson.mapper.readValue(GITHUBJOB1, GithubJobResponse::class.java)

        assertThat(deserialised.company).isEqualTo("Angel")
        assertThat(deserialised.title).isEqualTo("Junior Web Developer - Shoreditch, London")
        assertThat(deserialised.company_logo).isEqualTo("https://jobs.github.com/rails/active_storage/blobs/angel-circle-01.png")
    }
}
