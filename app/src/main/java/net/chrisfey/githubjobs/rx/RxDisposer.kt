package net.chrisfey.githubjobs.rx

import io.reactivex.disposables.Disposable


interface RxDisposer {
    val disposables: MutableList<Disposable>

    fun Disposable.addToTrash(){
        disposables.add(this)
    }

    fun takeOutTheTrash(){
        disposables.forEach{it.dispose()}
    }

}