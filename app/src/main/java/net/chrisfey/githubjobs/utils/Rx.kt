package net.chrisfey.githubjobs.utils

import io.reactivex.disposables.Disposable


interface Rx {
    val disposables: MutableList<Disposable>

    fun Disposable.addToTrash(){
        disposables.add(this)
    }

    fun takeOutTheTrash(){
        disposables.forEach{it.dispose()}
    }

}