package net.chrisfey.githubjobs

import android.app.Activity
import android.app.Application
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import net.chrisfey.githubjobs.logging.DebugTree
import net.chrisfey.githubjobs.logging.ReleaseTree
import timber.log.Timber
import javax.inject.Inject


open class JobsApplication : Application(), HasActivityInjector {
    @Inject lateinit var dispatchingActivityInjector :DispatchingAndroidInjector<Activity>


    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.create().inject(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }

    override fun activityInjector() =dispatchingActivityInjector



}