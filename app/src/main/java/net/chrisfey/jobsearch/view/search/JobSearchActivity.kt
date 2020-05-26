package net.chrisfey.jobsearch.view.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_search.*
import net.chrisfey.jobsearch.utils.*
import net.chrisfey.jobsearch.view.detail.github.GitHubJobActivity
import net.chrisfey.jobsearch.view.detail.stackoverflow.StackOverflowJobActivity
import net.chrisfey.jobsearch.view.search.JobSearchViewModel.NavigationEvent
import org.koin.android.ext.android.inject


class JobSearchActivity : BaseActivity() {

    companion object {
        fun getIntent(context: Context) = Intent(context, JobSearchActivity::class.java)
    }

    private lateinit var adapter: JobListAdapter

    private val viewModel: JobSearchViewModel by inject()

    private var errorSnackBar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(net.chrisfey.jobsearch.R.layout.activity_search)

        adapter = JobListAdapter(this, viewModel::jobTapped)
        jobList.adapter = adapter
        jobList.layoutManager = LinearLayoutManager(this)

        with(viewModel) {
            observe(viewState()) { renderState(it) }
            observe(navigationEvents()) { event -> event.handle { handleNavigationEvent(it) } }
        }

        searchBtn.setOnClickListener { search() }
        swiperefresh.setOnRefreshListener { search() }
    }

    private fun handleNavigationEvent(navigationEvent: NavigationEvent) = when (navigationEvent) {
        is NavigationEvent.GithubJobDetail -> startActivity(GitHubJobActivity.newIntent(this, navigationEvent.jobId))
        is NavigationEvent.StackOverflowJobDetail -> startActivity(StackOverflowJobActivity.newIntent(this, navigationEvent.jobId))
    }

    private fun search() {
        hideKeyboard()
        viewModel.searchJobs(
            description = descriptionEditText.text.toString(),
            location = locationEditText.text.toString()
        )
    }

    private fun renderState(viewState: JobSearchViewState) {
        swiperefresh.gone()
        swiperefresh.isRefreshing = false
        trySearching.gone()
        noResults.gone()
        errorSnackBar?.dismiss()

        when (viewState) {
            is JobSearchViewState.Initial -> {
                swiperefresh.visible()
                trySearching.visible()
            }
            is JobSearchViewState.Loading -> {
                swiperefresh.visible()
                swiperefresh.isRefreshing = true
            }
            is JobSearchViewState.NoResults -> {
                noResults.visible()
            }
            is JobSearchViewState.Success -> {
                swiperefresh.visible()
                adapter.setJobs(viewState.jobs())
            }
            is JobSearchViewState.Error -> {
                errorSnackBar = Snackbar.make(this.window.decorView, "Error: ${viewState.message}", Snackbar.LENGTH_INDEFINITE)
                    .apply { setAction("DISMISS") { dismiss() } }
                errorSnackBar?.show()
            }
        }
    }

}
