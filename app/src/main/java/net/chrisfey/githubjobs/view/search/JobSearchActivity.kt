package net.chrisfey.githubjobs.view.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding3.swiperefreshlayout.refreshes
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_search.*
import net.chrisfey.githubjobs.GithubJobsApplication
import net.chrisfey.githubjobs.utils.Rx
import net.chrisfey.githubjobs.utils.hideKeyboard




class JobSearchActivity : AppCompatActivity(), Rx {
    override val disposables = mutableListOf<Disposable>()

    private lateinit var adapter: JobListAdapter

    private lateinit var viewModel: JobSearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(net.chrisfey.githubjobs.R.layout.activity_search)

        adapter = JobListAdapter(this)
        jobList.adapter = adapter
        jobList.layoutManager = LinearLayoutManager(this)


        viewModel = ViewModelProviders.of(this).get(JobSearchViewModel::class.java)
        (application as GithubJobsApplication).viewModelInjector.inject(viewModel)

        viewModel.state.subscribe { renderState(it) }.addToTrash()

        searchBtn.clicks().subscribe { search() }.addToTrash()
        swiperefresh.refreshes().subscribe { search() }.addToTrash()
    }


    private fun search() {
        hideKeyboard()
        viewModel.searchJobs(
            description = descriptionEditText.text.toString(),
            location = locationEditText.text.toString()
        )
    }

    private fun renderState(viewState: JobSearchViewState) {

        if (viewState.jobs().isEmpty()) {
            trySearching.visibility = View.VISIBLE
            swiperefresh.visibility = View.GONE
        } else {
            swiperefresh.visibility = View.VISIBLE
            trySearching.visibility = View.GONE
            adapter.setJobs(viewState.jobs())
        }
        swiperefresh.isRefreshing = viewState.isLoading
    }

    override fun onDestroy() {
        super.onDestroy()
        takeOutTheTrash()
    }

}
