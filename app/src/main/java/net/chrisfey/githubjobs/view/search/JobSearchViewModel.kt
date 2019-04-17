package net.chrisfey.githubjobs.view.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import net.chrisfey.githubjobs.R
import net.chrisfey.githubjobs.repository.GithubJob
import net.chrisfey.githubjobs.repository.IGithubJobRepository
import net.chrisfey.githubjobs.utils.Rx
import net.chrisfey.stackOverflowjobs.repository.IStackOverflowJobRepository
import net.chrisfey.stackOverflowjobs.repository.StackOverflowJob


class JobSearchViewModelFactory constructor(
    val stackoverflowRepository: IStackOverflowJobRepository,
    val githubRepository: IGithubJobRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(JobSearchViewModel::class.java!!)) {
            JobSearchViewModel(githubRepository, stackoverflowRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}


class JobSearchViewModel(
    private val githubRepository: IGithubJobRepository,
    private val stackoverflowRepository: IStackOverflowJobRepository
) : ViewModel(), Rx {
    override val disposables = mutableListOf<Disposable>()

    val state = BehaviorSubject.createDefault<JobSearchViewState>(JobSearchViewState.Initial)


    fun searchJobs(description: String, location: String) {
        state.onNext(JobSearchViewState.Loading)
        githubRepository.searchJobs(description, location)
            .singleElement()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

            .subscribe({
                val currentState = state.value!!
                state.onNext(
                    when (currentState) {
                        is JobSearchViewState.Success -> currentState.copy(gtihubJobs = it)
                        else -> JobSearchViewState.Success(it, null)
                    }
                )
            }, {

                state.onNext(JobSearchViewState.Error(it.message))
            })
            .addToTrash()

        stackoverflowRepository.searchJobs(description, location)
            .singleElement()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val currentState = state.value!!
                state.onNext(
                    when (currentState) {
                        is JobSearchViewState.Success -> currentState.copy(stackOverflowJobs = it)
                        else -> JobSearchViewState.Success(null, it)
                    }
                )
            }, {

                state.onNext(JobSearchViewState.Error(it.message))
            })
            .addToTrash()
    }

    override fun onCleared() {
        super.onCleared()
        takeOutTheTrash()
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