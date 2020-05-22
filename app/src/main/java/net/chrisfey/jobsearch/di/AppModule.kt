package net.chrisfey.jobsearch.di

import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector
import net.chrisfey.jobsearch.view.detail.github.GitHubJobActivity
import net.chrisfey.jobsearch.view.detail.stackoverflow.StackOverflowJobActivity
import net.chrisfey.jobsearch.view.login.LoginActivity
import net.chrisfey.jobsearch.view.search.JobSearchActivity


@Module(includes = [AndroidInjectionModule::class])
internal abstract class AppModule {

    @ContributesAndroidInjector
    internal abstract fun jobSearchActivity(): JobSearchActivity


    @ContributesAndroidInjector
    internal abstract fun stackOverflowJobActivity(): StackOverflowJobActivity

    @ContributesAndroidInjector
    internal abstract fun githubJobActivity(): GitHubJobActivity

    @ContributesAndroidInjector
    internal abstract fun loginActivity(): LoginActivity

}
