package net.chrisfey.githubjobs

import android.app.Application
import net.chrisfey.githubjobs.di.DaggerViewModelInjector
import net.chrisfey.githubjobs.logging.DebugTree
import net.chrisfey.githubjobs.logging.ReleaseTree
import timber.log.Timber



open class GithubJobsApplication : Application(){

    open var viewModelInjector = DaggerViewModelInjector.create()

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }



}