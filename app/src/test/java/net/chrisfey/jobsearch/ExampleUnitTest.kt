package net.chrisfey.jobsearch

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import net.chrisfey.jobsearch.data.STACK_OVERFLOW_RSS_FEED
import net.chrisfey.jobsearch.repository.networking.StackOverflowRssFeedResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert.assertEquals
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun item1(){
        val xmlMapper = XmlMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        val rss = xmlMapper.readValue(STACK_OVERFLOW_RSS_FEED, StackOverflowRssFeedResponse::class.java)

        assertThat(rss.channel!!.item!![0].title)
            .isEqualTo("title1")
    }
}

