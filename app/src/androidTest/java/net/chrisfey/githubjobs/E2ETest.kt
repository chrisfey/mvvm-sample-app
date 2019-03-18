package net.chrisfey.githubjobs


import android.app.Activity
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import net.chrisfey.githubjobs.di.DaggerViewModelInjector
import net.chrisfey.githubjobs.di.ViewModelInjector
import net.chrisfey.githubjobs.view.search.JobListAdapter
import net.chrisfey.githubjobs.view.search.JobSearchActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


class InjectionActivityTestRule<T : Activity>(val viewModelInjector: ViewModelInjector, activityClass: Class<T>) :
    ActivityTestRule<T>(activityClass) {

    override fun beforeActivityLaunched() {
        super.beforeActivityLaunched()
        // setup test component before activity launches
        val app = ApplicationProvider.getApplicationContext<GithubJobsApplication>()
        app.viewModelInjector = viewModelInjector
    }
}

@RunWith(AndroidJUnit4::class)
@LargeTest
class ChangeTextBehaviorTest {

    var fakeGithubClient = FakeGithubJobHttpClient()

    var viewModelInjector = DaggerViewModelInjector.builder()
        .networkModule(FakeNetworkModule(fakeGithubClient))
        .build()

    @get:Rule
    var activityRule = InjectionActivityTestRule(viewModelInjector, JobSearchActivity::class.java)


    @Test
    fun showSingleJob() {
        onView(withText("Try Searching")).check(matches(isDisplayed()))

        fakeGithubClient.searchJobs = listOf(JOB1)

        onView(withId(R.id.searchBtn))
            .perform(click())


        onView(withId(R.id.jobList)).perform(RecyclerViewActions.scrollToPosition<JobListAdapter.JobViewHolder>(0))
        onView(withText(JOB1.title)).check(matches(isDisplayed()))
        onView(withText(JOB1.company)).check(matches(isDisplayed()))


        fakeGithubClient.searchJobs = listOf(JOB1, JOB2)

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