package net.chrisfey.githubjobs

import dagger.Component
import net.chrisfey.githubjobs.di.AppModule
import net.chrisfey.githubjobs.di.NetworkModule
import net.chrisfey.githubjobs.di.RxModule
import net.chrisfey.githubjobs.di.ViewModelFactoryModule
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, RxModule::class, ViewModelFactoryModule::class])
internal interface AppComponent {
    fun inject(app: JobsApplication)
}
