package net.chrisfey.githubjobs.di

import dagger.Module
import dagger.Provides
import net.chrisfey.Logins.view.login.LoginViewModelFactory
import net.chrisfey.githubjobs.repository.IGithubJobRepository
import net.chrisfey.githubjobs.repository.IStackOverflowJobRepository
import net.chrisfey.githubjobs.rx.RxSchedulers
import net.chrisfey.githubjobs.view.detail.github.GithubJobViewModelFactory
import net.chrisfey.githubjobs.view.detail.stackoverflow.StackOverflowJobViewModelFactory
import org.koin.core.KoinComponent
import org.koin.core.inject


@Module
open class ViewModelFactoryModule : KoinComponent {
    private val githubRepository: IGithubJobRepository by inject()
    private val stackOverflowRepository: IStackOverflowJobRepository by inject()
    private val rxSchedulers: RxSchedulers by inject()

    @Provides
    open fun stackOverflowJobViewModelFactory() =
        StackOverflowJobViewModelFactory(stackOverflowRepository, rxSchedulers)

    @Provides
    open fun githubJobViewModelFactory() =
        GithubJobViewModelFactory(githubRepository, rxSchedulers)

    @Provides
    open fun loginViewModelFactory() =
        LoginViewModelFactory()
}
