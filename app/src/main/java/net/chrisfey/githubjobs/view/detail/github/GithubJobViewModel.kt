package net.chrisfey.githubjobs.view.detail.github

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.schedulers.Schedulers
import net.chrisfey.githubjobs.repository.GithubJob
import net.chrisfey.githubjobs.repository.IGithubJobRepository
import net.chrisfey.githubjobs.utils.BaseViewModel
import timber.log.Timber

class GithubJobViewModelFactory constructor(
    private val githubRepository: IGithubJobRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(GithubJobViewModel::class.java)) {
            GithubJobViewModel(githubRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}

class GithubJobViewModel(private val githubRepository: IGithubJobRepository) : BaseViewModel() {
    private val _viewState = MutableLiveData(GithubJobViewState())

    fun viewState(): LiveData<GithubJobViewState> = _viewState

    fun init(url: String) {
        githubRepository
            .viewJob(url)
            .subscribeOn(Schedulers.io())
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
