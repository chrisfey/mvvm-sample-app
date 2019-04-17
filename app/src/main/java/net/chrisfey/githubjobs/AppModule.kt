package net.chrisfey.githubjobs

import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector
import net.chrisfey.githubjobs.view.search.JobSearchActivity
import net.chrisfey.githubjobs.view.search.JobSearchActivityModule


@Module(includes = [AndroidInjectionModule::class])
internal abstract class AppModule {

    @ContributesAndroidInjector(modules = [JobSearchActivityModule::class])
    internal abstract fun mainActivityInjector(): JobSearchActivity


}
