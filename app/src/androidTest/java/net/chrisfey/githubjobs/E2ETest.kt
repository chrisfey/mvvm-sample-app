package net.chrisfey.githubjobs


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import dagger.Provides
import net.chrisfey.githubjobs.data.JOB1
import net.chrisfey.githubjobs.data.JOB2
import net.chrisfey.githubjobs.fakes.FakeGithubJobRepository
import net.chrisfey.githubjobs.fakes.FakeStackOverflowRepository
import net.chrisfey.githubjobs.repository.IGithubJobRepository
import net.chrisfey.githubjobs.repository.toGitHubJobs
import net.chrisfey.githubjobs.view.search.JobListAdapter
import net.chrisfey.githubjobs.view.search.JobSearchActivity
import net.chrisfey.githubjobs.view.search.JobSearchViewModelFactory
import net.chrisfey.stackOverflowjobs.repository.IStackOverflowJobRepository
import org.hamcrest.Description
import org.hamcrest.Matcher
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
        val fakeViewModelFactory = object : ViewModelFactoryModule() {
            @Provides
            override fun jobSearchViewModelFactory(stackOverflowRepository: IStackOverflowJobRepository, githubRepository: IGithubJobRepository) =
                JobSearchViewModelFactory(fakeStackOverflowRepository, fakeGithubRepository)
        }

        val testAppComponent = DaggerAppComponent.builder()
            .viewModelFactoryModule(fakeViewModelFactory)
            .build()

        val app = ApplicationProvider.getApplicationContext<JobsApplication>()

        testAppComponent.inject(app)
    }

    @Test
    fun showSingleJob() {
        ActivityScenario.launch(JobSearchActivity::class.java)
        //TODO maybe need to launch after the app was injected???
        onView(withText("Try Searching")).check(matches(isDisplayed()))

        fakeGithubRepository.searchJobs = listOf(JOB1).toGitHubJobs()

        onView(withId(R.id.searchBtn))
            .perform(click())


        onView(withId(R.id.jobList)).perform(RecyclerViewActions.scrollToPosition<JobListAdapter.JobViewHolder>(0))
        onView(withText(JOB1.title)).check(matches(isDisplayed()))
        onView(withText(JOB1.company)).check(matches(isDisplayed()))


        fakeGithubRepository.searchJobs = listOf(JOB1, JOB2).toGitHubJobs()

        onView(withId(R.id.searchBtn))
            .perform(click())

        onView(withId(R.id.jobList)).perform(RecyclerViewActions.scrollToPosition<JobListAdapter.JobViewHolder>(2))
        onView(withText(JOB2.title)).check(matches(isDisplayed()))
        onView(withText(JOB2.company)).check(matches(isDisplayed()))


    }
}

fun atPosition(position: Int, itemMatcher: Matcher<View>): Matcher<View> {
    checkNotNull(itemMatcher)
    return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("has item at position $position: ")
            itemMatcher.describeTo(description)
        }

        override fun matchesSafely(view: RecyclerView): Boolean {
            val viewHolder = view.findViewHolderForAdapterPosition(position)
                ?: // has no item on such position
                return false
            return itemMatcher.matches(viewHolder.itemView)
        }
    }
}