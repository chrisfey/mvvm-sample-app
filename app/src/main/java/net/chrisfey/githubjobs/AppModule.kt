package net.chrisfey.githubjobs

import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector
import net.chrisfey.githubjobs.view.detail.github.GitHubJobActivity
import net.chrisfey.githubjobs.view.detail.github.GithubJobActivityModule
import net.chrisfey.githubjobs.view.detail.stackoverflow.StackOverflowJobActivity
import net.chrisfey.githubjobs.view.detail.stackoverflow.StackOverflowJobActivityModule
import net.chrisfey.githubjobs.view.search.JobSearchActivity
import net.chrisfey.githubjobs.view.search.JobSearchActivityModule


@Module(includes = [AndroidInjectionModule::class])
internal abstract class AppModule {

    @ContributesAndroidInjector(modules = [JobSearchActivityModule::class])
    internal abstract fun jobSearchActivity(): JobSearchActivity


    @ContributesAndroidInjector(modules = [StackOverflowJobActivityModule::class])
    internal abstract fun stackOverflowJobActivity(): StackOverflowJobActivity

    @ContributesAndroidInjector(modules = [GithubJobActivityModule::class])
    internal abstract fun githubJobActivity(): GitHubJobActivity

}
