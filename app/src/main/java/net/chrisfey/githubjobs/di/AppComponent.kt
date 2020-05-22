package net.chrisfey.githubjobs.di

import dagger.Component
import net.chrisfey.githubjobs.JobsApplication
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, ViewModelFactoryModule::class])
internal interface AppComponent {
    fun inject(app: JobsApplication)
}
