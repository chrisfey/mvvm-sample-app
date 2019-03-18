package net.chrisfey.githubjobs.view.search

import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import net.chrisfey.githubjobs.R
import net.chrisfey.githubjobs.repository.GithubJob
import net.chrisfey.githubjobs.repository.GithubJobRepository
import net.chrisfey.githubjobs.utils.Rx
import net.chrisfey.stackOverflowjobs.repository.StackOverflowJob
import net.chrisfey.stackOverflowjobs.repository.StackOverflowJobRepository
import javax.inject.Inject

class JobSearchViewModel : ViewModel(), Rx {
    override val disposables = mutableListOf<Disposable>()


    @Inject
    lateinit var githubRepository: GithubJobRepository

    @Inject
    lateinit var stackoverflowRepository: StackOverflowJobRepository

    val state = BehaviorSubject.createDefault(JobSearchViewState())


    fun searchJobs(description: String, location: String) {
        state.onNext(JobSearchViewState(isLoading = true))
        githubRepository.searchJobs(description, location)
            .singleElement()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                state.onNext(state.value!!.copy(isLoading = false, gtihubJobs = it))
            }
            .addToTrash()

        stackoverflowRepository.searchJobs(description, location)
            .singleElement()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                state.onNext(state.value!!.copy(isLoading = false, stackOverflowJobs = it))
            }
            .addToTrash()
    }

    override fun onCleared() {
        super.onCleared()
        takeOutTheTrash()
    }


}

sealed class Source(val icon: Int) {
    class Github() : Source(R.drawable.ic_github)
    class StackOverflow() : Source(R.drawable.ic_stack_overflow)
}

data class JobViewState(
    val jobId: String,
    val title: String,
    val company: String,
    val companyImg: String?,
    val source: Source
)

data class JobSearchViewState(
    private val gtihubJobs: List<GithubJob>? = null,
    private val stackOverflowJobs: List<StackOverflowJob>? = null,
    val isLoading: Boolean = false,
    val isCached: Boolean = false,
    val isOffline: Boolean = false
) {
    fun jobs(): List<JobViewState> {
        val githubJobs = gtihubJobs?.map {
            JobViewState(it.id, it.title, it.company, it.company_logo, Source.Github())
        } ?: emptyList()
        val stackOverflowJobs = stackOverflowJobs?.map {
            JobViewState(
                it.link!!,
                it.title!!,
                it.company!!,
                it.companyImage,
                Source.StackOverflow()
            )
        } ?: emptyList()

        return stackOverflowJobs.plus(githubJobs).sortedBy { it.title }
    }
}
