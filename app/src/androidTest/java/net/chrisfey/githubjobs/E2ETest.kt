package net.chrisfey.githubjobs



import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import dagger.Provides
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import net.chrisfey.githubjobs.data.JOB1
import net.chrisfey.githubjobs.di.NetworkModule
import net.chrisfey.githubjobs.di.RxModule
import net.chrisfey.githubjobs.fakes.FakeGithubJobRepository
import net.chrisfey.githubjobs.fakes.FakeStackOverflowRepository
import net.chrisfey.githubjobs.repository.IGithubJobRepository
import net.chrisfey.githubjobs.repository.IStackOverflowJobRepository
import net.chrisfey.githubjobs.repository.networking.GithubJobHttpClient
import net.chrisfey.githubjobs.repository.networking.StackOverflowRssFeedJobHttpClient
import net.chrisfey.githubjobs.repository.networking.StackOverflowScreenScrapeJobHttpClient
import net.chrisfey.githubjobs.repository.toGitHubJob
import net.chrisfey.githubjobs.repository.toGitHubJobs
import net.chrisfey.githubjobs.rx.RxSchedulers
import net.chrisfey.githubjobs.view.search.JobListAdapter
import net.chrisfey.githubjobs.view.search.JobSearchActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class E2ETest {

    val fakeGithubRepository = FakeGithubJobRepository()
    val fakeStackOverflowRepository = FakeStackOverflowRepository()

    @Before
    fun setup() {

        val fakeRxModule = object : RxModule() {
            @Provides
            override fun schedulers() = object : RxSchedulers {
                override fun computation() = Schedulers.trampoline()
                override fun io() = Schedulers.trampoline()
                override fun ui() = Schedulers.trampoline()
            }
        }
        val fakeNetworkModule = object : NetworkModule() {
            @Provides
            override fun githubJobRepository(githubJobClient: GithubJobHttpClient): IGithubJobRepository = fakeGithubRepository
            @Provides
            override fun stackOverflowJobRepository(rssClient: StackOverflowRssFeedJobHttpClient, scrapeClient : StackOverflowScreenScrapeJobHttpClient): IStackOverflowJobRepository = fakeStackOverflowRepository
        }

        val testAppComponent = DaggerAppComponent.builder()
            .networkModule(fakeNetworkModule)
            .rxModule(fakeRxModule)
            .build()

        val app = ApplicationProvider.getApplicationContext<JobsApplication>()

        testAppComponent.inject(app)
    }

    @Test
    fun happyPathGithub() {
        fakeGithubRepository.searchJobs = Observable.just(listOf(JOB1).toGitHubJobs())
        fakeGithubRepository.viewJob = Observable.just(JOB1.toGitHubJob())

        ActivityScenario.launch(JobSearchActivity::class.java)
        onView(withText("Try Searching")).check(matches(isDisplayed()))


        onView(withId(R.id.searchBtn))
            .perform(click())

        onView(withId(R.id.jobList))
            .perform(RecyclerViewActions.scrollToPosition<JobListAdapter.JobViewHolder>(0))

        onView(withText(JOB1.title)).perform(click())

        onView(withId(R.id.jobTitle))
            .check(matches(isDisplayed()))
            .check(matches(withText(JOB1.title)))

    }
}
