package net.chrisfey.githubjobs.logging

import timber.log.Timber

class DebugTree : Timber.DebugTree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
       super.log(priority, "GITHUB_JOBS:$tag",message,t)

    }

}
