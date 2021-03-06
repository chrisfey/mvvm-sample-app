package net.chrisfey.jobsearch

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.runner.AndroidJUnit4
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import net.chrisfey.jobsearch.data.JOB1
import net.chrisfey.jobsearch.data.JOB2
import net.chrisfey.jobsearch.data.STACK_OVERFLOW_JOB1
import net.chrisfey.jobsearch.data.STACK_OVERFLOW_JOB2
import net.chrisfey.jobsearch.di.DaggerAppComponent
import net.chrisfey.jobsearch.fakes.FakeGithubJobRepository
import net.chrisfey.jobsearch.fakes.FakeStackOverflowRepository
import net.chrisfey.jobsearch.postlogon.PostLogonActivity
import net.chrisfey.jobsearch.postlogon.search.JobListAdapter
import net.chrisfey.jobsearch.repository.IGithubJobRepository
import net.chrisfey.jobsearch.repository.IStackOverflowJobRepository
import net.chrisfey.jobsearch.repository.toGitHubJobs
import net.chrisfey.jobsearch.rx.RxSchedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module


@RunWith(AndroidJUnit4::class)
class PostLogonActivityTest {

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
    fun loadingState() {
        ActivityScenario.launch(PostLogonActivity::class.java)

        onView(withId(R.id.trySearchingTxt))
            .check(matches(withText("Try Searching")))
    }

    @Test
    fun searchNoJobs() {
        ActivityScenario.launch(PostLogonActivity::class.java)
        fakeGithubRepository.searchJobs = Single.just(listOf())
        onView(withId(R.id.descriptionEditText))
            .perform(typeText("java"))
        onView(withId(R.id.locationEditText))
            .perform(typeText("london"))
        onView(withId(R.id.searchBtn))
            .perform(click())

        onView(withId(R.id.noResultsTxt))
            .check(matches(isDisplayed()))
            .check(matches(withText("No jobs found matching these criteria")))
    }

    @Test
    fun showJobsFromOneSource() {
        ActivityScenario.launch(PostLogonActivity::class.java)
        onView(withText("Try Searching")).check(matches(isDisplayed()))

        fakeGithubRepository.searchJobs = Single.just(listOf(JOB1).toGitHubJobs())
        onView(withId(R.id.searchBtn))
            .perform(click())
        onView(withId(R.id.jobList)).perform(RecyclerViewActions.scrollToPosition<JobListAdapter.JobViewHolder>(0))
        onView(withText(JOB1.title)).check(matches(isDisplayed()))
        onView(withText(JOB1.company)).check(matches(isDisplayed()))


        fakeGithubRepository.searchJobs = Single.just(listOf(JOB1, JOB2).toGitHubJobs())

        onView(withId(R.id.searchBtn))
            .perform(click())

        onView(withId(R.id.jobList)).perform(RecyclerViewActions.scrollToPosition<JobListAdapter.JobViewHolder>(0))
        onView(withText(JOB1.title)).check(matches(isDisplayed()))
        onView(withText(JOB1.company)).check(matches(isDisplayed()))

        onView(withId(R.id.jobList)).perform(RecyclerViewActions.scrollToPosition<JobListAdapter.JobViewHolder>(2))
        onView(withText(JOB2.title)).check(matches(isDisplayed()))
        onView(withText(JOB2.company)).check(matches(isDisplayed()))
    }

    @Test
    fun showJobsFromMultipleSources() {
        ActivityScenario.launch(PostLogonActivity::class.java)
        onView(withText("Try Searching")).check(matches(isDisplayed()))

        fakeGithubRepository.searchJobs = Single.just(listOf(JOB1, JOB2).toGitHubJobs())
        fakeStackOverflowRepository.searchJobs = listOf(STACK_OVERFLOW_JOB1, STACK_OVERFLOW_JOB2)

        onView(withId(R.id.searchBtn))
            .perform(click())

        onView(withId(R.id.jobList)).perform(RecyclerViewActions.scrollToPosition<JobListAdapter.JobViewHolder>(0))
        onView(withText(JOB1.title)).check(matches(isDisplayed()))
        onView(withText(JOB1.company)).check(matches(isDisplayed()))

        onView(withId(R.id.jobList)).perform(RecyclerViewActions.scrollToPosition<JobListAdapter.JobViewHolder>(2))
        onView(withText(JOB2.title)).check(matches(isDisplayed()))
        onView(withText(JOB2.company)).check(matches(isDisplayed()))

        onView(withId(R.id.jobList)).perform(RecyclerViewActions.scrollToPosition<JobListAdapter.JobViewHolder>(4))
        onView(withText(STACK_OVERFLOW_JOB1.title)).check(matches(isDisplayed()))
        onView(withText(STACK_OVERFLOW_JOB1.company)).check(matches(isDisplayed()))

        onView(withId(R.id.jobList)).perform(RecyclerViewActions.scrollToPosition<JobListAdapter.JobViewHolder>(6))
        onView(withText(STACK_OVERFLOW_JOB2.title)).check(matches(isDisplayed()))
        onView(withText(STACK_OVERFLOW_JOB2.company)).check(matches(isDisplayed()))
    }


}