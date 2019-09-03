package net.chrisfey.githubjobs.view.detail.github

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import net.chrisfey.githubjobs.repository.GithubJob
import net.chrisfey.githubjobs.repository.IGithubJobRepository
import net.chrisfey.githubjobs.rx.RxDisposer
import timber.log.Timber

class GithubJobViewModelFactory constructor(
    val githubRepository: IGithubJobRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(GithubJobViewModel::class.java)) {
            GithubJobViewModel(githubRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}

class GithubJobViewModel(val githubRepository: IGithubJobRepository) : ViewModel(),
    RxDisposer {
    override val disposables = mutableListOf<Disposable>()
    val state = BehaviorSubject.createDefault(GithubJobViewState())


    fun getJob(url: String) {
        githubRepository
            .viewJob(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { state.onNext(GithubJobViewState(it)) },
                { Timber.e(it, "Error while scrapping") }
            )
            .addToTrash()

    }

    override fun onCleared() {
        super.onCleared()
        takeOutTheTrash()
    }

}


data class GithubJobViewState(
    val job: GithubJob? = null
)
