package net.chrisfey.jobsearch.di

import com.google.firebase.auth.FirebaseAuth
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import net.chrisfey.jobsearch.repository.GithubJobRepository
import net.chrisfey.jobsearch.repository.IGithubJobRepository
import net.chrisfey.jobsearch.repository.IStackOverflowJobRepository
import net.chrisfey.jobsearch.repository.StackOverflowJobRepository
import net.chrisfey.jobsearch.repository.networking.GithubJobHttpClient
import net.chrisfey.jobsearch.repository.networking.StackOverflowRssFeedJobHttpClient
import net.chrisfey.jobsearch.repository.networking.StackOverflowScreenScrapeJobHttpClient
import net.chrisfey.jobsearch.rx.RxSchedulers
import net.chrisfey.jobsearch.utils.Jackson
import net.chrisfey.jobsearch.view.login.LoginViewModel
import net.chrisfey.jobsearch.view.search.JobSearchViewModel
import net.chrisfey.jobsearch.view.startup.StartupViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pl.droidsonroids.retrofit2.JspoonConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

val firebaseModule = module {
    single { FirebaseAuth.getInstance() }
}
val viewModelModule = module {
    viewModel { StartupViewModel(get()) }
    viewModel { JobSearchViewModel(get(), get(), get()) }
    viewModel { LoginViewModel(get()) }
}
val rxModule = module {
    single<RxSchedulers> {
        object : RxSchedulers {
            override fun computation() = Schedulers.computation()
            override fun io() = Schedulers.io()
            override fun ui() = AndroidSchedulers.mainThread()
        }
    }
}
val networkModuleKoin = module {
    single { OkHttpClient.Builder().build() }
    single<GithubJobHttpClient> {
        Retrofit.Builder()
            .client(get())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(get<RxSchedulers>().io()))
            .addConverterFactory(JacksonConverterFactory.create(Jackson.mapper))
            .baseUrl("https://jobs.github.com/")
            .build()
            .create(GithubJobHttpClient::class.java)
    }
    single<IGithubJobRepository> { GithubJobRepository(get()) }
    single<StackOverflowRssFeedJobHttpClient> {
        Retrofit.Builder()
            .client(get())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(get<RxSchedulers>().io()))
            .baseUrl("https://stackoverflow.com/")
            .addConverterFactory(JacksonConverterFactory.create(Jackson.xmlMapper))
            .build()
            .create(StackOverflowRssFeedJobHttpClient::class.java)
    }
    single<StackOverflowScreenScrapeJobHttpClient> {
        Retrofit.Builder()
            .client(get())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(get<RxSchedulers>().io()))
            .baseUrl("https://stackoverflow.com/")
            .addConverterFactory(JspoonConverterFactory.create()).build()
            .create(StackOverflowScreenScrapeJobHttpClient::class.java)
    }

    single<IStackOverflowJobRepository> {
        StackOverflowJobRepository(get(), get())
    }
}
