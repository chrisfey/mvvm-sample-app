package net.chrisfey.jobsearch.di

import dagger.Module
import dagger.Provides
import net.chrisfey.jobsearch.postlogon.detail.github.GithubJobViewModelFactory
import net.chrisfey.jobsearch.postlogon.detail.stackoverflow.StackOverflowJobViewModelFactory
import net.chrisfey.jobsearch.repository.IGithubJobRepository
import net.chrisfey.jobsearch.repository.IStackOverflowJobRepository
import net.chrisfey.jobsearch.rx.RxSchedulers
import org.koin.core.KoinComponent
import org.koin.core.inject


@Module
open class ViewModelFactoryModule : KoinComponent {
    private val githubRepository: IGithubJobRepository by inject()
    private val stackOverflowRepository: IStackOverflowJobRepository by inject()
    private val rxSchedulers: RxSchedulers by inject()

    @Provides
    open fun stackOverflowJobViewModelFactory() =
        StackOverflowJobViewModelFactory(
            stackOverflowRepository,
            rxSchedulers
        )

    @Provides
    open fun githubJobViewModelFactory() =
        GithubJobViewModelFactory(githubRepository, rxSchedulers)

}
