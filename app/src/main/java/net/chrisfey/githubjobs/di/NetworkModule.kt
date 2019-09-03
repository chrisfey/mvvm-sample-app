package net.chrisfey.githubjobs.di

import dagger.Module
import dagger.Provides
import net.chrisfey.githubjobs.repository.GithubJobRepository
import net.chrisfey.githubjobs.repository.IGithubJobRepository
import net.chrisfey.githubjobs.repository.IStackOverflowJobRepository
import net.chrisfey.githubjobs.repository.StackOverflowJobRepository
import net.chrisfey.githubjobs.repository.networking.GithubJobHttpClient
import net.chrisfey.githubjobs.repository.networking.StackOverflowRssFeedJobHttpClient
import net.chrisfey.githubjobs.repository.networking.StackOverflowScreenScrapeJobHttpClient
import net.chrisfey.githubjobs.utils.Jackson
import okhttp3.OkHttpClient
import pl.droidsonroids.retrofit2.JspoonConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory


@Module
open class NetworkModule {


    @Provides
    open fun okhttpClient(): OkHttpClient = OkHttpClient.Builder()
        .build()


    @Provides
    open fun githubJobHttpClient(okhttpClient : OkHttpClient): GithubJobHttpClient = Retrofit.Builder()
        .client(okhttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(JacksonConverterFactory.create(Jackson.mapper))
        .baseUrl("https://jobs.github.com/")
        .build()
        .create(GithubJobHttpClient::class.java)

    @Provides
    open fun stackOverflowRssFeedJobHttpClient(okhttpClient : OkHttpClient): StackOverflowRssFeedJobHttpClient = Retrofit.Builder()
        .client(okhttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl("https://stackoverflow.com/")
        .addConverterFactory(JacksonConverterFactory.create(Jackson.xmlMapper))
        .build()
        .create(StackOverflowRssFeedJobHttpClient::class.java)

    @Provides
    open fun stackOverflowScreenScrapeJobHttpClient(okhttpClient : OkHttpClient): StackOverflowScreenScrapeJobHttpClient = Retrofit.Builder()
        .client(okhttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl("https://stackoverflow.com/")
        .addConverterFactory(JspoonConverterFactory.create()).build()
        .create(StackOverflowScreenScrapeJobHttpClient::class.java)

    @Provides
    open fun githubJobRepository(githubJobClient: GithubJobHttpClient): IGithubJobRepository =
        GithubJobRepository(githubJobClient)

    @Provides
    open fun stackOverflowJobRepository(rssClient: StackOverflowRssFeedJobHttpClient, scrapeClient : StackOverflowScreenScrapeJobHttpClient): IStackOverflowJobRepository =
        StackOverflowJobRepository(rssClient, scrapeClient)

}