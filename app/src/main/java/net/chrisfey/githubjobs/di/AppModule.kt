package net.chrisfey.githubjobs.di

import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector
import net.chrisfey.githubjobs.view.detail.github.GitHubJobActivity
import net.chrisfey.githubjobs.view.detail.stackoverflow.StackOverflowJobActivity
import net.chrisfey.githubjobs.view.search.JobSearchActivity


@Module(includes = [AndroidInjectionModule::class])
internal abstract class AppModule {

    @ContributesAndroidInjector
    internal abstract fun jobSearchActivity(): JobSearchActivity


    @ContributesAndroidInjector
    internal abstract fun stackOverflowJobActivity(): StackOverflowJobActivity

    @ContributesAndroidInjector
    internal abstract fun githubJobActivity(): GitHubJobActivity

}
