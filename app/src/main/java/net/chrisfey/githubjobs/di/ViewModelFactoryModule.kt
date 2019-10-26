package net.chrisfey.githubjobs.di

import dagger.Module
import dagger.Provides
import net.chrisfey.githubjobs.repository.IGithubJobRepository
import net.chrisfey.githubjobs.repository.IStackOverflowJobRepository
import net.chrisfey.githubjobs.rx.RxSchedulers
import net.chrisfey.githubjobs.view.detail.github.GithubJobViewModelFactory
import net.chrisfey.githubjobs.view.detail.stackoverflow.StackOverflowJobViewModelFactory
import net.chrisfey.githubjobs.view.search.JobSearchViewModelFactory
import okhttp3.OkHttpClient
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

@Module
open class ViewModelFactoryModule : KoinComponent{
    private val githubRepository: IGithubJobRepository by inject()


    @Provides
    open fun jobSearchViewModelFactory(stackOverflowRepository: IStackOverflowJobRepository, schedulers: RxSchedulers) =
        JobSearchViewModelFactory(stackOverflowRepository, githubRepository, schedulers)

    @Provides
    open fun stackOverflowJobViewModelFactory(stackOverflowRepository: IStackOverflowJobRepository) =
        StackOverflowJobViewModelFactory(stackOverflowRepository)

    @Provides
    open fun githubJobViewModelFactory() =
        GithubJobViewModelFactory(githubRepository)
}
