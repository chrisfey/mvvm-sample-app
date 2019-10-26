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
import org.koin.dsl.module.module
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import pl.droidsonroids.retrofit2.JspoonConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory


val networkModuleKoin = module {
    single<OkHttpClient> { OkHttpClient.Builder().build() }
}

@Module
open class NetworkModule : KoinComponent{

    private val okHttpClient: OkHttpClient by inject()

    @Provides
    open fun githubJobHttpClient(): GithubJobHttpClient = Retrofit.Builder()
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(JacksonConverterFactory.create(Jackson.mapper))
        .baseUrl("https://jobs.github.com/")
        .build()
        .create(GithubJobHttpClient::class.java)

    @Provides
    open fun stackOverflowRssFeedJobHttpClient(): StackOverflowRssFeedJobHttpClient = Retrofit.Builder()
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl("https://stackoverflow.com/")
        .addConverterFactory(JacksonConverterFactory.create(Jackson.xmlMapper))
        .build()
        .create(StackOverflowRssFeedJobHttpClient::class.java)

    @Provides
    open fun stackOverflowScreenScrapeJobHttpClient(): StackOverflowScreenScrapeJobHttpClient = Retrofit.Builder()
        .client(okHttpClient)
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