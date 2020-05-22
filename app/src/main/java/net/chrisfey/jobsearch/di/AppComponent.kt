package net.chrisfey.jobsearch.di

import dagger.Component
import net.chrisfey.jobsearch.JobsApplication
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, ViewModelFactoryModule::class])
internal interface AppComponent {
    fun inject(app: JobsApplication)
}
