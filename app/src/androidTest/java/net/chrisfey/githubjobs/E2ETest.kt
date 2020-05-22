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
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import net.chrisfey.githubjobs.data.JOB1
import net.chrisfey.githubjobs.di.DaggerAppComponent
import net.chrisfey.githubjobs.fakes.FakeGithubJobRepository
import net.chrisfey.githubjobs.fakes.FakeStackOverflowRepository
import net.chrisfey.githubjobs.repository.IGithubJobRepository
import net.chrisfey.githubjobs.repository.IStackOverflowJobRepository
import net.chrisfey.githubjobs.repository.toGitHubJob
import net.chrisfey.githubjobs.repository.toGitHubJobs
import net.chrisfey.githubjobs.rx.RxSchedulers
import net.chrisfey.githubjobs.view.search.JobListAdapter
import net.chrisfey.githubjobs.view.search.JobSearchActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module


@RunWith(AndroidJUnit4::class)
@LargeTest
class E2ETest {

    val fakeGithubRepository = FakeGithubJobRepository()
    val fakeStackOverflowRepository = FakeStackOverflowRepository()
    val fakeRxSchedulers = object : RxSchedulers {
        override fun computation() = Schedulers.trampoline()
        override fun io() = Schedulers.trampoline()
        override fun ui() = Schedulers.trampoline()
    }

    @Before
    fun setup() {


        loadKoinModules(module {
            single<RxSchedulers>(override = true) { fakeRxSchedulers }
            single<IGithubJobRepository>(override = true) { fakeGithubRepository }
            single<IStackOverflowJobRepository>(override = true) { fakeStackOverflowRepository }
        })


        val testAppComponent = DaggerAppComponent.builder().build()

        val app = ApplicationProvider.getApplicationContext<JobsApplication>()

        testAppComponent.inject(app)
    }

    @Test
    fun happyPathGithub() {
        fakeGithubRepository.searchJobs = Single.just(listOf(JOB1).toGitHubJobs())
        fakeGithubRepository.viewJob = Single.just(JOB1.toGitHubJob())

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
