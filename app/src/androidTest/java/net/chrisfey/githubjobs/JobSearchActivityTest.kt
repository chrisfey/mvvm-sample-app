package net.chrisfey.githubjobs

import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import dagger.Provides
import net.chrisfey.githubjobs.fakes.FakeGithubJobRepository
import net.chrisfey.githubjobs.repository.IGithubJobRepository
import net.chrisfey.githubjobs.view.search.JobSearchActivity
import net.chrisfey.githubjobs.view.search.JobSearchViewModelFactory
import net.chrisfey.stackOverflowjobs.repository.IStackOverflowJobRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith



@RunWith(AndroidJUnit4::class)
class JobSearchActivityTest {


    @get:Rule
    val testRule = ActivityTestRule(JobSearchActivity::class.java)

    val fakeGithubRepository = FakeGithubJobRepository()

    @Before
    fun setup() {
        val fakeViewModelFactory = object : ViewModelFactoryModule() {
            @Provides
            override fun jobSearchViewModelFactory(stackOverflowRepository: IStackOverflowJobRepository, githubRepository: IGithubJobRepository) =
                JobSearchViewModelFactory(stackOverflowRepository, fakeGithubRepository)
        }



        val testAppComponent = DaggerAppComponent.builder()
            .viewModelFactoryModule(fakeViewModelFactory)
            .build()

        val app = ApplicationProvider.getApplicationContext<JobsApplication>()

        testAppComponent.inject(app)
    }

    @Test
    fun loadingState(){

        onView(withId(R.id.trySearchingTxt))
            .check(matches(withText("Try Searching")))

    }



}