package net.chrisfey.githubjobs.di

import dagger.Component
import net.chrisfey.githubjobs.view.detail.stackoverflow.StackOverflowJobViewModel
import net.chrisfey.githubjobs.view.search.JobSearchViewModel


@Component(modules = [NetworkModule::class])
interface ViewModelInjector {
    fun inject(app: JobSearchViewModel)
    fun inject(app: StackOverflowJobViewModel)
}
