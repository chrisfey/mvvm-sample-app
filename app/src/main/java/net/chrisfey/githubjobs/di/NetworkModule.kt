package net.chrisfey.githubjobs.di

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import net.chrisfey.githubjobs.repository.GithubJobRepository
import net.chrisfey.githubjobs.repository.IGithubJobRepository
import net.chrisfey.githubjobs.repository.IStackOverflowJobRepository
import net.chrisfey.githubjobs.repository.StackOverflowJobRepository
import net.chrisfey.githubjobs.repository.networking.GithubJobHttpClient
import net.chrisfey.githubjobs.repository.networking.StackOverflowRssFeedJobHttpClient
import net.chrisfey.githubjobs.repository.networking.StackOverflowScreenScrapeJobHttpClient
import net.chrisfey.githubjobs.rx.RxSchedulers
import net.chrisfey.githubjobs.utils.Jackson
import okhttp3.OkHttpClient
import org.koin.dsl.module
import pl.droidsonroids.retrofit2.JspoonConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

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
    single<OkHttpClient> { OkHttpClient.Builder().build() }
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
