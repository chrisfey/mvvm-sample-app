package net.chrisfey.jobsearch.view.detail.github

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.chrisfey.jobsearch.repository.GithubJob
import net.chrisfey.jobsearch.repository.IGithubJobRepository
import net.chrisfey.jobsearch.rx.RxSchedulers
import net.chrisfey.jobsearch.utils.BaseViewModel
import timber.log.Timber

class GithubJobViewModelFactory constructor(
    private val githubRepository: IGithubJobRepository,
    private val schedulers: RxSchedulers
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(GithubJobViewModel::class.java)) {
            GithubJobViewModel(githubRepository, schedulers) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}

class GithubJobViewModel(private val githubRepository: IGithubJobRepository, private val schedulers: RxSchedulers) : BaseViewModel() {
    private val _viewState = MutableLiveData(GithubJobViewState())

    fun viewState(): LiveData<GithubJobViewState> = _viewState

    fun init(url: String) {
        githubRepository
            .viewJob(url)
            .subscribe(
                { _viewState.postValue(GithubJobViewState(it)) },
                { Timber.e(it, "Error while scrapping") }
            )
            .disposeOnCleared()
    }
}


data class GithubJobViewState(
    val job: GithubJob? = null
)
