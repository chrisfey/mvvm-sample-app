package net.chrisfey.githubjobs.rx

import io.reactivex.Scheduler

interface RxSchedulers {
    fun computation() : Scheduler
    fun io() : Scheduler
    fun ui() : Scheduler
}