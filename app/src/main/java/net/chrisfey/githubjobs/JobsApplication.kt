package net.chrisfey.githubjobs

import android.app.Activity
import android.app.Application
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import net.chrisfey.githubjobs.di.DaggerAppComponent
import net.chrisfey.githubjobs.di.networkModuleKoin
import net.chrisfey.githubjobs.logging.DebugTree
import net.chrisfey.githubjobs.logging.ReleaseTree
import org.koin.core.context.startKoin
import timber.log.Timber
import javax.inject.Inject


open class JobsApplication : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>


    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(networkModuleKoin)
        }
        DaggerAppComponent.create().inject(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }

    override fun activityInjector() = dispatchingActivityInjector


}