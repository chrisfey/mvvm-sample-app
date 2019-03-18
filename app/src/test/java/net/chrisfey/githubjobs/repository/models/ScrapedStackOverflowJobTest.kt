package net.chrisfey.githubjobs.repository.models

import net.chrisfey.githubjobs.repository.networking.ScrapedStackOverflowJob
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import pl.droidsonroids.jspoon.Jspoon
import pl.droidsonroids.jspoon.annotation.Selector


class ScrapedStackOverflowJobTest{

    @Test
    fun deserialiseTest() {


        val jspoon = Jspoon.create()
        val htmlAdapter = jspoon.adapter<ScrapedStackOverflowJob>(ScrapedStackOverflowJob::class.java!!)

        val deserialised = htmlAdapter.fromHtml(SO_HTML_JOB_PAGE)

        Assertions.assertThat(deserialised.companyImage).isEqualTo("https://i.stack.imgur.com/xb7qY.jpg")
    }

    @Test
    fun jspoonExample(){
        class Page {

            @Selector(value = "div.s-avatar img", attr = "src")
            var title: String? = null
//
//            var intList: List<Int>? = null
//            var imageSource: String? = null
        }
        val htmlContent = ("<div>"
                + "<p id='title'>Title</p>"
                + "<ul>"
                + "<li class='a'>1</li>"
                + "<li>2</li>"
                + "<li class='a'>3</li>"
                + "</ul>"
                + "<img id='image1' src='image.bmp' />"
                + "</div>")

        val jspoon = Jspoon.create()
        val htmlAdapter = jspoon.adapter<Page>(Page::class.java!!)

        val page = htmlAdapter.fromHtml(SO_HTML_JOB_PAGE)
        assertThat(page.title).isEqualTo("test")
//title = "Title"; intList = [1, 3]; imageSource = "image.bmp"
    }
}

val SO_HTML_JOB_PAGE = """
<!DOCTYPE html>
<div class="job-details--header">
    <div class="s-avatar">
        <img src="test"/>
    </div>
</div>
"""
