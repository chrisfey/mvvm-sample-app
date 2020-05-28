package net.chrisfey.jobsearch

import android.app.Activity
import android.app.Application
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import net.chrisfey.jobsearch.di.DaggerAppComponent
import net.chrisfey.jobsearch.di.koin.firebaseModule
import net.chrisfey.jobsearch.di.koin.networkModule
import net.chrisfey.jobsearch.di.koin.preferencesModule
import net.chrisfey.jobsearch.di.koin.rxModule
import net.chrisfey.jobsearch.logging.DebugTree
import net.chrisfey.jobsearch.logging.KoinTimberLogger
import net.chrisfey.jobsearch.logging.ReleaseTree
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import javax.inject.Inject


open class JobsApplication : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>


    override fun onCreate() {
        super.onCreate()
        startKoin {
            logger(KoinTimberLogger())
            androidContext(this@JobsApplication)
            modules(rxModule, firebaseModule, networkModule, preferencesModule)
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