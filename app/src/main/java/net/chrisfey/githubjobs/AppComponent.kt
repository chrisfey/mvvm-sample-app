package net.chrisfey.githubjobs

import dagger.Component
import dagger.Module
import dagger.Provides
import net.chrisfey.githubjobs.di.NetworkModule
import net.chrisfey.githubjobs.di.RxModule
import net.chrisfey.githubjobs.repository.IGithubJobRepository
import net.chrisfey.githubjobs.rx.RxSchedulers
import net.chrisfey.githubjobs.view.detail.github.GithubJobViewModelFactory
import net.chrisfey.githubjobs.view.detail.stackoverflow.StackOverflowJobViewModelFactory
import net.chrisfey.githubjobs.view.search.JobSearchViewModelFactory
import net.chrisfey.githubjobs.repository.IStackOverflowJobRepository
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, RxModule::class, ViewModelFactoryModule::class])
internal interface AppComponent {
    fun inject(app: JobsApplication)
}

@Module
open class ViewModelFactoryModule {
    @Provides
    open fun jobSearchViewModelFactory(stackOverflowRepository: IStackOverflowJobRepository, githubRepository: IGithubJobRepository, schedulers: RxSchedulers) =
        JobSearchViewModelFactory(stackOverflowRepository, githubRepository, schedulers)

    @Provides
    open fun stackOverflowJobViewModelFactory(stackOverflowRepository: IStackOverflowJobRepository) =
        StackOverflowJobViewModelFactory(stackOverflowRepository)

    @Provides
    open fun githubJobViewModelFactory(githubRepository: IGithubJobRepository) =
        GithubJobViewModelFactory(githubRepository)
}
