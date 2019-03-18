package net.chrisfey.githubjobs.logging

import android.util.Log
import org.jetbrains.annotations.NotNull
import timber.log.Timber

class ReleaseTree : @NotNull Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return;
        }

        //Dont log anything! but you could send this to EUM system like appD

    }

}
