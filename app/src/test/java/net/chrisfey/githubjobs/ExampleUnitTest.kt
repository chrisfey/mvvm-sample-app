package net.chrisfey.githubjobs

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
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
    fun flower(){
        val xmlMapper = XmlMapper()
        val poppy = xmlMapper.readValue(xml, Flower::class.java)

        assertThat(poppy.name)
            .isEqualTo("Poppy")
    }
    @Test
    fun item1(){
        val xmlMapper = XmlMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        val rss = xmlMapper.readValue(stackoverflow, Rss::class.java)

        assertThat(rss.channel!!.item!![0].title)
            .isEqualTo("title1")
    }
}

class Flower {
     val name: String? = null
     val color: Color? = null
     val petals: Int? = null
    // getters and setters
}

class Rss{
    val channel : Channel? = null
}
class Channel{

    @JacksonXmlElementWrapper(useWrapping = false)
    val item : List<Item1>? = null
}
class Item1{
    val link:String? = null
    val title:String? = null
    val description:String? = null
    val author:Author? = null
}
class Author{
    val name: String? = null
}

enum class Color {
    PINK, BLUE, YELLOW, RED
}

val xml = """
<Flower>
    <name>Poppy</name>
    <color>RED</color>
    <petals>9</petals>
</Flower>
""".trimIndent()

val item1 = """
        <item>
            <guid isPermaLink="false">236707</guid>
            <link>
                https://stackoverflow.com/jobs/236707/software-engineering-team-lead-groovy-java-adaptavist?a=1hnOOns2XvoI
            </link>
            <a10:author>
                <a10:name>Adaptavist</a10:name>
            </a10:author>
            <category>java</category>
            <category>groovy</category>
            <category>gradle</category>
            <category>geb</category>
            <category>javascript</category>
            <title>title1</title>
            <description>A short description</description>
            <pubDate>Mon, 04 Mar 2019 15:22:08 Z</pubDate>
            <a10:updated>2019-03-04T15:22:08Z</a10:updated>
            <location xmlns="http://stackoverflow.com/jobs/">London, UK</location>
        </item>

""".trimIndent()
val stackoverflow ="""
<?xml version="1.0" encoding="utf-8"?>
<rss xmlns:a10="http://www.w3.org/2005/Atom" version="2.0">
    <channel xmlns:os="http://a9.com/-/spec/opensearch/1.1/">
        <title>java jobs in london - Stack Overflow</title>
        <link>https://stackoverflow.com/jobs</link>
        <description>java jobs in london - Stack Overflow</description>
        <image>
            <url>http://cdn.sstatic.net/Sites/stackoverflow/img/favicon.ico?v=4f32ecc8f43d</url>
            <title>java jobs in london - Stack Overflow</title>
            <link>https://stackoverflow.com/jobs</link>
        </image>
        <os:totalResults>171</os:totalResults>
        $item1
        <item>
            <guid isPermaLink="false">206628</guid>
            <link>
                https://stackoverflow.com/jobs/206628/senior-technical-lead-java-newton-investment-hays-plc?a=17ioduEWOlzy
            </link>
            <a10:author>
                <a10:name>Hays plc</a10:name>
            </a10:author>
            <category>java</category>
            <category>java-ee</category>
            <category>java-8</category>
            <category>web</category>
            <category>api-design</category>
            <title>Senior Technical Lead (Java) - Newton Investment Management - Central London at Hays plc (London,
                UK)
            </title>
            <description>&lt;p&gt;Newton IM (of BNY Mellon) are seeking an experienced agile leader / developer to
                spearhead the design &amp;amp; delivery of their key front office investment platform.&lt;/p&gt;&lt;br /&gt;&lt;p&gt;&lt;br&gt;Hands-on
                development role - delivering high quality technical solutions with emphasis on automation and
                continuous integration - as well as offering coaching style leadership aimed at motivated and empowering
                this small, dynamic agile delivery team.&lt;/p&gt;&lt;br /&gt;&lt;p&gt;Tech stack includes:&lt;br&gt;Java
                8 / EE (APIs, cloud, messaging, integration...), Web Frameworks, some front end (inc. Html/CSS,
                JavaScript)&lt;/p&gt;&lt;br /&gt;&lt;p&gt;Professional, friendly and collegiate environment, offering
                competitive benefits package.&lt;/p&gt;
            </description>
            <pubDate>Mon, 04 Mar 2019 21:22:57 Z</pubDate>
            <a10:updated>2019-03-04T21:22:57Z</a10:updated>
            <location xmlns="http://stackoverflow.com/jobs/">London, UK</location>
        </item>
    </channel>
</rss>
""".trimIndent()