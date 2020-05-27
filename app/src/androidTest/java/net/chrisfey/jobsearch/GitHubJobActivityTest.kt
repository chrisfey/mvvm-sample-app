package net.chrisfey.jobsearch

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.runner.AndroidJUnit4
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import net.chrisfey.jobsearch.data.JOB1
import net.chrisfey.jobsearch.di.DaggerAppComponent
import net.chrisfey.jobsearch.fakes.FakeGithubJobRepository
import net.chrisfey.jobsearch.jobsearch.detail.github.GitHubJobActivity
import net.chrisfey.jobsearch.repository.toGitHubJob
import net.chrisfey.jobsearch.rx.RxSchedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module


@RunWith(AndroidJUnit4::class)
class GitHubJobActivityTest {

    private lateinit var app : JobsApplication
    val fakeGithubRepository = FakeGithubJobRepository()

    @Before
    fun setup() {

        val fakeRxSchedulers = object : RxSchedulers {
            override fun computation() = Schedulers.trampoline()
            override fun io() = Schedulers.trampoline()
            override fun ui() = Schedulers.trampoline()
        }

        loadKoinModules(
            module {
                single(override = true) { fakeRxSchedulers }
                single(override = true) { fakeGithubRepository }
            })

        val testAppComponent = DaggerAppComponent.builder()
            .build()

        app = ApplicationProvider.getApplicationContext()

        testAppComponent.inject(app)
    }

    @Test
    fun displayAJob(){
        fakeGithubRepository.viewJob = Single.just(JOB1.toGitHubJob())

        ActivityScenario.launch<GitHubJobActivity>(GitHubJobActivity.newIntent( app, "http://joburl"))

        onView(withId(R.id.jobTitle))
            .check(matches(withText("Best JobViewState Ever 1")))
    }


}