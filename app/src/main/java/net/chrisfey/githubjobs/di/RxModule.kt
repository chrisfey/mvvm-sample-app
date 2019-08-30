package net.chrisfey.githubjobs.di

import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import net.chrisfey.githubjobs.rx.RxSchedulers


@Module
open class RxModule {

    @Provides
    open fun schedulers() = object : RxSchedulers {
        override fun computation() = Schedulers.computation()
        override fun io() = Schedulers.io()
        override fun ui() = AndroidSchedulers.mainThread()
    }
}