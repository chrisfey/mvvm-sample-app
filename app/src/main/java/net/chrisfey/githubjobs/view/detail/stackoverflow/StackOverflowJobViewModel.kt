package net.chrisfey.githubjobs.view.detail.stackoverflow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.chrisfey.githubjobs.repository.IStackOverflowJobRepository
import net.chrisfey.githubjobs.repository.networking.StackOverflowScrapedJobResponse
import net.chrisfey.githubjobs.rx.RxSchedulers
import net.chrisfey.githubjobs.utils.BaseViewModel
import timber.log.Timber

class StackOverflowJobViewModelFactory constructor(
    private val stackoverflowRepository: IStackOverflowJobRepository,
    private val schedulers: RxSchedulers
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(StackOverflowJobViewModel::class.java)) {
            StackOverflowJobViewModel(stackoverflowRepository, schedulers) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}

class StackOverflowJobViewModel(private val stackoverflowRepository: IStackOverflowJobRepository, private val schedulers: RxSchedulers) :
    BaseViewModel() {
    private val _viewState = MutableLiveData<StackOverflowJobViewState>(StackOverflowJobViewState())

    fun viewState(): LiveData<StackOverflowJobViewState> = _viewState

    fun init(url: String) {
        stackoverflowRepository
            .viewJob(url)
            .subscribe(
                { _viewState.postValue(StackOverflowJobViewState(it)) },
                { Timber.e(it, "Error while scrapping") }
            )
            .disposeOnCleared()
    }
}

data class StackOverflowJobViewState(
    val job: StackOverflowScrapedJobResponse? = null
)
