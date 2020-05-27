package net.chrisfey.jobsearch.utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel() {

    val coordinatorEvents = MutableLiveData<Any>()

    private val onClearedDisposable = CompositeDisposable()

    fun Disposable.disposeOnCleared() {
        onClearedDisposable.add(this)
    }

    override fun onCleared() {
        super.onCleared()
        onClearedDisposable.dispose()
    }

    fun sendCoordinatorEvent(event: Any) {
        coordinatorEvents.postValue(event)
    }
}