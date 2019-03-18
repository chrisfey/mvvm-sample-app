package net.chrisfey.githubjobs.view.search.job

import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import net.chrisfey.githubjobs.repository.networking.ScrapedStackOverflowJobResponse
import net.chrisfey.githubjobs.utils.Rx
import net.chrisfey.stackOverflowjobs.repository.StackOverflowJobRepository
import timber.log.Timber
import javax.inject.Inject

class StackOverflowJobViewModel : ViewModel(), Rx {
    override val disposables = mutableListOf<Disposable>()

    @Inject
    lateinit var stackoverflowRepository: StackOverflowJobRepository

    val state = BehaviorSubject.createDefault(StackOverflowJobViewState())


    fun getJob(url: String) {
        stackoverflowRepository
            .viewJob(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { state.onNext(StackOverflowJobViewState(it)) },
                { Timber.e(it, "Fucked up the scrapping")}
            )
            .addToTrash()


    }

    override fun onCleared() {
        super.onCleared()
        takeOutTheTrash()
    }


}


data class StackOverflowJobViewState(
     val job: ScrapedStackOverflowJobResponse? = null
)
