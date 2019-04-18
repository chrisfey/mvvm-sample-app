package net.chrisfey.githubjobs.view.detail.stackoverflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import net.chrisfey.githubjobs.repository.networking.StackOverflowScrapedJobResponse
import net.chrisfey.githubjobs.utils.Rx
import net.chrisfey.stackOverflowjobs.repository.IStackOverflowJobRepository
import timber.log.Timber

class StackOverflowJobViewModelFactory constructor(
    val stackoverflowRepository: IStackOverflowJobRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(StackOverflowJobViewModel::class.java!!)) {
            StackOverflowJobViewModel(stackoverflowRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}

class StackOverflowJobViewModel(val stackoverflowRepository: IStackOverflowJobRepository) : ViewModel(), Rx {
    override val disposables = mutableListOf<Disposable>()
    val state = BehaviorSubject.createDefault(StackOverflowJobViewState())


    fun getJob(url: String) {
        stackoverflowRepository
            .viewJob(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { state.onNext(StackOverflowJobViewState(it)) },
                { Timber.e(it, "Error while scrapping") }
            )
            .addToTrash()


    }

    override fun onCleared() {
        super.onCleared()
        takeOutTheTrash()
    }


}


data class StackOverflowJobViewState(
    val job: StackOverflowScrapedJobResponse? = null
)
