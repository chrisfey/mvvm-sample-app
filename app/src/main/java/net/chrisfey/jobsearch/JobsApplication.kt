package net.chrisfey.jobsearch

import android.app.Activity
import android.app.Application
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import net.chrisfey.jobsearch.di.*
import net.chrisfey.jobsearch.logging.DebugTree
import net.chrisfey.jobsearch.logging.ReleaseTree
import org.koin.core.context.startKoin
import timber.log.Timber
import javax.inject.Inject


open class JobsApplication : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>


    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(rxModule, firebaseModule, networkModuleKoin, viewModelModule)
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