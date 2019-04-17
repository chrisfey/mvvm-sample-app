package net.chrisfey.githubjobs.di

import dagger.Module
import dagger.Provides
import net.chrisfey.githubjobs.repository.GithubJobRepository
import net.chrisfey.githubjobs.repository.IGithubJobRepository
import net.chrisfey.githubjobs.repository.networking.GithubJobHttpClient
import net.chrisfey.githubjobs.repository.networking.StackOverflowRssFeedJobHttpClient
import net.chrisfey.githubjobs.repository.networking.StackOverflowScreenScrapeJobHttpClient
import net.chrisfey.githubjobs.utils.Jackson
import net.chrisfey.stackOverflowjobs.repository.IStackOverflowJobRepository
import net.chrisfey.stackOverflowjobs.repository.StackOverflowJobRepository
import pl.droidsonroids.retrofit2.JspoonConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory





@Module
open class NetworkModule {




    @Provides
    open fun githubJobHttpClient(): GithubJobHttpClient = Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(JacksonConverterFactory.create(Jackson.mapper))
        .baseUrl("https://jobs.github.com/")
        .build()
        .create<GithubJobHttpClient>(GithubJobHttpClient::class.java)

    @Provides
    open fun stackOverflowRssFeedJobHttpClient(): StackOverflowRssFeedJobHttpClient = Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl("https://stackoverflow.com/")
        .addConverterFactory(JacksonConverterFactory.create(Jackson.xmlMapper))
        .build()
        .create<StackOverflowRssFeedJobHttpClient>(StackOverflowRssFeedJobHttpClient::class.java)

    @Provides
    open fun stackOverflowScreenScrapeJobHttpClient(): StackOverflowScreenScrapeJobHttpClient = Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl("https://stackoverflow.com/")
        .addConverterFactory(JspoonConverterFactory.create()).build()
        .create<StackOverflowScreenScrapeJobHttpClient>(StackOverflowScreenScrapeJobHttpClient::class.java)

    @Provides
    fun githubJobRepository(githubJobClient: GithubJobHttpClient): IGithubJobRepository =
        GithubJobRepository(githubJobClient)

    @Provides
    fun stackOverflowJobRepository(rssClient: StackOverflowRssFeedJobHttpClient, scrapeClient : StackOverflowScreenScrapeJobHttpClient): IStackOverflowJobRepository =
        StackOverflowJobRepository(rssClient, scrapeClient)

}