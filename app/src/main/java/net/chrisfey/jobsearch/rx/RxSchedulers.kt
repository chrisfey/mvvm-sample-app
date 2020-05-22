package net.chrisfey.jobsearch.rx

import io.reactivex.Scheduler

interface RxSchedulers {
    fun computation() : Scheduler
    fun io() : Scheduler
    fun ui() : Scheduler
}