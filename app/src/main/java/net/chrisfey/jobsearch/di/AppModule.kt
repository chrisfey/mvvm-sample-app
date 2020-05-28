package net.chrisfey.jobsearch.di

import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector
import net.chrisfey.jobsearch.postlogon.PostLogonActivity
import net.chrisfey.jobsearch.postlogon.detail.github.GitHubJobActivity
import net.chrisfey.jobsearch.postlogon.detail.stackoverflow.StackOverflowJobActivity


@Module(includes = [AndroidInjectionModule::class])
internal abstract class AppModule {

    @ContributesAndroidInjector
    internal abstract fun jobSearchActivity(): PostLogonActivity


    @ContributesAndroidInjector
    internal abstract fun stackOverflowJobActivity(): StackOverflowJobActivity

    @ContributesAndroidInjector
    internal abstract fun githubJobActivity(): GitHubJobActivity


}
