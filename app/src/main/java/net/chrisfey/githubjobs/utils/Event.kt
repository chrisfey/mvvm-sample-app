package net.chrisfey.githubjobs.utils

import androidx.lifecycle.MutableLiveData

class EventMutableLiveData<T> : MutableLiveData<Event<T>>() {

    fun postEvent(value: T) {
        super.postValue(Event(value))
    }
}


class Event<T>(val event: T) {

    private var handled = false

    fun isHandled(): Boolean {
        synchronized(this) {
            return handled
        }
    }

    fun handle(handler: (T) -> Unit) {
        synchronized(this) {
            handled = true
            handler.invoke(event)
        }
    }
}