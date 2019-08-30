package net.chrisfey.githubjobs

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.runner.AndroidJUnit4
import dagger.Provides
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import net.chrisfey.githubjobs.data.JOB1
import net.chrisfey.githubjobs.di.NetworkModule
import net.chrisfey.githubjobs.di.RxModule
import net.chrisfey.githubjobs.fakes.FakeGithubJobRepository
import net.chrisfey.githubjobs.repository.IGithubJobRepository
import net.chrisfey.githubjobs.repository.networking.GithubJobHttpClient
import net.chrisfey.githubjobs.repository.toGitHubJob
import net.chrisfey.githubjobs.rx.RxSchedulers
import net.chrisfey.githubjobs.view.detail.github.GitHubJobActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class GitHubJobActivityTest {

    lateinit var app : JobsApplication
    val fakeGithubRepository = FakeGithubJobRepository()

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
        }

        val testAppComponent = DaggerAppComponent.builder()
            .networkModule(fakeNetworkModule)
            .rxModule(fakeRxModule)
            .build()

        app = ApplicationProvider.getApplicationContext()

        testAppComponent.inject(app)
    }

    @Test
    fun displayAJob(){
        fakeGithubRepository.viewJob = Observable.just(JOB1.toGitHubJob())

        ActivityScenario.launch<GitHubJobActivity>(GitHubJobActivity.newIntent( app, "http://joburl"))

        onView(withId(R.id.jobTitle))
            .check(matches(withText("Best JobViewState Ever 1")))
    }


}