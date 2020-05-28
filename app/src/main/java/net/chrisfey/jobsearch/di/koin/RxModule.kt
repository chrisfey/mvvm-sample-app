package net.chrisfey.jobsearch.di.koin

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import net.chrisfey.jobsearch.rx.RxSchedulers
import org.koin.dsl.module

val rxModule = module {
    single<RxSchedulers> {
        object : RxSchedulers {
            override fun computation() = Schedulers.computation()
            override fun io() = Schedulers.io()
            override fun ui() = AndroidSchedulers.mainThread()
        }
    }
}