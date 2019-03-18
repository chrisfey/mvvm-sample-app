package net.chrisfey.githubjobs.repository.models

import net.chrisfey.githubjobs.repository.networking.ScrapedStackOverflowJobResponse
import org.assertj.core.api.Assertions
import org.junit.Test
import pl.droidsonroids.jspoon.Jspoon


class ScrapedStackOverflowJobTest{

    @Test
    fun deserialiseTest() {


        val jspoon = Jspoon.create()
        val htmlAdapter = jspoon.adapter<ScrapedStackOverflowJobResponse>(ScrapedStackOverflowJobResponse::class.java!!)

        val deserialised = htmlAdapter.fromHtml(SO_HTML_JOB_PAGE)

        Assertions.assertThat(deserialised.companyImage).isEqualTo("https://i.stack.imgur.com/xb7qY.jpg")
    }

}

val SO_HTML_JOB_PAGE = """
<!DOCTYPE html>
<div class="job-details--header">
    <div class="s-avatar">
        <img src="https://i.stack.imgur.com/xb7qY.jpg"/>
    </div>
</div>
"""
