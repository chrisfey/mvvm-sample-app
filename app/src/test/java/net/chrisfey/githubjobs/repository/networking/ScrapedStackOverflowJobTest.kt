package net.chrisfey.githubjobs.repository.networking

import net.chrisfey.githubjobs.data.SO_HTML_JOB_PAGE
import org.assertj.core.api.Assertions
import org.junit.Test
import pl.droidsonroids.jspoon.Jspoon


class ScrapedStackOverflowJobTest{

    @Test
    fun deserialiseTest() {


        val jspoon = Jspoon.create()
        val htmlAdapter = jspoon.adapter(StackOverflowScrapedJobResponse::class.java)

        val deserialised = htmlAdapter.fromHtml(SO_HTML_JOB_PAGE)

        Assertions.assertThat(deserialised.companyImage).isEqualTo("https://i.stack.imgur.com/xb7qY.jpg")
    }

}
