package net.chrisfey.jobsearch.di.koin

import net.chrisfey.jobsearch.repository.GithubJobRepository
import net.chrisfey.jobsearch.repository.IGithubJobRepository
import net.chrisfey.jobsearch.repository.IStackOverflowJobRepository
import net.chrisfey.jobsearch.repository.StackOverflowJobRepository
import net.chrisfey.jobsearch.repository.networking.GithubJobHttpClient
import net.chrisfey.jobsearch.repository.networking.StackOverflowRssFeedJobHttpClient
import net.chrisfey.jobsearch.repository.networking.StackOverflowScreenScrapeJobHttpClient
import net.chrisfey.jobsearch.rx.RxSchedulers
import net.chrisfey.jobsearch.utils.Jackson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import pl.droidsonroids.retrofit2.JspoonConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import timber.log.Timber


val networkModule = module {
    single {
        val logging = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Timber.tag("OkHttp").d(message)
            }
        })
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }
    single<GithubJobHttpClient> {
        Retrofit.Builder()
            .client(get())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(get<RxSchedulers>().io()))
            .addConverterFactory(JacksonConverterFactory.create(Jackson.mapper))
            .baseUrl("https://jobs.github.com/")
            .build()
            .create(GithubJobHttpClient::class.java)
    }
    single<IGithubJobRepository> {
        GithubJobRepository(
            get()
        )
    }
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