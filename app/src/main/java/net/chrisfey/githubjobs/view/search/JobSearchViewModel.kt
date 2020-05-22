package net.chrisfey.githubjobs.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import net.chrisfey.githubjobs.R
import net.chrisfey.githubjobs.repository.GithubJob
import net.chrisfey.githubjobs.repository.IGithubJobRepository
import net.chrisfey.githubjobs.repository.IStackOverflowJobRepository
import net.chrisfey.githubjobs.repository.StackOverflowJob
import net.chrisfey.githubjobs.rx.RxSchedulers
import net.chrisfey.githubjobs.utils.BaseViewModel
import net.chrisfey.githubjobs.utils.Event
import net.chrisfey.githubjobs.utils.EventMutableLiveData

class JobSearchViewModel(
    private val githubRepository: IGithubJobRepository,
    private val stackoverflowRepository: IStackOverflowJobRepository,
    private val schedulers: RxSchedulers
) : BaseViewModel() {

    private val _viewState = MutableLiveData<JobSearchViewState>(JobSearchViewState.Initial)
    private val _navigationEvent = EventMutableLiveData<NavigationEvent>()

    fun viewState(): LiveData<JobSearchViewState> = _viewState
    fun navigationEvent(): LiveData<Event<NavigationEvent>> = _navigationEvent

    fun searchJobs(description: String, location: String) {
        _viewState.postValue(JobSearchViewState.Loading)
        Observable
            .combineLatest(
                githubRepository.searchJobs(description, location).toObservable(),
                stackoverflowRepository.searchJobs(description, location),
                BiFunction { githubJobs: List<GithubJob>, stackoverflowJobs: List<StackOverflowJob> ->
                    when {
                        githubJobs.isEmpty() && stackoverflowJobs.isEmpty() -> JobSearchViewState.NoResults
                        else -> JobSearchViewState.Success(githubJobs, stackoverflowJobs)
                    }

                }
            )
            .subscribe(
                { _viewState.postValue(it) },
                { _viewState.postValue(JobSearchViewState.Error(it.message)) }
            )
            .disposeOnCleared()

    }

    fun jobTapped(current: JobViewState) {
        when (current.source) {
            is Source.StackOverflow -> _navigationEvent.postEvent(NavigationEvent.StackOverflowJobDetail(current.jobId))
            is Source.Github -> _navigationEvent.postEvent(NavigationEvent.GithubJobDetail(current.jobId))
        }
    }

    sealed class NavigationEvent {
        class StackOverflowJobDetail(val jobId: String) : NavigationEvent()
        class GithubJobDetail(val jobId: String) : NavigationEvent()
    }
}

sealed class Source(val icon: Int) {
    class Github : Source(R.drawable.ic_github)
    class StackOverflow : Source(R.drawable.ic_stack_overflow)
}

data class JobViewState(
    val jobId: String,
    val title: String,
    val company: String,
    val companyImg: String?,
    val source: Source
)

sealed class JobSearchViewState {
    object Initial : JobSearchViewState()
    object Loading : JobSearchViewState()
    object NoResults : JobSearchViewState()
    data class Success(
        private val gtihubJobs: List<GithubJob>? = null,
        private val stackOverflowJobs: List<StackOverflowJob>? = null
    ) : JobSearchViewState() {
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

    data class Error(val message: String?) : JobSearchViewState()
}